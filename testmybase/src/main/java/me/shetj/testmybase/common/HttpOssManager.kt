package shetj.me.base.common

import com.zhouyou.http.EasyHttp
import com.zhouyou.http.cache.model.CacheMode
import me.shetj.base.http.callback.EasyCallBack
import shetj.me.base.api.API

/**
 * Created by shetj
 * on 2017/9/28.
 * @author shetj
 */

class HttpOssManager private constructor() {

    fun getOSSFromSever(callBack: EasyCallBack<String>) {
        EasyHttp.get(API.QINIU_GET_TOKEN)
                .cacheKey(MD5.md5(API.QINIU_GET_TOKEN))
                .cacheMode(CacheMode.FIRSTCACHE)
                .cacheTime((1000 * 60).toLong())
                .execute(callBack)
    }

    companion object {

        private var instance: HttpOssManager? = null

        fun getHttpOssManager(): HttpOssManager? {
            if (instance == null) {
                synchronized(HttpOssManager::class.java) {
                    if (instance == null) {
                        instance = HttpOssManager()
                    }
                }
            }
            return instance
        }


    }

}
