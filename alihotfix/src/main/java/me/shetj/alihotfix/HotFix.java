package me.shetj.alihotfix;

import android.app.Application;
import android.content.Context;
import android.support.annotation.Keep;
import android.util.Log;

import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixEntry;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.a.e;
import com.taobao.sophix.listener.PatchLoadStatusListener;

import timber.log.Timber;

/**
 * <b>@packageName：</b> me.shetj.alihotfix<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2018/9/13 0013<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */
public class HotFix {


	public static void onCreateInit(){
		((e)SophixManager.getInstance()).a();
	}

	public static void init(Application app,boolean isDebug,String AppSecret){
		String appVersion = "0.0.0";
		try {
			appVersion = app.getPackageManager()
							.getPackageInfo(app.getPackageName(), 0)
							.versionName;
		} catch (Exception ignored) {

		}
		final SophixManager instance = SophixManager.getInstance();
		instance.setContext(app)
						.setAppVersion(appVersion)
						.setSecretMetaData(null, AppSecret, null)
						.setEnableDebug(isDebug)
						.setEnableFullLog()
						.setPatchLoadStatusStub(new PatchLoadStatusListener() {
							@Override
							public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
								Timber.i("mode = " +mode +"\n"
								+ "code = " +code +"\n info = " +info +"\n handlePatchVersion = " + handlePatchVersion);
								if (code == PatchStatus.CODE_LOAD_SUCCESS) {
									Timber.i( "sophix load patch success!");
								} else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
									// 如果需要在后台重启，建议此处用SharePreference保存状态。
									Timber.i("sophix preload patch success. restart app to make effect.");
								}
							}
						}).initialize();
	}

	public static  void softKill(){
		SophixManager.getInstance().killProcessSafely();
	}

}
