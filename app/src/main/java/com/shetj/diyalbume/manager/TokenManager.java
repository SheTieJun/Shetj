package com.shetj.diyalbume.manager;

import io.reactivex.Observable;
import me.shetj.base.s;
import me.shetj.base.tools.file.SPUtils;
import me.shetj.base.tools.json.EmptyUtils;

/**
 * Created by admin on 2017/10/16.
 */

public class TokenManager {

	private static TokenManager instance = null;
	private TokenManager() {
	}

	public static TokenManager getInstance() {
		if (instance == null) {
			synchronized (TokenManager.class) {
				if (instance == null) {
					instance = new TokenManager();
				}
			}
		}
		return instance;
	}


	public Observable<String> getToken() {
		String cacheToken = TokenLoader.getInstance().getCacheToken();
		if (cacheToken.contains("token_fail")){
			return TokenLoader.getInstance().getNetTokenLocked();
		}else {
			return Observable.just(cacheToken);
		}
	}

	
	/**
	 * Is login boolean.
	 * @return the boolean
	 */
	public  boolean isLogin(){
		String token= (String) SPUtils.get(s.getApp().getApplicationContext(), "PRE_CUSTOM_TOKEN","");
		return EmptyUtils.isNotEmpty(token);
	}


	public void setToken(String token) {
		 SPUtils.put(s.getApp().getApplicationContext(), "PRE_CUSTOM_TOKEN",token);
	}
}
