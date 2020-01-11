package com.shetj.diyalbume.nitification


import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import java.lang.reflect.Method


class MediaControlReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (TextUtils.isEmpty(action)) {
            return
        }
        when (action) {
            NOTIFICATION_ITEM_BUTTON_LAST -> {
            }
            NOTIFICATION_ITEM_BUTTON_PLAY -> {
            }
            NOTIFICATION_ITEM_BUTTON_NEXT -> {
            }
            NOTIFICATION_ITEM_BUTTON_CLEAR -> MusicNotification.cancel(context)
            NOTIFICATION_ITEM_BUTTON_OPEN -> collapseStatusBar(context)
            else -> {
            }
        }
    }

    /**
     *   收起通知栏
     *   
     */
    fun collapseStatusBar(context: Context) {
        try {
            @SuppressLint("WrongConstant")
            val statusBarManager = context.getSystemService("statusbar")
            val collapse: Method
            collapse = statusBarManager.javaClass.getMethod("collapsePanels")
            collapse.invoke(statusBarManager)
        } catch (localException: Exception) {
            localException.printStackTrace()
        }

    }

    companion object {

        val NOTIFICATION_ITEM_BUTTON_LAST = "com.lizhiweike.player.receiver.MediaControlReceiver.last"//----通知栏上一首按钮
        val NOTIFICATION_ITEM_BUTTON_PLAY = "com.lizhiweike.player.receiver.MediaControlReceiver.play"//----通知栏播放按钮
        val NOTIFICATION_ITEM_BUTTON_NEXT = "com.lizhiweike.player.receiver.MediaControlReceiver.next"//----通知栏下一首按钮
        val NOTIFICATION_ITEM_BUTTON_CLEAR = "com.lizhiweike.player.receiver.MediaControlReceiver.clear"//----暂停并且取消通知
        val NOTIFICATION_ITEM_BUTTON_OPEN = "com.lizhiweike.player.receiver.MediaControlReceiver.open"//----打开通知
    }
}