package com.shetj.diyalbume.playVideo.media

import android.content.ComponentName
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.shetj.diyalbume.R
import kotlinx.android.synthetic.main.activity_media.*
import me.shetj.base.base.BaseActivity
import me.shetj.base.kt.showToast
import me.shetj.base.kt.toJson
import me.shetj.base.tools.app.ArmsUtils
import me.shetj.base.tools.json.GsonKit
import timber.log.Timber
import java.util.*


/**
 *       //connect → onConnected → subscribe → onChildrenLoaded
 */
class MediaActivity : BaseActivity<MediaPresenter>() {

    private lateinit var mAdapter: MusicAdapter
    //连接状态回调接口
    private var mBrowserConnectionCallback: MediaBrowserCompat.ConnectionCallback ?=null
    private var mediaBrowser:MediaBrowserCompat ?= null //媒体浏览器

    private var mMediaController: MediaControllerCompat?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media)
        floatingActionButton.setOnClickListener {
            startOrPause()
        }
    }

    override fun initData() {
        mPresenter = MediaPresenter(this)
        mAdapter = MusicAdapter(ArrayList())
        mAdapter.bindToRecyclerView(iRecyclerView)
        mAdapter.setOnItemClickListener { _, _, position ->
            run {
                val item = mAdapter.getItem(position)
                ArmsUtils.makeText(GsonKit.objectToJson(item!!)!!)
                mMediaController?.transportControls?.playFromMediaId(item.mediaId,null)
            }
        }
    }


    override fun initView() {
        mPresenter = MediaPresenter(this)

        mBrowserConnectionCallback = object : MediaBrowserCompat.ConnectionCallback() {
            override fun onConnected() {
                super.onConnected()
                Timber.i("连接成功")
                mediaBrowser?.apply {
                    if (isConnected){
                        //链接 成功后创建控制器
                        mMediaController = MediaControllerCompat(applicationContext, this.sessionToken)
                        mMediaController?.registerCallback(mMediaControllerCallback)
                        // Sync existing MediaSession state to the UI.同步信息
                        mMediaController?.metadata?.let {
                            mMediaControllerCallback.onMetadataChanged(mMediaController?.metadata!!)
                        }
                        mMediaControllerCallback.onPlaybackStateChanged(mMediaController?.playbackState)

                        MediaControllerCompat.setMediaController(this@MediaActivity,mMediaController)

                        //设置循环模式
                        mMediaController?.transportControls?.setRepeatMode(PlaybackStateCompat.REPEAT_MODE_ALL)
                        Timber.i("连接成功 = mMediaController = ${mMediaController.toString()}")
                    }
                }
            }

            override fun onConnectionFailed() {
                super.onConnectionFailed()
                Timber.i("连接失败！")
            }
        }

        mediaBrowser = MediaBrowserCompat(this,
                ComponentName(this, MusicService::class.java)
                ,mBrowserConnectionCallback,
                null)

        //connect → onConnected → subscribe → onChildrenLoaded
        //3.建立连接
        mediaBrowser?.connect()
        //ID 用来区分列表
        mediaBrowser?.subscribe("ID", mBrowserSubscriptionCallback)

        //客户端通过调用sendCustomAction，根据与服务端的协商，制定相应的action类型，进行数据的传递交互。
//        mediaBrowser?.sendCustomAction("go", null,mCustomActionCallback)
    }

    /**
     *  客户端通过调用sendCustomAction，根据与服务端的协商，制定相应的action类型，进行数据的传递交互。
     */
    private val mCustomActionCallback = object : MediaBrowserCompat.CustomActionCallback() {
        override fun onProgressUpdate(action: String?, extras: Bundle?, data: Bundle?) {
            super.onProgressUpdate(action, extras, data)
            Timber.i("mCustomActionCallback :onProgressUpdate----数据变化")
        }

        override fun onResult(action: String?, extras: Bundle?, resultData: Bundle?) {
            super.onResult(action, extras, resultData)
            Timber.i("mCustomActionCallback :onResult ----数据变化")
        }

        override fun onError(action: String?, extras: Bundle?, data: Bundle?) {
            super.onError(action, extras, data)
            Timber.i("mCustomActionCallback :onError ----数据变化")
        }
    }

    /**
     * 向媒体浏览器服务发起订阅请求的回调接口
     * 客户端通过调用subscribe方法，传递MediaID，在SubscriptionCallback的方法中进行处理。
     */
    private val mBrowserSubscriptionCallback = object:MediaBrowserCompat.SubscriptionCallback(){
        override fun onChildrenLoaded(parentId: String, children: MutableList<MediaBrowserCompat.MediaItem>) {
            super.onChildrenLoaded(parentId, children)
            Timber.i("onChildrenLoaded---")

            //children 为来自Service的列表数据
            children.forEach{
                Timber.i(it.description.title.toString())
            }

            mAdapter.setNewData(children)
            Timber.i("setNewData---")
            if (mMediaController == null) {
                return
            }
//            // Queue up all media items for this simple sample.
            for (mediaItem in children) {
                mMediaController?.addQueueItem(mediaItem.description)
            }

            // Call "playFromMedia" so the UI is updated.
            mMediaController?.transportControls?.prepare()
        }
    }


    private val mMediaControllerCallback = object :MediaControllerCompat.Callback(){
        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            super.onPlaybackStateChanged(state)
            //音乐播放状态改变回调
            when (state?.state) {
                PlaybackStateCompat.STATE_NONE -> "需要点击开始".showToast()
                PlaybackStateCompat.STATE_PAUSED -> "需要点击重新开始".showToast()
                PlaybackStateCompat.STATE_PLAYING -> "需要点击暂停".showToast()
                else -> "需要点击开始".showToast()
            }
        }

        override fun onSessionDestroyed() {
            super.onSessionDestroyed()
            //Session销毁
            onPlaybackStateChanged(null)
        }

        override  fun onRepeatModeChanged(repeatMode: Int) {
            super.onRepeatModeChanged(repeatMode)
            Timber.i("onRepeatModeChanged ----循环模式发生变化")
        }

        override  fun onShuffleModeChanged(shuffleMode: Int) {
            super.onShuffleModeChanged(shuffleMode)
            Timber.i("onShuffleModeChanged ----随机模式发生变化 = $shuffleMode")
            //随机模式发生变化
        }

        override  fun onMetadataChanged(metadata: MediaMetadataCompat) {
            super. onMetadataChanged(metadata)
            Timber.i("onMetadataChanged ----数据变化 = ${metadata.toJson()}")
            mAdapter.selectMediaId(metadata.description.mediaId)
            //数据变化
        }
    }

    /**
     * 播放控制
     */
    fun startOrPause(){
        when(mMediaController?.playbackState?.state){
            PlaybackStateCompat.STATE_PLAYING ->{
                mMediaController?.transportControls?.pause()
            }
            PlaybackStateCompat.STATE_PAUSED ->{
                mMediaController?.transportControls?.play()
            }
            else ->{
                mMediaController?.transportControls?.play()
            }
        }
    }

}