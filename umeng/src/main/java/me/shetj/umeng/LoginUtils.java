package me.shetj.umeng;

import android.app.Activity;

import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;


/**
 * <b>@packageName：</b> me.shetj.umeng<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2018/4/9 0009<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */
public class LoginUtils {

	public  void loginByWx(Activity activity,UMAuthListener listener) {
		UMShareAPI umShareAPI = UMShareAPI.get(activity);
		umShareAPI.getPlatformInfo(activity, SHARE_MEDIA.WEIXIN, listener);
	}

	public  void loginByQQ(Activity activity,UMAuthListener listener) {
		UMShareAPI umShareAPI = UMShareAPI.get(activity);
		umShareAPI.getPlatformInfo(activity, SHARE_MEDIA.QQ, listener);
	}

	/**
	 * 把用户注册
	 * @param userId
	 */
	public  void loginIn(String userId){
		MobclickAgent.onProfileSignIn(userId);
	}
}
