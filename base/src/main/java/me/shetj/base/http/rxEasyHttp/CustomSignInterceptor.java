package me.shetj.base.http.rxEasyHttp;

import com.zhouyou.http.interceptor.BaseDynamicInterceptor;

import java.util.TreeMap;

import me.shetj.base.http.manager.TokenManager;

/**
 */
public class CustomSignInterceptor extends BaseDynamicInterceptor<CustomSignInterceptor> {

	@Override
	public boolean isAccessToken() {
		return true;
	}

	@Override
	public boolean isTimeStamp() {
		return true;
	}

	@Override
	public TreeMap<String, String> dynamic(TreeMap<String, String> dynamicMap) {
		//dynamicMap:是原有的全局参数+局部参数
		if (isTimeStamp()) {//是否添加时间戳，因为你的字段key可能不是timestamp,这种动态的自己处理
			dynamicMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
		}
		if (isAccessToken() && TokenManager.getInstance().isLogin()) {//是否添加token
//			dynamicMap.put("Authorization", "Bearer " + MFangCommonUtil.getToken());
		}
		if (isSign()) {//是否签名,因为你的字段key可能不是sign，这种动态的自己处理

		}
		//HttpLog.i("dynamicMap:" + dynamicMap.toString());
		return dynamicMap;//dynamicMap:是原有的全局参数+局部参数+新增的动态参数
	}

}
