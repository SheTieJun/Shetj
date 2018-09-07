package com.shetj.diyalbume.manager;

import android.content.Context;

import com.zhouyou.http.EasyHttp;


import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import com.shetj.diyalbume.api.ShetjApi;

import me.shetj.base.s;
import me.shetj.base.tools.file.SPUtils;
import me.shetj.base.tools.json.EmptyUtils;
import me.shetj.base.tools.json.GsonKit;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import me.shetj.base.tools.time.TimeUtil;
import timber.log.Timber;

public class TokenLoader {

    private static final String TAG = TokenLoader.class.getSimpleName();

    private AtomicBoolean mRefreshing = new AtomicBoolean(false);
    private PublishSubject<String> mPublishSubject;
    private Observable<String> mTokenObservable;

    private TokenLoader() {
        mPublishSubject = PublishSubject.create();
        HashMap<String,String> map=new HashMap<>();
        String expire = TimeUtil.getTime()+"";
        map.put("expire",expire);
        mTokenObservable = EasyHttp.post(ShetjApi.Token.URL_REFRESH_TOKEN)
                .upJson(GsonKit.objectToJson(map))
//                .syncRequest(true)//设置同步请求
                .execute(String.class)
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String token) throws Exception {
                        Timber.i( "存储Token=" + token);
                        TokenManager.getInstance().setToken(token);
                        mRefreshing.set(false);
                    }
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mRefreshing.set(false);
                    }
                }).subscribeOn(Schedulers.io());
    }

    public static TokenLoader getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final TokenLoader INSTANCE = new TokenLoader();
    }

    public String getCacheToken() {
        String token = (String) SPUtils.get(s.getApp().getApplicationContext(), "PRE_CUSTOM_TOKEN", "");
        if (EmptyUtils.isNotEmpty(token)) {
            long timeDiff = TimeUtil.getTimeDiff(getExpire(s.getApp().getApplicationContext()));
            if (timeDiff > 50000) {
                return token;
            }else {
                token = "token_fail";
            }
            return token;
        }
        return "token_fail";
    }


    /**
     * Get expire string.
     *
     * @param c the c
     * @return the string
     */
    private static String getExpire(Context c){
        return (String) SPUtils.get(c, "PRE_CUSTOM_TOKEN_FAILURE_TIME",  TimeUtil.getYMDHMSTime());
    }

    public Observable<String> getNetTokenLocked() {
        if (mRefreshing.compareAndSet(false, true)) {
            Timber.i("没有请求，发起一次新的Token请求");
            startTokenRequest();
        } else {
            Timber.i( "已经有请求，直接返回等待");
        }
        return mPublishSubject;
    }

    private void startTokenRequest() {
        mTokenObservable.subscribe(mPublishSubject);
    }

}
