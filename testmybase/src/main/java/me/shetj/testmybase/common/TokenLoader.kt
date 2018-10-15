package shetj.me.base.common

import com.zhouyou.http.EasyHttp
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import me.shetj.base.tools.file.SPUtils
import me.shetj.base.tools.json.EmptyUtils
import me.shetj.base.tools.json.GsonKit
import shetj.me.base.api.API
import shetj.me.base.configs.tag.SPKey.Companion.SAVE_TOKEN
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean


/**
 * @author shetj
 */
class TokenLoader private constructor() {


    private val mRefreshing = AtomicBoolean(false)
    private val mPublishSubject: PublishSubject<String> = PublishSubject.create()
    private val mTokenObservable: Observable<String>

    val cacheToken: String
        get() {
            val token = SPUtils[x.app().applicationContext, SAVE_TOKEN, ""] as String
            return if (EmptyUtils.isEmpty(token)) {
                "token_fail"
            } else token
        }

    val netTokenLocked: Observable<String>
        @Deprecated("")
        get() {
            if (mRefreshing.compareAndSet(false, true)) {
                LogUtil.i("没有请求，发起一次新的Token请求")
                startTokenRequest()
            } else {
                LogUtil.i("已经有请求，直接返回等待")
            }
            return mPublishSubject
        }

    init {
        val map = HashMap<String, String>()
        mTokenObservable = EasyHttp.post(API.QINIU_GET_TOKEN)
                .upJson(GsonKit.objectToJson(map))
                .execute<String>(String::class.java)
                .doOnNext({ token ->
                    LogUtil.i("存储Token=$token")
                    TokenManager.getTokenManager()?.token = token
                    mRefreshing.set(false)
                }).doOnError({ mRefreshing.set(false) })
                .subscribeOn(Schedulers.io())
    }

    private object Holder {
        val INSTANCE = TokenLoader()
    }

    private fun startTokenRequest() {
        mTokenObservable.subscribe(mPublishSubject)
    }

    companion object {

        val instance: TokenLoader
            get() = Holder.INSTANCE
    }

}
