package com.shetj.components;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.shetj.components.db.UserDataBase;

import org.xutils.x;

/**
 * <b>@packageName：</b> com.shetj.components<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2018/1/2<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */

public class APP extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		x.Ext.init(this);
		x.Ext.setDebug(true);

	}


}
