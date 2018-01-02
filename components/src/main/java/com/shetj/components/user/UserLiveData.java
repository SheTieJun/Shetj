package com.shetj.components.user;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.MainThread;

import com.shetj.components.db.User;

/**
 * <b>@packageName：</b> com.shetj.components.user<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2018/1/2<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */

public class UserLiveData extends LiveData<User> {

	private  static  UserLiveData mUserLiveData;


	private UserLiveData(Context context) {
	}

	@MainThread
	public static UserLiveData get(Context context) {
		if (mUserLiveData == null) {
			mUserLiveData = new UserLiveData(context.getApplicationContext());
		}
		return mUserLiveData;
	}


	@Override
	protected void onActive() {
		super.onActive();
	}

	@Override
	protected void onInactive() {
		super.onInactive();
	}
}
