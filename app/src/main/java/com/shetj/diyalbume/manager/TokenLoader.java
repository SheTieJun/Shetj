package com.shetj.diyalbume.manager;

import android.content.Context;

import com.zhouyou.http.EasyHttp;

import org.xutils.common.util.LogUtil;
import org.xutils.x;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import com.shetj.diyalbume.api.ShetjApi;
import me.shetj.base.tools.app.DataConfig;
import me.shetj.base.tools.app.TimeUtil;
import me.shetj.base.tools.file.SPUtils;
import me.shetj.base.tools.json.EmptyUtils;
import me.shetj.base.tools.json.GsonKit;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

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
                        LogUtil.i( "存储Token=" + token);
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
        String token = (String) SPUtils.get(x.app().getApplicationContext(), DataConfig.PRE_CUSTOM_TOKEN, "");
        if (EmptyUtils.isNotEmpty(token)) {
            long timeDiff = TimeUtil.getTimeDiff(getExpire(x.app().getApplicationContext()));
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
        return (String) SPUtils.get(c, DataConfig.PRE_CUSTOM_TOKEN_FAILURE_TIME, TimeUtil.getYMDHMSTime());
    }

    public Observable<String> getNetTokenLocked() {
        if (mRefreshing.compareAndSet(false, true)) {
            LogUtil.i("没有请求，发起一次新的Token请求");
            startTokenRequest();
        } else {
            LogUtil.i( "已经有请求，直接返回等待");
        }
        return mPublishSubject;
    }

    private void startTokenRequest() {
        mTokenObservable.subscribe(mPublishSubject);
    }

}
