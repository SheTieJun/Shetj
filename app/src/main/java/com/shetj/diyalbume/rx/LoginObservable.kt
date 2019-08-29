package com.shetj.diyalbume.rx


import io.reactivex.observers.DefaultObserver

/**
 * **@packageName：** com.shetj.diyalbume.rx<br></br>
 * **@author：** shetj<br></br>
 * **@createTime：** 2019/3/15 0015<br></br>
 * **@company：**<br></br>
 * **@email：** 375105540@qq.com<br></br>
 * **@describe**<br></br>
 */
internal class LoginObservable(private val name: String, private val password: String) : DefaultObserver<Any>() {

    override fun onComplete() {

    }

    override fun onNext(t: Any) {

    }

    override fun onError(e: Throwable) {

    }

}
