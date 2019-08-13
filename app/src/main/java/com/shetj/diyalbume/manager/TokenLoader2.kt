package com.shetj.diyalbume.manager

import android.content.Context
import android.text.TextUtils

import com.shetj.diyalbume.api.ShetjApi
import com.shetj.diyalbume.utils.TimeUtil
import com.zhouyou.http.EasyHttp

import java.util.HashMap
import java.util.concurrent.atomic.AtomicBoolean

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import me.shetj.base.s
import me.shetj.base.tools.file.SPUtils
import me.shetj.base.tools.json.EmptyUtils
import me.shetj.base.tools.json.GsonKit
import timber.log.Timber


/**
 * token管理类
 */
class TokenLoader2 private constructor() {
    private val mTokenObservable: Observable<String>
    private val mRefreshing = AtomicBoolean(false)
    private var mPublishSubject: PublishSubject<String>? = null

    /**
     * 获取通过
     * 如果过期 或者token 为空就重新去获取
     */
    val token: Observable<String>?
        get() {
            val cacheToken = cacheToken
            if (TextUtils.isEmpty(cacheToken)) {
                Timber.i("token过期了")
                return netTokenLocked
            } else {
                Timber.i("使用本地缓存token = %s", cacheToken)
                return Observable.just(cacheToken)
            }
        }
    private val cacheToken: String
        get() {
            var token: String = SPUtils.get(s.app.applicationContext, "PRE_CUSTOM_TOKEN", "") as String
            if (EmptyUtils.isNotEmpty(token)) {
                val timeDiff = TimeUtil.getTimeDiff(getExpire(s.app.applicationContext))
                if (timeDiff > 50000) {
                    return token
                } else {
                    token = ""
                }
                return token
            }
            return ""
        }

    private val netTokenLocked: Observable<String>?
        get() {
            if (mRefreshing.compareAndSet(false, true)) {
                startTokenRequest()
            } else {
                Timber.i("已经去请求token了获取token")
            }
            return mPublishSubject
        }

    private object Holder {
        val INSTANCE = TokenLoader2()
    }

    init {
        mTokenObservable = buildRequester()
    }


    private fun buildRequester(): Observable<String> {
        val map = HashMap<String, String>()
        val expire = System.currentTimeMillis().toString()
        map["expire"] = expire
        return EasyHttp.post(ShetjApi.Token.URL_REFRESH_TOKEN)
                .upJson(GsonKit.objectToJson(map))
                .execute(String::class.java)
                .doOnNext { token ->
                    Timber.i("存储Token=%s", token)
                    TokenManager.getInstance().setToken(token)
                    mRefreshing.set(false)
                }.doOnError { throwable -> mRefreshing.set(false) }.subscribeOn(Schedulers.io())
    }

    private fun startTokenRequest() {
        mPublishSubject = PublishSubject.create()
        mTokenObservable.subscribe(mPublishSubject!!)
    }

    companion object {

        val instance: TokenLoader2
            get() = Holder.INSTANCE

        private fun getExpire(c: Context): String {
            return SPUtils.get(c, "PRE_CUSTOM_TOKEN_FAILURE_TIME", System.currentTimeMillis().toString()) as String
        }
    }

}
