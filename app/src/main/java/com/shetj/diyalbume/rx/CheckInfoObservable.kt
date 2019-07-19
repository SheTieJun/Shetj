package com.shetj.diyalbume.rx


import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable
import io.reactivex.disposables.Disposable
import me.shetj.base.tools.json.GsonKit

/**
 * **@author：** shetj<br></br>
 * **@createTime：** 2019/3/22<br></br>
 * **@email：** 375105540@qq.com<br></br>
 * **@describe**  <br></br>
 */
class CheckInfoObservable(private val msg: String) : Observable<ReturnMsg<*>>() {
    override fun subscribeActual(observer: Observer<in ReturnMsg<*>>?) {
        observer?.onSubscribe(CheckListener(GsonKit.jsonToBean(msg, ReturnMsg::class.java), observer))
    }

    internal class CheckListener(returnMsg: ReturnMsg<*>?, observer: Observer<in ReturnMsg<*>>) : Disposable {
        override fun isDisposed(): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun dispose() {

        }

        init {
            if (null == returnMsg) {
                observer.onError(Throwable("msg is null"))
            }else {
                when {
                    returnMsg.code == 1 -> observer.onNext(returnMsg)
                    returnMsg.code == 422 -> observer.onError(Throwable("登录失效"))
                    else -> observer.onError(Throwable(returnMsg.message))
                }
            }
            observer.onComplete()
        }

    }
}
