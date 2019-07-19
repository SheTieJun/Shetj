package com.shetj.diyalbume.playVideo

import android.content.ComponentName
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.shetj.diyalbume.R
import me.shetj.base.base.BaseActivity
import timber.log.Timber

/**
 */
class MediaActivity : BaseActivity<MediaPresenter>() {

    //连接状态回调接口
    private var mBrowserConnectionCallback: MediaBrowserCompat.ConnectionCallback ?=null
    private var mediaBrowser:MediaBrowserCompat ?= null //媒体浏览器

    private var controller: MediaControllerCompat?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media)
        initView()
        initData()
    }

    override fun initData() {

    }

    override fun onStart() {
        super.onStart()
        mediaBrowser?.connect()
    }

    override fun onStop() {
        super.onStop()
        mediaBrowser?.disconnect()
    }

    override fun initView() {
        mPresenter = MediaPresenter(this)

        mBrowserConnectionCallback = object : MediaBrowserCompat.ConnectionCallback() {
            override fun onConnected() {
                super.onConnected()
                Timber.i("连接成功")
                mediaBrowser?.apply {
                    if (isConnected){
                        root
                        unsubscribe(root)
                        subscribe(root,mBrowserSubscriptionCallback)
                        controller = MediaControllerCompat(rxContext,sessionToken)
                        controller?.registerCallback(mMediaControllerCallback)
                    }
                }
            }

            override fun onConnectionFailed() {
                super.onConnectionFailed()
                Timber.i("连接失败！")
            }
        }

        mediaBrowser = MediaBrowserCompat(this,
                ComponentName(this,MediaService::class.java)
                ,mBrowserConnectionCallback,
                null)

        //connect → onConnected → subscribe → onChildrenLoaded


    }

    /**
     * 向媒体浏览器服务发起订阅请求的回调接口
     */
    private val mBrowserSubscriptionCallback = object:MediaBrowserCompat.SubscriptionCallback(){
        override fun onChildrenLoaded(parentId: String, children: MutableList<MediaBrowserCompat.MediaItem>) {
            super.onChildrenLoaded(parentId, children)
            Timber.i("onChildrenLoaded---")
            children.forEach{
                Timber.i(it.description.title.toString())
            }
        }
    }


    private val mMediaControllerCallback = object :MediaControllerCompat.Callback(){
        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            super.onPlaybackStateChanged(state)
            //音乐播放状态改变回调
            val info = when (state?.state) {
                PlaybackStateCompat.STATE_NONE -> "点击开始"
                PlaybackStateCompat.STATE_PAUSED -> "点击开始"
                PlaybackStateCompat.STATE_PLAYING -> "点击暂停"
                else -> "其他状态"
            }
            Timber.i(info)
        }

        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            super.onMetadataChanged(metadata)
            //音乐播放改变回调
        }
    }

    fun startOrPause(){
        when(controller?.playbackState?.state){
            PlaybackStateCompat.STATE_PLAYING ->{
                controller?.transportControls?.pause()
            }
            PlaybackStateCompat.STATE_PAUSED ->{
                controller?.transportControls?.play()
            }
        }
    }


}