package com.shetj.diyalbume.playVideo.media

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.media.MediaBrowserServiceCompat
import androidx.media.session.MediaButtonReceiver
import timber.log.Timber
import android.support.v4.media.session.MediaControllerCompat
import androidx.annotation.NonNull
import com.shetj.diyalbume.pipiti.localMusic.Music
import com.shetj.diyalbume.playVideo.media.contentcatalogs.MetadataUtil
import java.util.concurrent.TimeUnit


/**
 * [MediaBrowserServiceCompat]
 * Android多媒体播放采用client，server架构，一个server可以对应多个client，
 * client在使用的时候需要先连接到server，双方通过设置的一些callback来进行状态的同步。
 *
 * [MediaSessionCompat] 回调，会话:控制着播放器的播放，
 * [MediaControllerCompat] 来控制着UI的变化。
 * [PlaybackStateCompat] 播放状态回调
 * [androidx.media.app.NotificationCompat.MediaStyle] 播放音乐的style
 * [NotificationCompat] 通知栏的创建
 *
 */
class MediaService : MediaBrowserServiceCompat() {

    private var mMediaSession:MediaSessionCompat?=null //会话，用来回调
    private var mPlaybackState:PlaybackStateCompat ?=null //

    override fun onCreate() {
        super.onCreate()

        mMediaSession = MediaSessionCompat(this,"mediaService")
        //2. 设置回调
        mMediaSession?.setCallback(sessionCallback)//设置回调
        //3. 开启 MediaButton 和 TransportControls 的支持
        mMediaSession?.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS)
        //设置Token 后会触发MediaBrowserCompat.connectionCallBack的回调方法


        //4. 初始化 PlaybackState
        mPlaybackState = PlaybackStateCompat
                .Builder()
                .setActions(PlaybackStateCompat.ACTION_PLAY or  PlaybackStateCompat.ACTION_PLAY_PAUSE)
                .setState(PlaybackStateCompat.STATE_NONE, 0, 1.0f)
                .build()
        mMediaSession?.setPlaybackState(mPlaybackState)
        //触发成功表示MediaBrowser和MediaBrowserService连接成功
        sessionToken = mMediaSession?.sessionToken
    }


    //控制客户端媒体浏览器的连接请求，通过返回值决定是否允许该客户端连接服务
    override fun onGetRoot(clientPackageName: String, clientUid: Int, rootHints: Bundle?): BrowserRoot? {
        Timber.i("onGetRoot ---- ")
        return BrowserRoot("root", null)
    }

    /**
     * 通过parentId 获取不同音乐列表
     */
    override fun onLoadChildren(parentId: String, result: Result<MutableList<MediaBrowserCompat.MediaItem>>) {
        Timber.i("onLoadChildren ---- ")
        //通过不同的parentId,获取不同的列表
        when(parentId)
        {
            "ID" ->{
                MusicUtils.loadFileData(this).map {
                    val mediaItems = ArrayList<MediaBrowserCompat.MediaItem>()
                    it.apply {
                        forEach {
                            mediaItems.add(createMediaItemAlbum(it))
                        }
                    }
                    mediaItems
                }.subscribe({
                    result.sendResult(it)
                },{
                    Timber.e(it)
                })
            }
        }
    }

    /**
     * 自定义数据获取内容
     * mMediaBrowser.sendCustomAction
     */
    override fun onCustomAction(action: String, extras: Bundle?, result: Result<Bundle>) {
        super.onCustomAction(action, extras, result)
    }

    @NonNull
    private fun createMediaItemAlbum(@NonNull music: Music): MediaBrowserCompat.MediaItem {
            val mediaMetadataCompat = MetadataUtil.getMediaMetadataCompat(mediaId = music.name!!,
                    album = music.img!!,
                    duration = music.duration,
                    durationUnit = TimeUnit.MILLISECONDS,
                    genre = "1",
                    title = music.name!!)
        return MediaBrowserCompat.MediaItem(mediaMetadataCompat.description, MediaBrowserCompat.MediaItem.FLAG_PLAYABLE)
    }

    /**
     * MediaController.getTransportControls().play
     * 响应控制器指令的回调
     */
    private val sessionCallback = object :MediaSessionCompat.Callback(){
        private val mPlaylist = java.util.ArrayList<MediaSessionCompat.QueueItem>()
        private var mQueueIndex = -1
        override fun onPause() {
            super.onPause()
        }

        override fun onPrepare() {
            super.onPrepare()
        }

        override fun onPlay() {
            super.onPlay()
        }

    }


    companion object {

        /**
         * 通过[MediaSessionCompat]获取 [NotificationCompat.Builder]
         */
        @JvmStatic
        fun getNotification(context: Context, mediaSession: MediaSessionCompat): NotificationCompat.Builder {
            val builder = NotificationCompat.Builder(context, createNotificationChannel(context))
            val controller = mediaSession.controller
            val mediaMetadata = controller.metadata
            val description = mediaMetadata.description
            getMediaStyle(mediaSession, context)
            builder.setStyle(getMediaStyle(mediaSession, context))
                    .setContentTitle(description.title)
                    .setContentText(description.subtitle)
                    .setSubText(description.description)
                    .setLargeIcon(description.iconBitmap)
                    .setContentIntent(controller.sessionActivity)
                    .setDeleteIntent(MediaButtonReceiver.buildMediaButtonPendingIntent(context, PlaybackStateCompat.ACTION_STOP))
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .addAction(NotificationCompat.Action(android.R.drawable.ic_media_previous,
                            "previous", MediaButtonReceiver.buildMediaButtonPendingIntent(
                            context, PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS)))
                    .addAction(NotificationCompat.Action(android.R.drawable.ic_media_pause,
                            "pause", MediaButtonReceiver.buildMediaButtonPendingIntent(
                            context, PlaybackStateCompat.ACTION_PAUSE)))
                    .addAction(NotificationCompat.Action(android.R.drawable.ic_media_next,
                            "next", MediaButtonReceiver.buildMediaButtonPendingIntent(
                            context, PlaybackStateCompat.ACTION_SKIP_TO_NEXT)))
            return builder
        }

        private fun getMediaStyle(mediaSession: MediaSessionCompat, context: Context): androidx.media.app.NotificationCompat.MediaStyle? {
            return androidx.media.app.NotificationCompat.MediaStyle()
                    .setMediaSession(mediaSession.sessionToken)
                    .setShowCancelButton(true)
                    .setCancelButtonIntent(
                            MediaButtonReceiver.buildMediaButtonPendingIntent(
                                    context, PlaybackStateCompat.ACTION_STOP))
        }

        @JvmStatic
        fun notify(context: Context, notification: Notification) {
            NotificationManagerCompat.from(context).notify("mediaService".hashCode(), notification)
        }

        @JvmStatic
        fun cancel(context: Context) {
            NotificationManagerCompat.from(context).cancel("mediaService".hashCode())
        }

        /**
         * 8.0 通知栏，创建通知栏渠道ID
         */
        @JvmStatic
        fun createNotificationChannel(context: Context): String {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channelId ="shetj"
                val channelName = "shetj"
                val channelDescription = "shetj"
                val channelImportance = NotificationManager.IMPORTANCE_LOW
                val notificationChannel = NotificationChannel(channelId, channelName, channelImportance)
                // 该渠道的通知是否使用震动
                notificationChannel.enableVibration(false)
                // 不要呼吸灯
                notificationChannel.enableLights(false)
                // 设置描述 最长30字符
                notificationChannel.description = channelDescription
                // 不要铃声
                notificationChannel.vibrationPattern = longArrayOf(0)
                // 设置显示模式
                notificationChannel.lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
                notificationChannel.setSound(null, null)
                val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(notificationChannel)
                return channelId
            } else {
                return ""
            }
        }
    }

}