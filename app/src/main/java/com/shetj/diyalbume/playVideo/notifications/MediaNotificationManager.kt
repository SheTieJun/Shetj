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

package com.shetj.diyalbume.playVideo.notifications


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log

import com.shetj.diyalbume.R
import com.shetj.diyalbume.main.view.MainActivity
import com.shetj.diyalbume.playVideo.contentcatalogs.MusicLibrary
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.media.session.MediaButtonReceiver
import timber.log.Timber

/**
 * Keeps track of a notification and updates it automatically for a given MediaSession. This is
 * required so that the music service don't get killed during playback.
 */
class MediaNotificationManager(private val mContext: Context) {


    private val mPlayAction: NotificationCompat.Action
    private val mPauseAction: NotificationCompat.Action
    private val mNextAction: NotificationCompat.Action
    private val mPrevAction: NotificationCompat.Action
    private  val notificationManager: NotificationManager

    private val isAndroidOOrHigher: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

    init {
        // NotificationManager
        notificationManager = mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        /**
         *
         */
        mPlayAction = NotificationCompat.Action(
                R.drawable.ic_play_arrow_white_24dp,
                mContext.getString(R.string.label_play),
                MediaButtonReceiver.buildMediaButtonPendingIntent(mContext, PlaybackStateCompat.ACTION_PLAY))
        mPauseAction = NotificationCompat.Action(
                R.drawable.ic_pause_white_24dp,
                mContext.getString(R.string.label_pause),
                MediaButtonReceiver.buildMediaButtonPendingIntent(
                        mContext,
                        PlaybackStateCompat.ACTION_PAUSE))
        mNextAction = NotificationCompat.Action(
                R.drawable.ic_skip_next_white_24dp,
                mContext.getString(R.string.label_next),
                MediaButtonReceiver.buildMediaButtonPendingIntent(
                        mContext,
                        PlaybackStateCompat.ACTION_SKIP_TO_NEXT))
        mPrevAction = NotificationCompat.Action(
                R.drawable.ic_skip_previous_white_24dp,
                mContext.getString(R.string.label_previous),
                MediaButtonReceiver.buildMediaButtonPendingIntent(
                        mContext,
                        PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS))

        // Cancel all notifications to handle the case where the Service was killed and
        // restarted by the system.
        notificationManager.cancelAll()
    }// MediaBrowserService

    fun onDestroy() {
        Timber.d( "onDestroy: ")
    }

    fun getNotification(metadata: MediaMetadataCompat,
                        state: PlaybackStateCompat,
                        token: MediaSessionCompat.Token): Notification {
        val isPlaying = state.state == PlaybackStateCompat.STATE_PLAYING
        val description = metadata.description
        val builder = buildNotification(state, token, isPlaying, description)
        return builder.build()
    }

    private fun buildNotification(state: PlaybackStateCompat,
                                  token: MediaSessionCompat.Token,
                                  isPlaying: Boolean,
                                  description: MediaDescriptionCompat): NotificationCompat.Builder {

        // Create the (mandatory) notification channel when running on Android Oreo.
        if (isAndroidOOrHigher) {
            createChannel()
        }

        val builder = NotificationCompat.Builder(mContext, CHANNEL_ID)
        builder.setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                        .setMediaSession(token)
                        .setShowActionsInCompactView(0, 1, 2)
                        // For backwards compatibility with Android L and earlier.
                        .setShowCancelButton(true)
                        .setCancelButtonIntent(
                                MediaButtonReceiver.buildMediaButtonPendingIntent(
                                        mContext,
                                        PlaybackStateCompat.ACTION_STOP)))
                .setColor(ContextCompat.getColor(mContext, R.color.notification_bg))
                .setSmallIcon(R.drawable.ic_stat_image_audiotrack)
                .setContentIntent(createContentIntent())
                .setContentTitle(description.title)
                .setContentText(description.subtitle)
                .setLargeIcon(MusicLibrary.getAlbumBitmap(mContext, description.mediaId))
                .setDeleteIntent(MediaButtonReceiver.buildMediaButtonPendingIntent(
                        mContext, PlaybackStateCompat.ACTION_STOP))
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

        if (state.actions and PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS != 0L) {
            builder.addAction(mPrevAction)
        }
        builder.addAction(if (isPlaying) mPauseAction else mPlayAction)
        if (state.actions and PlaybackStateCompat.ACTION_SKIP_TO_NEXT != 0L) {
            builder.addAction(mNextAction)
        }

        return builder
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        if (notificationManager.getNotificationChannel(CHANNEL_ID) == null) {
            val name = "MediaSession"
            val description = "MediaSession and MediaPlayer"
            val importance = NotificationManager.IMPORTANCE_LOW
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            mChannel.description = description
            mChannel.enableLights(true)
            mChannel.lightColor = Color.RED
            mChannel.enableVibration(true)
            notificationManager.createNotificationChannel(mChannel)
            Timber.d( "createChannel: New channel created")
        } else {
            Timber.d( "createChannel: Existing channel reused")
        }
    }

    private fun createContentIntent(): PendingIntent {
        val openUI = Intent(mContext, MainActivity::class.java)
        openUI.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        return PendingIntent.getActivity(
                mContext, REQUEST_CODE, openUI, PendingIntent.FLAG_CANCEL_CURRENT)
    }

    companion object {
        val NOTIFICATION_ID = 412
        private val CHANNEL_ID = "com.shetj.diyalbume.playVideo"
        private val REQUEST_CODE = 501
    }

}