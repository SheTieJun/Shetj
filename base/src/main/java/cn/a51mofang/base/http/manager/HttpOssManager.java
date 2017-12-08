package cn.a51mofang.base.http.manager;

import android.content.Context;

import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.cache.model.CacheMode;
import com.zhouyou.http.callback.CallBack;
import com.zhouyou.http.exception.ApiException;

import org.xutils.common.util.MD5;

import cn.a51mofang.base.http.api.ShetjApi;
import cn.a51mofang.base.http.callback.MCallBack;

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


    public  void getOSS_STS(final Context context, final MCallBack<String> callBack){



      if (TokenManager.getInstance().isLogin()) {
        EasyHttp.get(ShetjApi.User.URL_GET_OSS_STS)
                .baseUrl(ShetjApi.HTTP_USER)
                .headers("Authorization", "Bearer " + TokenManager.getInstance().getToken())
                .cacheKey(MD5.md5(ShetjApi.User.URL_GET_OSS_STS))
                .cacheMode(CacheMode.CACHEANDREMOTEDISTINCT)
                .cacheTime(24*60*60*1000)
                .execute(new CallBack<String>() {
                  @Override
                  public void onStart() {

                  }

                  @Override
                  public void onCompleted() {
                    if (callBack != null) {
                      callBack.onFinished();
                    }
                  }

                  @Override
                  public void onError(ApiException e) {

                  }

                  @Override
                  public void onSuccess(String content) {
                  }
                });
      }
    }

    public void  saveOSSSTS(){

    }


}
