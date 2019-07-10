package com.shetj.diyalbume.manager

import com.shetj.diyalbume.api.ShetjApi
import com.zhouyou.http.EasyHttp
import com.zhouyou.http.cache.model.CacheMode
import com.zhouyou.http.callback.SimpleCallBack
import com.zhouyou.http.exception.ApiException
import io.reactivex.disposables.Disposable

import me.shetj.base.tools.app.ArmsUtils

/**
 * Created by shetj
 * on 2017/9/28.
 */

class HttpOssManager private constructor() {

    fun getOSSFromSever(callBack: SimpleCallBack<String>) {
        if (TokenManager.getInstance().isLogin) {
            TokenManager.getInstance().token
                    .map<Disposable> { token ->
                        EasyHttp.get(ShetjApi.User.URL_GET_OSS_STS)
                                .baseUrl(ShetjApi.HTTP_USER)
                                .headers("Authorization", "Bearer $token")
                                .cacheKey(ArmsUtils.encodeToMD5(ShetjApi.User.URL_GET_OSS_STS))
                                .cacheMode(CacheMode.CACHEANDREMOTEDISTINCT)
                                .cacheTime((24 * 60 * 60 * 1000 - 60 * 60 * 1000).toLong())
                                .execute(callBack)
                    }
        } else {
            callBack.onError(ApiException(Throwable("您还没有登录！"), 402))
        }
    }

    companion object {

        private var instance: HttpOssManager? = null

        fun getInstance(): HttpOssManager {
            if (instance == null) {
                synchronized(HttpOssManager::class.java) {
                    if (instance == null) {
                        instance = HttpOssManager()
                    }
                }
            }
            return instance!!
        }
    }

}
