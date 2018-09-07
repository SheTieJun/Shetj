package com.shetj.diyalbume.manager;

import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.cache.model.CacheMode;


import com.shetj.diyalbume.api.ShetjApi;
import me.shetj.base.http.callback.EasyCallBack;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import me.shetj.base.tools.app.ArmsUtils;

/**
 * Created by shetj
 * on 2017/9/28.
 */

public class HttpOssManager {

  private static HttpOssManager instance = null;
  private HttpOssManager() {
  }

  public static HttpOssManager getInstance() {
    if (instance == null) {
      synchronized (HttpOssManager.class) {
        if (instance == null) {
          instance = new HttpOssManager();
        }
      }
    }
    return instance;
  }

  public  void getOSSFromSever(final EasyCallBack<String> callBack){
    if (TokenManager.getInstance().isLogin()){
      TokenManager.getInstance().getToken()
              .map(new Function<String, Disposable>() {
                @Override
                public Disposable apply(String token) throws Exception {
                  return  EasyHttp.get(ShetjApi.User.URL_GET_OSS_STS)
                          .baseUrl(ShetjApi.HTTP_USER)
                          .headers("Authorization", "Bearer " + token)
                          .cacheKey(ArmsUtils.encodeToMD5(ShetjApi.User.URL_GET_OSS_STS))
                          .cacheMode(CacheMode.CACHEANDREMOTEDISTINCT)
                          .cacheTime(24 * 60 * 60 * 1000 - 60*60*1000)
                          .execute(callBack);
                }
              });
    }
  }

}
