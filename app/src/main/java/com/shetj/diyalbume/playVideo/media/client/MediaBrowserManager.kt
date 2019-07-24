/*
 * Copyright 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.shetj.diyalbume.playVideo.media.client


import android.content.ComponentName
import android.content.Context
import android.os.RemoteException
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat

import com.shetj.diyalbume.playVideo.media.MediaService

import java.util.ArrayList
import timber.log.Timber

/**
 * MediaBrowserManager for a MediaBrowser that handles connecting, disconnecting,
 * and basic browsing.
 */
class MediaBrowserManager(private val mContext: Context) {
    private var mMediaBrowserCompat: MediaBrowserCompat? = null
    private var mMediaController: MediaControllerCompat? = null
    private val mMediaBrowserConnectionCallback = MediaBrowserConnectionCallback()
    private val mMediaControllerCallback = MediaControllerCallback()
    private val mMediaBrowserSubscriptionCallback = MediaBrowserSubscriptionCallback()


    /**
     * 获取播放控制器 通过该方法控制播放
     *
     * @return
     */
    val transportControls: MediaControllerCompat.TransportControls
        get() {
            if (mMediaController == null) {
                Timber.d("getTransportControls: MediaController is null!")
                throw IllegalStateException()
            }
            return mMediaController!!.transportControls
        }

    // ########################################音频变化回调 管理列表###################################################

    /**
     * 音频变化回调 管理列表
     */
    private val mMediaStatusChangeListenerList = ArrayList<OnMediaStatusChangeListener>()

    /**
     * 跟随Activity的生命周期
     */
    fun onStart() {
        //
        if (mMediaBrowserCompat == null) {
            // 创建MediaBrowserCompat
            mMediaBrowserCompat = MediaBrowserCompat(
                    mContext,
                    // 创建ComponentName 连接 MusicService
                    ComponentName(mContext, MediaService::class.java),
                    // 创建callback
                    mMediaBrowserConnectionCallback, null)//
            // 链接service
            mMediaBrowserCompat!!.connect()
        }
        Timber.d("onStart: Creating MediaBrowser, and connecting")
    }

    /**
     * 跟随Activity的生命周期
     */
    fun onStop() {
        if (mMediaController != null) {
            mMediaController!!.unregisterCallback(mMediaControllerCallback)
            mMediaController = null
        }
        if (mMediaBrowserCompat != null && mMediaBrowserCompat!!.isConnected) {
            mMediaBrowserCompat!!.disconnect()
            mMediaBrowserCompat = null
        }
        // 数据置空
        Timber.d("onStop: Releasing MediaController, Disconnecting from MediaBrowser")
    }


    // ############################################onConnected CallBack################################################

    /**
     * mediaService的链接回调
     */
    // Receives callbacks from the MediaBrowser when it has successfully connected to the
    // MediaBrowserService (MusicService).
    inner class MediaBrowserConnectionCallback : MediaBrowserCompat.ConnectionCallback() {

        // 连接成功
        // Happens as a result of onStart().
        override fun onConnected() {
            try {
                mMediaController = MediaControllerCompat(
                        mContext,
                        mMediaBrowserCompat!!.sessionToken)
                mMediaController?.registerCallback(mMediaControllerCallback)
                if (mMediaController?.metadata != null) {
                    mMediaControllerCallback.onMetadataChanged(mMediaController?.metadata)
                    mMediaControllerCallback.onPlaybackStateChanged(mMediaController?.playbackState)
                }
            } catch (e: RemoteException) {
                Timber.d(String.format("onConnected: Problem: %s", e.toString()))
                throw RuntimeException(e)
            }

            mMediaBrowserCompat!!.subscribe(mMediaBrowserCompat!!.root, mMediaBrowserSubscriptionCallback)
        }
    }

    // ############################################onChildrenLoaded CallBack################################################


    /**
     * 加载新数据后调用
     * Receives callbacks from the MediaBrowser when the MediaBrowserService has loaded new media
     * that is ready for playback.
     */
    inner class MediaBrowserSubscriptionCallback : MediaBrowserCompat.SubscriptionCallback() {

        /**
         * service 的数据发送到这里
         *
         * @param parentId
         * @param children
         */
        override fun onChildrenLoaded(parentId: String,
                                      children: List<MediaBrowserCompat.MediaItem>) {
            if (mMediaController == null) {
                return
            }
            // Queue up all media items for this simple sample.
            for (mediaItem in children) {
                mMediaController!!.addQueueItem(mediaItem.description)
            }
            // Call "playFromMedia" so the UI is updated.
            mMediaController!!.transportControls.prepare()
        }
    }


    // ############################################MediaControllerCallback CallBack################################################


    /**
     * service 通过MediaControllerCallback 回调到client
     */
    inner class MediaControllerCallback : MediaControllerCompat.Callback() {

        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            //
            for (callback in mMediaStatusChangeListenerList) {
                callback.onMetadataChanged(metadata)
            }
        }

        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            //
            for (callback in mMediaStatusChangeListenerList) {
                callback.onPlaybackStateChanged(state!!)
            }
        }

        override fun onQueueChanged(queue: List<MediaSessionCompat.QueueItem>?) {
            super.onQueueChanged(queue)
            //
            for (callback in mMediaStatusChangeListenerList) {
                callback.onQueueChanged(queue)
            }
        }

        // service被杀死时调用
        override fun onSessionDestroyed() {
            onPlaybackStateChanged(null)
        }

    }

    /**
     * 添加音频变化回调
     *
     * @param l
     */
    fun addOnMediaStatusListener(l: OnMediaStatusChangeListener) {
        mMediaStatusChangeListenerList.add(l)
    }

    /**
     * 移除音频变化回调
     *
     * @param l
     */
    fun removeOnMediaStatusListener(l: OnMediaStatusChangeListener) {
        mMediaStatusChangeListenerList.remove(l)
    }


    /**
     * 音频变化回调
     */
    interface OnMediaStatusChangeListener {

        /**
         * 播放状态修改
         */
        fun onPlaybackStateChanged(state: PlaybackStateCompat)

        /**
         * 当前播放歌曲信息修改
         */
        fun onMetadataChanged(metadata: MediaMetadataCompat?)

        /**
         * 播放队列修改
         */
        fun onQueueChanged(queue: List<MediaSessionCompat.QueueItem>?)
    }


}