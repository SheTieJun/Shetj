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

package com.shetj.diyalbume.playVideo;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import com.shetj.diyalbume.playVideo.callback.MediaSessionCallback;
import com.shetj.diyalbume.playVideo.contentcatalogs.MusicLibrary;
import com.shetj.diyalbume.playVideo.notifications.MediaNotificationManager;
import com.shetj.diyalbume.playVideo.player.MediaPlayerManager;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.media.MediaBrowserServiceCompat;
import timber.log.Timber;


/**
 * MediaBrowserServiceCompat
 */
public class MusicService extends MediaBrowserServiceCompat {
    private MediaPlayerManager mMediaPlayerManager;
    private MediaNotificationManager mMediaNotificationManager;
    private boolean mServiceInStartedState;
    private MediaSessionCompat mMediaSessionCompat;

    @Override
    public void onCreate() {
        super.onCreate();
        // 创建MediaSessionCompat
        mMediaSessionCompat = new MediaSessionCompat(this, "MusicService");
        mMediaPlayerManager = new MediaPlayerManager(this, new MediaPlayerListener());
        // setCallBack
        mMediaSessionCompat.setCallback(new MediaSessionCallback(this,mMediaSessionCompat,mMediaPlayerManager,0));
        mMediaSessionCompat.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_QUEUE_COMMANDS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        // setSessionToken
        setSessionToken(mMediaSessionCompat.getSessionToken());
        mMediaNotificationManager = new MediaNotificationManager(this);

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        stopSelf();
    }

    @Override
    public void onDestroy() {
        mMediaNotificationManager.onDestroy();
        mMediaPlayerManager.stop();
        mMediaSessionCompat.release();
        Timber.d ("onDestroy: MediaPlayerManager stopped, and MediaSession released");
    }

    @Override
    public BrowserRoot onGetRoot(@NonNull String clientPackageName,
                                 int clientUid,
                                 Bundle rootHints) {
        return new BrowserRoot(MusicLibrary.getRoot(), null);
    }

    @Override
    public void onLoadChildren(
            @NonNull final String parentMediaId,
            @NonNull final Result<List<MediaBrowserCompat.MediaItem>> result) {
        result.sendResult(MusicLibrary.getMediaItems());
    }

    /**
     * MediaPlayer 播放状态回调
     */
    public class MediaPlayerListener extends PlaybackInfoListener {

        private final ServiceManager mServiceManager;

        public MediaPlayerListener() {
            mServiceManager = new ServiceManager();
        }

        @Override
        public void onPlaybackStateChange(PlaybackStateCompat state) {
            // 最终回调到Client 的 MediaControllerCallback.onPlaybackStateChanged
            mMediaSessionCompat.setPlaybackState(state);

            // Manage the started state of this service.
            switch (state.getState()) {
                case PlaybackStateCompat.STATE_PLAYING:
                    mServiceManager.moveServiceToStartedState(state);
                    break;
                case PlaybackStateCompat.STATE_PAUSED:
                    mServiceManager.updateNotificationForPause(state);
                    break;
                case PlaybackStateCompat.STATE_STOPPED:
                    mServiceManager.moveServiceOutOfStartedState(state);
                    break;
            }
        }

        @Override
        public void onPlaybackCompleted() {

        }

        class ServiceManager {
            /**
             * @param state
             */
            private void moveServiceToStartedState(PlaybackStateCompat state) {
                //
                Notification notification =
                        mMediaNotificationManager.getNotification(
                                mMediaPlayerManager.getCurrentMedia(), state, getSessionToken());
                //
                if (!mServiceInStartedState) {
                    ContextCompat.startForegroundService(
                            MusicService.this,
                            new Intent(MusicService.this, MusicService.class));
                    mServiceInStartedState = true;
                }
                //
                startForeground(MediaNotificationManager.Companion.getNOTIFICATION_ID(), notification);
            }

            /**
             * @param state
             */
            private void updateNotificationForPause(PlaybackStateCompat state) {
                stopForeground(false);
                Notification notification =
                        mMediaNotificationManager.getNotification(
                                mMediaPlayerManager.getCurrentMedia(), state, getSessionToken());
                mMediaNotificationManager.getNotificationManager()
                        .notify(MediaNotificationManager.Companion.getNOTIFICATION_ID(), notification);
            }

            /**
             * @param state
             */
            private void moveServiceOutOfStartedState(PlaybackStateCompat state) {
                stopForeground(true);
                stopSelf();
                mServiceInStartedState = false;
            }
        }

    }

}