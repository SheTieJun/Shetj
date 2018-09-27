package me.shetj.bdmap;

import android.content.Context;

import com.baidu.mapapi.SDKInitializer;

/**
 * <b>@packageName：</b> com.ebu.baidu<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2018/2/27<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */

public class BMapManager {
	public static  void init(Context context){
		SDKInitializer.initialize(context.getApplicationContext());
	}



}
