package com.shetj.diyalbume.nitification;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;


import java.lang.reflect.Method;

public class MediaControlReceiver extends BroadcastReceiver {


	public MediaControlReceiver() {
	}

	public static final String NOTIFICATION_ITEM_BUTTON_LAST = "com.lizhiweike.player.receiver.MediaControlReceiver.last";//----通知栏上一首按钮
	public static final String NOTIFICATION_ITEM_BUTTON_PLAY = "com.lizhiweike.player.receiver.MediaControlReceiver.play";//----通知栏播放按钮
	public static final String NOTIFICATION_ITEM_BUTTON_NEXT = "com.lizhiweike.player.receiver.MediaControlReceiver.next";//----通知栏下一首按钮
	public static final String NOTIFICATION_ITEM_BUTTON_CLEAR = "com.lizhiweike.player.receiver.MediaControlReceiver.clear";//----暂停并且取消通知
	public static final String NOTIFICATION_ITEM_BUTTON_OPEN = "com.lizhiweike.player.receiver.MediaControlReceiver.open";//----打开通知

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (TextUtils.isEmpty(action)) {
			return;
		}
		switch (action) {
			case NOTIFICATION_ITEM_BUTTON_LAST:
				break;
			case NOTIFICATION_ITEM_BUTTON_PLAY:
				break;
			case NOTIFICATION_ITEM_BUTTON_NEXT:
				break;
			case NOTIFICATION_ITEM_BUTTON_CLEAR:
				MusicNotification.INSTANCE.cancel(context);
				break;
			case NOTIFICATION_ITEM_BUTTON_OPEN:
				collapseStatusBar(context);
				break;
			default:
				break;
		}
	}

	/**
	 *   收起通知栏
  
	 */
	public void collapseStatusBar(Context context) {
		try {
			@SuppressLint("WrongConstant")
			Object statusBarManager = context.getSystemService("statusbar");
			Method collapse;
			if (Build.VERSION.SDK_INT <= 16) {
				collapse = statusBarManager.getClass().getMethod("collapse");
			} else {
				collapse = statusBarManager.getClass().getMethod("collapsePanels");
			}
			collapse.invoke(statusBarManager);
		} catch (Exception localException) {
			localException.printStackTrace();
		}

	}
}