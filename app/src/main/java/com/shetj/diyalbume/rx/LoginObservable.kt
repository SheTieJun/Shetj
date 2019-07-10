package com.shetj.diyalbume.rx


import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable

/**
 * **@packageName：** com.shetj.diyalbume.rx<br></br>
 * **@author：** shetj<br></br>
 * **@createTime：** 2019/3/15 0015<br></br>
 * **@company：**<br></br>
 * **@email：** 375105540@qq.com<br></br>
 * **@describe**<br></br>
 */
internal class LoginObservable(private val name: String, private val password: String) : Observable<Any>() {

    override fun subscribeActual(observer: Observer<in Any>) {
        observer.onSubscribe(Listener(observer))
    }

    internal class Listener(private val observer: Observer<in Any>) : MainThreadDisposable() {


        override fun onDispose() {
            observer.onComplete()
        }
    }
}
