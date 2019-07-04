package com.shetj.components.user;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * <b>@packageName：</b> com.shetj.components.user<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2018/1/2<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */

public class UserObserver implements LifecycleObserver {


	public UserObserver() {

	}

	@OnLifecycleEvent(value = Lifecycle.Event.ON_RESUME)
	public void startListener(){

	}

	@OnLifecycleEvent(value =  Lifecycle.Event.ON_PAUSE)
	public void endListener(){

	}


}
