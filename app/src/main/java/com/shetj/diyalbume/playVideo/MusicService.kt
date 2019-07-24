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

package com.shetj.diyalbume.playVideo

import android.content.Intent
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat

import com.shetj.diyalbume.playVideo.callback.MediaSessionCallback
import com.shetj.diyalbume.playVideo.contentcatalogs.MusicLibrary
import com.shetj.diyalbume.playVideo.notifications.MediaNotificationManager
import com.shetj.diyalbume.playVideo.player.MediaPlayerManager
import androidx.core.content.ContextCompat
import androidx.media.MediaBrowserServiceCompat
import timber.log.Timber


/**
 * MediaBrowserServiceCompat
 */
class MusicService : MediaBrowserServiceCompat() {
    private var mMediaPlayerManager: MediaPlayerManager? = null
    private var mMediaNotificationManager: MediaNotificationManager? = null
    private var mServiceInStartedState: Boolean = false
    private var mMediaSessionCompat: MediaSessionCompat? = null

    override fun onCreate() {
        super.onCreate()
        // 创建MediaSessionCompat
        mMediaSessionCompat = MediaSessionCompat(this, "MusicService")
        mMediaPlayerManager = MediaPlayerManager(this, MediaPlayerListener())
        // setCallBack
        mMediaSessionCompat!!.setCallback(MediaSessionCallback(this, mMediaSessionCompat!!, mMediaPlayerManager!!, 0))
        mMediaSessionCompat!!.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or
                        MediaSessionCompat.FLAG_HANDLES_QUEUE_COMMANDS or
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS)
        // setSessionToken
        sessionToken = mMediaSessionCompat!!.sessionToken
        mMediaNotificationManager = MediaNotificationManager(this)

    }

    override fun onTaskRemoved(rootIntent: Intent) {
        super.onTaskRemoved(rootIntent)
        stopSelf()
    }

    override fun onDestroy() {
        mMediaNotificationManager!!.onDestroy()
        mMediaPlayerManager!!.stop()
        mMediaSessionCompat!!.release()
        Timber.d("onDestroy: MediaPlayerManager stopped, and MediaSession released")
    }

    override fun onGetRoot(clientPackageName: String,
                           clientUid: Int,
                           rootHints: Bundle?): BrowserRoot? {
        return BrowserRoot(MusicLibrary.root, null)
    }

    override fun onLoadChildren(
            parentMediaId: String,
            result: Result<List<MediaBrowserCompat.MediaItem>>) {
        result.sendResult(MusicLibrary.mediaItems)
    }

    /**
     * MediaPlayer 播放状态回调
     */
    inner class MediaPlayerListener : PlaybackInfoListener() {



        override fun onPlaybackStateChange(state: PlaybackStateCompat) {
            // 最终回调到Client 的 MediaControllerCallback.onPlaybackStateChanged
            mMediaSessionCompat!!.setPlaybackState(state)
            when (state.state) {
                PlaybackStateCompat.STATE_PLAYING -> moveServiceToStartedState(state)
                PlaybackStateCompat.STATE_PAUSED -> updateNotificationForPause(state)
                PlaybackStateCompat.STATE_STOPPED -> moveServiceOutOfStartedState(state)
            }
        }

        override fun onPlaybackCompleted() {

        }


        private fun moveServiceToStartedState(state: PlaybackStateCompat) {
            //
            val notification = mMediaNotificationManager!!.getNotification(
                    mMediaPlayerManager!!.currentMedia!!, state, sessionToken!!)
            //
            if (!mServiceInStartedState) {
                ContextCompat.startForegroundService(
                        this@MusicService,
                        Intent(this@MusicService, MusicService::class.java))
                mServiceInStartedState = true
            }
            //
            startForeground(MediaNotificationManager.NOTIFICATION_ID, notification)
        }

        /**
         * @param state
         */
        private fun updateNotificationForPause(state: PlaybackStateCompat) {
            stopForeground(false)

        }

        /**
         * @param state
         */
        private fun moveServiceOutOfStartedState(state: PlaybackStateCompat) {
            stopForeground(true)
            stopSelf()
            mServiceInStartedState = false
        }


    }

}