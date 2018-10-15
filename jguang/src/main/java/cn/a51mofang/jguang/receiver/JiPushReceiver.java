package cn.a51mofang.jguang.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import cn.jpush.android.api.JPushInterface;

/**
 * @author shetj<br>
 */


/**
 * 自定义JPush接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 * <p>
 * Created by zcj on 2016/11/28.
 */

public class JiPushReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		assert bundle != null;
		Log.i("JiPushReceiver","[JPushReceiver] onReceive - " + intent.getAction() + ", title: " + bundle.getString(JPushInterface.EXTRA_TITLE));
		Log.i("JiPushReceiver", "[JPushReceiver] onReceive - " + intent.getAction() + ", extras: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
		Log.i("JiPushReceiver", "[JPushReceiver] onReceive - " + intent.getAction() + ", message: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
		Log.i("JiPushReceiver", "[JPushReceiver] onReceive - " + intent.getAction() + ", content_type: " + bundle.getString(JPushInterface.EXTRA_CONTENT_TYPE));
		Log.i( "JiPushReceiver","[JPushReceiver] onReceive - " + intent.getAction() + ", app_key: " + bundle.getString(JPushInterface.EXTRA_APP_KEY));
		Log.i("JiPushReceiver", "[JPushReceiver] onReceive - " + intent.getAction() + ", msg_id: " + bundle.getString(JPushInterface.EXTRA_MSG_ID));

		if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
			String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
			Log.i("JiPushReceiver","[JPushReceiver] 接收Registration Id : " + regId);

		} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
			Log.i("JiPushReceiver","[JPushReceiver] 接收到推送下来的自定义消息");


		} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
			Log.i("JiPushReceiver","[JPushReceiver] ACTION_NOTIFICATION_RECEIVED");

		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {

		} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
			Log.i("JiPushReceiver","[JPushReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));

		} else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
			boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
			Log.i("JiPushReceiver","[JPushReceiver]" + intent.getAction() + " connected state change to " + connected);
		} else {
			Log.i("JiPushReceiver","[JPushReceiver] Unhandled intent - " + intent.getAction());
		}
	}


}
