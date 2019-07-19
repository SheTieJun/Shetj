package com.shetj.diyalbume.playVideo

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.media.MediaBrowserServiceCompat
import timber.log.Timber

class MediaService : MediaBrowserServiceCompat()
        ,MediaPlayer.OnCompletionListener
        ,MediaPlayer.OnPreparedListener{

    private var mMediaSession:MediaSessionCompat?=null //会话，用来回调
    private var mPlaybackState:PlaybackStateCompat ?=null //
    private var mediaPlayer:MediaPlayer ?= null

    override fun onCreate() {
        super.onCreate()



        mMediaSession = MediaSessionCompat(this,"mediaService")
        mMediaSession?.setCallback(sessionCallback)//设置回调
        mMediaSession?.setFlags(MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS)
        bind()
        initMediaPlay()
        //设置Token 后会触发MediaBrowserCompat.connectionCallBack的回调方法
        //触发成功表示MediaBrowser和MediaBrowserService连接成功
        sessionToken = mMediaSession?.sessionToken
    }

    private fun initMediaPlay() {
        mediaPlayer = MediaPlayer()
        mediaPlayer?.setOnPreparedListener(this)
        mediaPlayer?.setOnCompletionListener(this)
    }

    /**
     * 会话和状态绑定
     */
    private fun bind() {
        mPlaybackState = PlaybackStateCompat
                .Builder()
                .setState(PlaybackStateCompat.STATE_NONE, 0, 1.0f)
                .build()
        mMediaSession?.setPlaybackState(mPlaybackState)
    }

    //控制客户端媒体浏览器的连接请求，通过返回值决定是否允许该客户端连接服务
    override fun onGetRoot(clientPackageName: String, clientUid: Int, rootHints: Bundle?): BrowserRoot? {
        Timber.i("onGetRoot ---- ")
        return BrowserRoot("root", null)
    }

    override fun onLoadChildren(parentId: String, result: Result<MutableList<MediaBrowserCompat.MediaItem>>) {
        Timber.i("onLoadChildren ---- ")
        //移除信息，允许sendResult
        result.detach()

        val mediaItems = ArrayList<MediaBrowserCompat.MediaItem>()
        //模拟获取音乐数据
        repeat(10) {
            mediaItems.add(createMediaItem())
        }
        result.sendResult(mediaItems)
    }

    private fun createMediaItem():MediaBrowserCompat.MediaItem {
        //我们模拟获取数据的过程，真实情况应该是异步从网络或本地读取数据
        val metadata = MediaMetadataCompat.Builder()
                .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI, "播放连接")
                .putString(MediaMetadataCompat.METADATA_KEY_TITLE, "圣诞歌")
                .build()
        return MediaBrowserCompat.MediaItem(metadata.description,MediaBrowserCompat.MediaItem.FLAG_PLAYABLE)
    }


    /**
     * MediaController.getTransportControls().play
     * 响应控制器指令的回调
     */
    private val sessionCallback = object :MediaSessionCompat.Callback(){
        override fun onPlay() {
            super.onPlay()
            //MediaController.getTransportControls().play
        }

        override fun onPause() {
            super.onPause()
        }

        override fun onPlayFromUri(uri: Uri?, extras: Bundle?) {
            super.onPlayFromUri(uri, extras)
        }

        override fun onPlayFromSearch(query: String?, extras: Bundle?) {
            super.onPlayFromSearch(query, extras)
        }

    }


    override fun onCompletion(mediaPlayer: MediaPlayer?) {
        mediaPlayer?.reset()
        bind()
    }

    override fun onPrepared(mediaPlayer: MediaPlayer?) {
        mediaPlayer?.start()
        bind()
    }
}