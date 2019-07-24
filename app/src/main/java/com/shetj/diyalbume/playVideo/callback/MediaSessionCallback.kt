package com.shetj.diyalbume.playVideo.callback

import android.content.Context
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat

import com.shetj.diyalbume.playVideo.MusicService
import com.shetj.diyalbume.playVideo.contentcatalogs.MusicLibrary
import com.shetj.diyalbume.playVideo.player.MediaPlayerManager

import java.util.ArrayList

import timber.log.Timber

/**
 * [MediaSessionCallback]
 * 用户对UI的操作将最终回调到这里。通过MediaSessionCallback 操作播放器
 * The callback class will receive all the user's actions, like play, pause, etc;
 */
class MediaSessionCallback(private val context: Context,
                           private val mMediaSessionCompat: MediaSessionCompat,
                           private val mMediaPlayerManager: MediaPlayerManager,
                           mQueueIndex: Int) : MediaSessionCompat.Callback() {
    /**
     * 播放列表
     */
    private val mPlaylist = ArrayList<MediaSessionCompat.QueueItem>()
    private var mQueueIndex = -1
    private var mPreparedMedia: MediaMetadataCompat? = null

    /**
     * 判断列表数据状态
     * @return
     */
    private val isReadyToPlay: Boolean
        get() = mPlaylist.isNotEmpty()

    init {
        this.mQueueIndex = mQueueIndex
    }


    override fun onAddQueueItem(description: MediaDescriptionCompat?) {
        mPlaylist.add(MediaSessionCompat.QueueItem(description!!, description.hashCode().toLong()))
        mQueueIndex = if (mQueueIndex == -1) 0 else mQueueIndex
    }

    override fun onRemoveQueueItem(description: MediaDescriptionCompat?) {
        mPlaylist.remove(MediaSessionCompat.QueueItem(description!!, description.hashCode().toLong()))
        mQueueIndex = if (mPlaylist.isEmpty()) -1 else mQueueIndex
    }

    override fun onPrepare() {
        if (mQueueIndex < 0 && mPlaylist.isEmpty()) {
            // Nothing to play.
            return
        }

        val mediaId = mPlaylist[mQueueIndex].description.mediaId
        // 根据音频 获取音频数据
        mPreparedMedia = MusicLibrary.getMetadata(context, mediaId!!)
        mMediaSessionCompat.setMetadata(mPreparedMedia)
        // 激活mediaSession
        if (!mMediaSessionCompat.isActive) {
            mMediaSessionCompat.isActive = true
        }
    }

    override fun onPlay() {
        //
        if (!isReadyToPlay) {
            // Nothing to play.
            return
        }
        // 准备数据
        if (mPreparedMedia == null) {
            onPrepare()
        }

    }

    override fun onPause() {
        mMediaPlayerManager.pause()
    }

    override fun onStop() {
        mMediaPlayerManager.stop()
        mMediaSessionCompat.isActive = false
    }

    override fun onSkipToNext() {
        mQueueIndex = ++mQueueIndex % mPlaylist.size
        mPreparedMedia = null
        onPlay()
    }

    override fun onSkipToPrevious() {
        mQueueIndex = if (mQueueIndex > 0) mQueueIndex - 1 else mPlaylist.size - 1
        mPreparedMedia = null
        onPlay()
    }

    override fun onSeekTo(pos: Long) {
        mMediaPlayerManager.seekTo(pos)
    }
}

