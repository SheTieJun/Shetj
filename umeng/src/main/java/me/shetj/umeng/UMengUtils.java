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

	public static void init(Application application){
		UMConfigure.init(application, "5af1472ba40fa315790000a1", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, null);

		PlatformConfig.setWeixin("wxb87578a66e09f3f7", "3661890958ea16991c8bcef780e58f70");
//		PlatformConfig.setQQZone("1106914964", "3mFw8xDkQFkjURJQ");
		UMShareAPI.init(application, " 5af1472ba40fa315790000a1");
		UMShareConfig config = new UMShareConfig();
		config.isNeedAuthOnGetUserInfo(true);
		UMShareAPI.get(application).setShareConfig(config);
	}
}
