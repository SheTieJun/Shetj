package me.shetj.router;

import android.app.Application;
import android.net.Uri;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * <b>@packageName：</b> me.shetj.router<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2018/9/30 0030<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */
public class RouterUtils {
	private RouterUtils(){
		throw new UnsupportedOperationException("u can't instantiate me...");
	}

	/**
	 * 初始化
	 * @param application
	 * @param isDebug
	 */
	public static void initRouter(Application application,boolean isDebug){
		if (isDebug ) {
			ARouter.openLog();
			ARouter.openDebug();
		}
		ARouter.init(application);
	}

	/**
	 * 打开界面
	 * @param uri
	 */
	public static void startOpen(Uri uri){
		ARouter.getInstance().build(uri).navigation();
	}

	/**
	 * 打开界面
	 * @param path
	 */
	public static void startOpen(String path ){
		ARouter.getInstance().build(path).navigation();
	}
}
