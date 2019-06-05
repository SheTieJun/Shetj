package com.shetj.diyalbume.nitification

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.content.ContextCompat
import android.widget.RemoteViews
import com.shetj.diyalbume.R
import com.shetj.diyalbume.main.view.MainActivity


object MusicNotification {


    private var remoteViews: RemoteViews? = null

    @SuppressLint("ObsoleteSdkInt")
    fun notify(context: Context,
               exampleString: String) {
        val res = context.resources
        val picture = BitmapFactory.decodeResource(res, R.drawable.example_picture)
        val intent = Intent(context, MainActivity::class.java)


        remoteViews = getCustomView(context)

        // 点击跳转到主界面
        val intentGo = PendingIntent.getActivity(context, 5, intent,
                PendingIntent.FLAG_UPDATE_CURRENT)
        remoteViews?.setOnClickPendingIntent(R.id.iv_lecture, intentGo)

        // 4个参数context, requestCode, intent, flags
        val intentClose = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT)
        remoteViews?.setOnClickPendingIntent(R.id.iv_del, intentClose)

//        // 设置上一曲
//        val prv = Intent()
//        prv.action = "ACTION_PRV"
//        val intent_prev = PendingIntent.getBroadcast(context, 1, prv,
//                PendingIntent.FLAG_UPDATE_CURRENT)
//        remoteViews?.setOnClickPendingIntent(R.id.audioBtnLayout, intent_prev)

        val playOrPause =   Intent()
        playOrPause.action = "ACTION_PAUSE"
        val intentPlay = PendingIntent.getBroadcast(context, 2,
                playOrPause, PendingIntent.FLAG_UPDATE_CURRENT)
        remoteViews?.setOnClickPendingIntent(R.id.audioBtnLayout, intentPlay)

        val builder = NotificationCompat.Builder(context,createNotificationChannel(context))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setOngoing(true)
                .setTicker("开始播放啦~~")
                .setContentIntent(intentGo)
                .setContent(remoteViews)
                .setCustomContentView(remoteViews)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setColor(ContextCompat.getColor(context.applicationContext, R.color.colorPrimary))
                .setPriority(NotificationCompat.PRIORITY_MAX)
        val notification = builder.build()
        notify(context, notification)



    }

    private fun getCustomView(context: Context): RemoteViews? {
        return   RemoteViews(context.packageName,R.layout.notification_globla_media_control_layout)
    }

    @TargetApi(Build.VERSION_CODES.ECLAIR)
    private fun notify(context: Context, notification: Notification) {
        NotificationManagerCompat.from(context).notify(1, notification)
    }

    /**
     * Cancels any notifications of this type previously shown using
     * [.notify].
     */
    @TargetApi(Build.VERSION_CODES.ECLAIR)
    fun cancel(context: Context) {
        NotificationManagerCompat.from(context).cancel(1)
    }

    private fun createNotificationChannel(context: Context): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId ="GlobalMediaControl"
            val channelName = "GlobalMediaControl"
            val channelDescription = "GlobalMediaControlDescription"
            val channelImportance = NotificationManager.IMPORTANCE_DEFAULT

            val notificationChannel = NotificationChannel(channelId, channelName, channelImportance)
            // 设置描述 最长30字符
            notificationChannel.description = channelDescription
            // 该渠道的通知是否使用震动
            notificationChannel.enableVibration(false)
            // 设置显示模式
            notificationChannel.lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)

            return channelId
        } else {
            return ""
        }
    }


}
