package com.shetj.diyalbume.nitification

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.view.View
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.shetj.diyalbume.R
import com.shetj.diyalbume.nitification.MediaControlReceiver.Companion.NOTIFICATION_ITEM_BUTTON_CLEAR
import com.shetj.diyalbume.nitification.MediaControlReceiver.Companion.NOTIFICATION_ITEM_BUTTON_OPEN
import com.shetj.diyalbume.nitification.MediaControlReceiver.Companion.NOTIFICATION_ITEM_BUTTON_PLAY
import me.shetj.base.tools.app.Utils


object MusicNotification {

    @SuppressLint("ObsoleteSdkInt")
    fun notify(context: Context) {
                notify(context,1)
//                notify(context,2)
//                notify(context,3)
    }


    @SuppressLint("ObsoleteSdkInt")
    fun notify(context: Context,type : Int) {
        //获取当前播放的数据，没有直接返回，不展示通知栏
//            notify(context,4,null)
        //可能图片没有下载好，先不展示
        notify(context, type, null)


    }


    /**
     * @param type
     *  音频
     *  [type]== 1 表示 正在播放   [type] == 2 表示 正在下载  [type] == 3 表示 暂停播放
     *  没有课程
     *  [type] ==4 表示[BgPlayerService]服务的通知栏
     *  视频
     *  [type]== 11 表示 正在播放 [type] == 33 表示 暂停播放
     * @param bitmap 展示图片
     */
    @SuppressLint("ObsoleteSdkInt")
    fun notify(context: Context,type:Int,bitmap: Bitmap?) {
        notify(context, when(type){
            4 -> createBgNotif(context)
            else ->createNotif(context, bitmap, type)
        })
    }



    fun createBgNotif(context: Context): Notification {
        val intents =
                context.packageManager.getLaunchIntentForPackage(context.packageName)
        val pendingIntent= PendingIntent.getActivity(Utils.app, 0, intents,
                PendingIntent.FLAG_UPDATE_CURRENT)
        val  builder = NotificationCompat.Builder(Utils.app, createNotificationChannel(Utils.app))
        builder.setSound(null)
                .setLargeIcon(BitmapFactory.decodeResource(Utils.app.resources, R.mipmap.shetj_logo))
                .setDefaults(NotificationCompat.FLAG_ONLY_ALERT_ONCE)
                .setOngoing(true)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setContentTitle("shetj")
                .setContentText("成长每时每刻")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.shetj_logo)

        return builder.build()
    }

    private fun createNotif(context: Context, bitmap: Bitmap?, type: Int): Notification {
        val remoteViews = getCustomView(context)
        if (bitmap != null) {
            //设置图片
            remoteViews?.setImageViewBitmap(R.id.iv_lecture, bitmap)
        }
        //打开课程
        val openIntent = Intent(NOTIFICATION_ITEM_BUTTON_OPEN)
        openIntent.component = ComponentName("com.lizhiweike", "com.lizhiweike.player.receiver.MediaControlReceiver")
        val pendIntentOpen = PendingIntent.getBroadcast(context, 0, openIntent, 0)
        remoteViews?.setOnClickPendingIntent(R.id.iv_lecture, pendIntentOpen)
        remoteViews?.setOnClickPendingIntent(R.id.tv_title, pendIntentOpen)
        //音频播放或者暂停
        val playOrPauseIntent = Intent(NOTIFICATION_ITEM_BUTTON_PLAY)
        playOrPauseIntent.component = ComponentName("com.lizhiweike", "com.lizhiweike.player.receiver.MediaControlReceiver")
        val pendIntentPlayOrPause = PendingIntent.getBroadcast(context, 1, playOrPauseIntent, 0)
        remoteViews?.setOnClickPendingIntent(R.id.audioBtnLayout, pendIntentPlayOrPause)

            //
            remoteViews?.setTextViewText(R.id.tv_title, "设置标题")

        //清除，并且暂停
        val clearIntent = Intent(NOTIFICATION_ITEM_BUTTON_CLEAR)
        clearIntent.component = ComponentName("com.lizhiweike", "com.lizhiweike.player.receiver.MediaControlReceiver")
        val pendIntentClear = PendingIntent.getBroadcast(context, 0, clearIntent, 0)
        remoteViews?.setOnClickPendingIntent(R.id.iv_del, pendIntentClear)
        when (type) {
            1,11 -> {
                showLoading(remoteViews, false) //播放中
                remoteViews?.setImageViewResource(R.id.iv_play, R.mipmap.shetj_logo)
            }
            2 -> {
                showLoading(remoteViews, true) //加载中
            }
            3,33 -> {
                showLoading(remoteViews, false) //暂停中
                remoteViews?.setImageViewResource(R.id.iv_play, R.mipmap.shetj_logo)
            }
        }

        val builder = NotificationCompat.Builder(context, createNotificationChannel(context))
                .setSmallIcon(R.mipmap.shetj_logo)
                .setOngoing(true)
                .setShowWhen(true)
                .setContentIntent(pendIntentOpen)
                .setContent(remoteViews)
                .setSound(null)
                .setDefaults(NotificationCompat.FLAG_ONLY_ALERT_ONCE)
                .setVibrate(longArrayOf(0))
                .setCustomContentView(remoteViews)
                .setColor(ContextCompat.getColor(context.applicationContext, R.color.colorPrimary))
                .setPriority(NotificationCompat.PRIORITY_MAX)

        return builder.build()
    }

    //是否展示加载中...
    private fun showLoading(remoteViews: RemoteViews?, isShow: Boolean) {
        if (isShow){
            remoteViews?.setViewVisibility(R.id.iv_play, View.GONE)
            remoteViews?.setViewVisibility(R.id.audioLoading, View.VISIBLE)
        }else{
            remoteViews?.setViewVisibility(R.id.iv_play, View.VISIBLE)
            remoteViews?.setViewVisibility(R.id.audioLoading, View.GONE)
        }
    }

    private fun getCustomView(context: Context): RemoteViews? {
        return   RemoteViews(context.packageName,R.layout.notification_globla_media_control_layout)
    }


    fun notify(context: Context, notification: Notification) {
        NotificationManagerCompat.from(context).notify("后台播放器".hashCode(), notification)
    }

    fun cancel(context: Context) {
        NotificationManagerCompat.from(context).cancel("后台播放器".hashCode())
    }

    fun NotificationManagerCompat.cancelAll(){
        cancelAll()
    }

    /**
     * 8.0 通知栏，创建通知栏渠道ID
     */
    private fun createNotificationChannel(context: Context): String {
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
