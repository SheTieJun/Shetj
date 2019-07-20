package com.shetj.diyalbume.playVideo.callback;

import android.content.Context;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;

import com.shetj.diyalbume.playVideo.MusicService;
import com.shetj.diyalbume.playVideo.contentcatalogs.MusicLibrary;
import com.shetj.diyalbume.playVideo.player.MediaPlayerManager;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * MediaSessionCallback
 * <p>
 * 用户对UI的操作将最终回调到这里。通过MediaSessionCallback 操作播放器
 * <p>
 * The callback class will receive all the user's actions, like play, pause, etc;
 */
public class MediaSessionCallback extends MediaSessionCompat.Callback {

    private MediaPlayerManager mMediaPlayerManager;
    private Context context;
    private MediaSessionCompat mMediaSessionCompat;
    /**
     *播放列表
     */
    private final List<MediaSessionCompat.QueueItem> mPlaylist = new ArrayList<>();
    private int mQueueIndex = -1;
    private MediaMetadataCompat mPreparedMedia;

    public MediaSessionCallback(Context context,
                                MediaSessionCompat mMediaSessionCompat,
                                MediaPlayerManager mMediaPlayerManager,
                                int mQueueIndex) {
        this.mQueueIndex = mQueueIndex;
        this.context = context;
        this.mMediaSessionCompat = mMediaSessionCompat;
        this.mMediaPlayerManager = mMediaPlayerManager;
    }


    @Override
    public void onAddQueueItem(MediaDescriptionCompat description) {
        mPlaylist.add(new MediaSessionCompat.QueueItem(description, description.hashCode()));
        mQueueIndex = (mQueueIndex == -1) ? 0 : mQueueIndex;
    }

    @Override
    public void onRemoveQueueItem(MediaDescriptionCompat description) {
        mPlaylist.remove(new MediaSessionCompat.QueueItem(description, description.hashCode()));
        mQueueIndex = (mPlaylist.isEmpty()) ? -1 : mQueueIndex;
    }

    @Override
    public void onPrepare() {
        if (mQueueIndex < 0 && mPlaylist.isEmpty()) {
            // Nothing to play.
            return;
        }

        final String mediaId = mPlaylist.get(mQueueIndex).getDescription().getMediaId();
        // 根据音频 获取音频数据
        mPreparedMedia = MusicLibrary.getMetadata(context, mediaId);
        mMediaSessionCompat.setMetadata(mPreparedMedia);
        // 激活mediaSession
        if (!mMediaSessionCompat.isActive()) {
            mMediaSessionCompat.setActive(true);
        }
    }

    @Override
    public void onPlay() {
        //
        if (!isReadyToPlay()) {
            // Nothing to play.
            return;
        }
        // 准备数据
        if (mPreparedMedia == null) {
            onPrepare();
        }

    }

    @Override
    public void onPause() {
        mMediaPlayerManager.pause();
    }

    @Override
    public void onStop() {
        mMediaPlayerManager.stop();
        mMediaSessionCompat.setActive(false);
    }
    @Override
    public void onSkipToNext() {
        mQueueIndex = (++mQueueIndex % mPlaylist.size());
        mPreparedMedia = null;
        onPlay();
    }

    @Override
    public void onSkipToPrevious() {
        mQueueIndex = mQueueIndex > 0 ? mQueueIndex - 1 : mPlaylist.size() - 1;
        mPreparedMedia = null;
        onPlay();
    }

    @Override
    public void onSeekTo(long pos) {
        mMediaPlayerManager.seekTo(pos);
    }

    /**
     * 判断列表数据状态
     *
     * @return
     */
    private boolean isReadyToPlay() {
        return (!mPlaylist.isEmpty());
    }
}

