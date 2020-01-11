package com.shetj.diyalbume.manager

import android.content.Context
import com.shetj.diyalbume.api.ShetjApi
import com.shetj.diyalbume.utils.TimeUtil
import com.zhouyou.http.EasyHttp
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import me.shetj.base.s
import me.shetj.base.tools.file.SPUtils
import me.shetj.base.tools.json.EmptyUtils
import me.shetj.base.tools.json.GsonKit
import timber.log.Timber
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean

class TokenLoader private constructor() {

    private val mRefreshing = AtomicBoolean(false)
    private var mPublishSubject: PublishSubject<String> ?=null
    private val mTokenObservable: Observable<String>

    val cacheToken: String
        get() {
            var token: String = (SPUtils.get(s.app.applicationContext, "PRE_CUSTOM_TOKEN", "") as String?).toString()
            if (EmptyUtils.isNotEmpty(token)) {
                val timeDiff = TimeUtil.getTimeDiff(getExpire(s.app.applicationContext))
                if (timeDiff > 50000) {
                    return token
                } else {
                    token = "token_fail"
                }
                return token
            }
            return "token_fail"
        }

    val netTokenLocked: Observable<String>?
        get() {
            if (mRefreshing.compareAndSet(false, true)) {
                Timber.i("没有请求，发起一次新的Token请求")
                startTokenRequest()
            } else {
                Timber.i("已经有请求，直接返回等待")
            }
            return mPublishSubject
        }

    init {
        val map = HashMap<String, String>()
        val expire = System.currentTimeMillis().toString() + ""
        map["expire"] = expire
        mTokenObservable = EasyHttp.post(ShetjApi.Token.URL_REFRESH_TOKEN)
                .upJson(GsonKit.objectToJson(map))
                //                .syncRequest(true)//设置同步请求
                .execute(String::class.java)
                .doOnNext { token ->
                    Timber.i("存储Token=$token")
                    TokenManager.getInstance().setToken(token)
                    mRefreshing.set(false)
                }.doOnError { throwable -> mRefreshing.set(false) }.subscribeOn(Schedulers.io())
    }

    private object Holder {
        val INSTANCE = TokenLoader()
    }

    private fun startTokenRequest() {
        mPublishSubject = PublishSubject.create()
        mTokenObservable.subscribe(mPublishSubject!!)
    }

    companion object {

        private val TAG = TokenLoader::class.java.simpleName

        val instance: TokenLoader
            get() = Holder.INSTANCE


        /**
         * Get expire string.
         *
         * @param c the c
         * @return the string
         */
        private fun getExpire(c: Context): String {
            return SPUtils.get(c, "PRE_CUSTOM_TOKEN_FAILURE_TIME", System.currentTimeMillis()) as String
        }
    }

}
