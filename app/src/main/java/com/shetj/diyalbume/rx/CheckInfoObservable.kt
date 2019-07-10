package com.shetj.diyalbume.rx


import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable
import me.shetj.base.tools.json.GsonKit

/**
 * **@author：** shetj<br></br>
 * **@createTime：** 2019/3/22<br></br>
 * **@email：** 375105540@qq.com<br></br>
 * **@describe**  <br></br>
 */
internal class CheckInfoObservable(private val msg: String) : Observable<Any>() {

    override fun subscribeActual(observer: Observer<in Any>) {
        observer.onSubscribe(CheckListener(GsonKit.jsonToBean(msg, ReturnMsg::class.java), observer))
    }

    internal class CheckListener(returnMsg: ReturnMsg<*>?, observer: Observer<in Any>) : MainThreadDisposable() {

        init {
            if (null == returnMsg) {
                observer.onError(Throwable("msg is null"))
            }
            if (returnMsg!!.code == 1) {
                observer.onNext(returnMsg)
            } else if (returnMsg.code == 422) {
                observer.onError(Throwable("登录失效"))
            } else {
                observer.onError(Throwable(returnMsg.message))
            }
            observer.onComplete()
        }

        override fun onDispose() {

        }
    }
}
