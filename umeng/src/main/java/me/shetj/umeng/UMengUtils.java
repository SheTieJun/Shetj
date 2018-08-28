package me.shetj.umeng;

import android.app.Application;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;

/**
 * <b>@packageName：</b> me.shetj.umeng<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2018/4/9 0009<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b> 友盟 相关配置<br>
 */
public class UMengUtils {
	private UMengUtils() {
	}
	private LoginUtils loginUtils;
	private ShareUtils shareUtils;

	private static final UMengUtils ourInstance = new UMengUtils();

	public static UMengUtils getInstance() {
		return ourInstance;
	}

	/**
	 * 初始化
	 * @param application Application
	 * @param isDebug 是否显示bug
	 * @param version 发行版本
	 * @param appkey umeng 的appkey
	 * @param wx wx[0] appId,wx[1] appKey
	 * @param qq qq[0] appId,qq[1] appKey
	 */
	public  void init(Application application,
	                  boolean isDebug,
	                  String version,
	                  String appkey,
	                  String[] wx,
	                  String[] qq){
		UMConfigure.setLogEnabled(isDebug);
		UMConfigure.init(application, appkey, version, UMConfigure.DEVICE_TYPE_PHONE, null);
		UMShareAPI.init(application, appkey);
		PlatformConfig.setWeixin(wx[0], wx[1]);
		PlatformConfig.setQQZone(qq[0], qq[1]);
		UMConfigure.setEncryptEnabled(true);
	}

	public LoginUtils getLoginUtil(){
		if (loginUtils == null){
			loginUtils = new LoginUtils();
		}
		return loginUtils;
	}

	public ShareUtils getShareUtil(){
		if (shareUtils == null){
			shareUtils = new ShareUtils();
		}
		return shareUtils;
	}

	public  void init(Application application){
		UMConfigure.init(application, "5af1472ba40fa315790000a1", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, null);

		PlatformConfig.setWeixin("wxb87578a66e09f3f7", "3661890958ea16991c8bcef780e58f70");
		UMShareAPI.init(application, " 5af1472ba40fa315790000a1");
		UMShareConfig config = new UMShareConfig();
		config.isNeedAuthOnGetUserInfo(true);
		UMShareAPI.get(application).setShareConfig(config);
	}

}
