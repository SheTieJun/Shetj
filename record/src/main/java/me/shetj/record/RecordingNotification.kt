package me.shetj.record

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.content.ContextCompat
import me.shetj.record.activity.MyRecordActivity


/**
 * 录音通知栏
 */
object RecordingNotification {

    @SuppressLint("ObsoleteSdkInt")
    fun notify(context: Context) {
        //默认没有播放 需要用户点击开始播放
        notify(context,3)
    }


    @SuppressLint("ObsoleteSdkInt")
    fun notify(context: Context,type : Int) {
        //可能图片没有下载好，先不展示
        notify(context,type,null)
    }


    /**
     * @param type  [type]== 1 表示 正在录音   [type] == 2 表示 暂停录音  [type] == 3 表示 完成录音
     * @param bitmap 展示图片
     */
    @SuppressLint("ObsoleteSdkInt")
    fun notify(context: Context,type:Int,bitmap: Bitmap?) {

        val notification = getNotification(type, context)
        notify(context, notification)
    }

    fun getNotification(type: Int, context: Context): Notification {
        val content = when (type) {
            1 -> "正在录音..."
            2 -> "录音已暂停"
            else -> "录音已完成"
        }

        val intent = Intent(context, MyRecordActivity::class.java)
//         点击跳转到主界面
        val intentGo = PendingIntent.getActivity(context, 1, intent,
                PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(context, createNotificationChannel(context))
                .setSmallIcon(R.mipmap.record)
                .setContentTitle("录音机")
                .setContentText(content)
                .setOngoing(true)
                .setContentIntent(intentGo)
                .setSound(null)
                .setDefaults(NotificationCompat.FLAG_ONLY_ALERT_ONCE)
                .setVibrate(longArrayOf(0))
                .setColor(ContextCompat.getColor(context.applicationContext, R.color.colorPrimary))
                .setPriority(NotificationCompat.PRIORITY_MAX)
        return builder.build()
    }


    private fun notify(context: Context, notification: Notification) {
        NotificationManagerCompat.from(context).notify("后台播放器".hashCode(), notification)
    }

    /**
     * Cancels any notifications of this type previously shown using
     * [.notify].
     */
    fun cancel(context: Context) {
        NotificationManagerCompat.from(context).cancel("record".hashCode())
    }

    /**
     * 创建通知栏渠道ID
     */
    private fun createNotificationChannel(context: Context): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId ="录音机"
            val channelName = "录音机"
            val channelDescription = "录音机"
            val channelImportance = NotificationManager.IMPORTANCE_DEFAULT

            val notificationChannel = NotificationChannel(channelId, channelName, channelImportance)
            // 设置描述 最长30字符
            notificationChannel.description = channelDescription
            // 该渠道的通知是否使用震动
            notificationChannel.enableVibration(false)
            // 设置显示模式
            notificationChannel.lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
            // 不要呼吸灯
            notificationChannel.enableLights(false)
            // 不要铃声
            notificationChannel.vibrationPattern = longArrayOf(0)

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)

            return channelId
        } else {
            return ""
        }
    }



}
