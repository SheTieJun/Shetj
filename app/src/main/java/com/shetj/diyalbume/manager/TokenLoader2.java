package com.shetj.diyalbume.manager;

import android.content.Context;
import android.text.TextUtils;

import com.shetj.diyalbume.api.ShetjApi;
import com.zhouyou.http.EasyHttp;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import me.shetj.base.s;
import me.shetj.base.tools.file.SPUtils;
import me.shetj.base.tools.json.EmptyUtils;
import me.shetj.base.tools.json.GsonKit;
import me.shetj.base.tools.time.TimeUtil;
import timber.log.Timber;


/**
 * token管理类
 */
public class TokenLoader2 {
	private final Observable<String> mTokenObservable;
	private static class Holder {
		private static final TokenLoader2 INSTANCE = new TokenLoader2();
	}
	private AtomicBoolean mRefreshing = new AtomicBoolean(false);
	private PublishSubject<String> mPublishSubject;

	private TokenLoader2() {
		mTokenObservable = buildRequester();
	}

	public static TokenLoader2 getInstance() {
		return Holder.INSTANCE;
	}

	/**
	 * 获取通过
	 * 如果过期 或者token 为空就重新去获取
	 */
	public Observable<String> getToken() {
		String cacheToken = getCacheToken();
		if (TextUtils.isEmpty(cacheToken)) {
			Timber.i("token过期了");
			return getNetTokenLocked();
		} else {
			Timber.i("使用本地缓存token = " + cacheToken);
			return Observable.just(cacheToken);
		}
	}


	private Observable<String> buildRequester(){
		HashMap<String,String> map=new HashMap<>();
		String expire = TimeUtil.getTime()+"";
		map.put("expire",expire);
		return   EasyHttp.post(ShetjApi.Token.URL_REFRESH_TOKEN)
				.upJson(GsonKit.objectToJson(map))
				.execute(String.class)
				.doOnNext(token -> {
					Timber.i( "存储Token=" + token);
					TokenManager.getInstance().setToken(token);
					mRefreshing.set(false);
				}).doOnError(throwable -> mRefreshing.set(false)).subscribeOn(Schedulers.io());
	}
	private String getCacheToken() {
		String token = (String) SPUtils.get(s.getApp().getApplicationContext(), "PRE_CUSTOM_TOKEN", "");
		if (EmptyUtils.isNotEmpty(token)) {
			long timeDiff = TimeUtil.getTimeDiff(getExpire(s.getApp().getApplicationContext()));
			if (timeDiff > 50000) {
				return token;
			}else {
				token = "";
			}
			return token;
		}
		return "";
	}
	private static String getExpire(Context c){
		return (String) SPUtils.get(c, "PRE_CUSTOM_TOKEN_FAILURE_TIME",  TimeUtil.getYMDHMSTime());
	}

	private Observable<String> getNetTokenLocked() {
		if (mRefreshing.compareAndSet(false, true)) {
			startTokenRequest();
		} else {
			Timber.i("已经去请求token了获取token");
		}
		return mPublishSubject;
	}

	private void startTokenRequest() {
		mPublishSubject = PublishSubject.create();
		mTokenObservable.subscribe(mPublishSubject);
	}

}
