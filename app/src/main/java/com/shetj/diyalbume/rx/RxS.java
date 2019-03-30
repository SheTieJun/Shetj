package com.shetj.diyalbume.rx;

import com.shetj.diyalbume.test.Test;

import io.reactivex.Observable;
import me.shetj.base.tools.json.GsonKit;

/**
 * <b>@packageName：</b> com.shetj.diyalbume.rx<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2019/3/15 0015<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */
public class RxS {
	public static Observable Login(String name,String password){
		return new LoginObservable(name,password);
	}

	public  static Observable checkMsg(String msg){
		return new CheckInfoObservable(msg);
	}
}
