package com.shetj.diyalbume.aidltest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.shetj.diyalbume.IMyAidlInterface;

public class MyService extends Service {
	public MyService() {
	}

	@Override
	public IBinder onBind(Intent intent) {
		return new MyBinder();
	}

	class MyBinder extends IMyAidlInterface.Stub
	{

		@Override
		public String getName() {
			return "AIDL-test：getPackageName:"+getPackageName();
		}
	}

}
