package com.shetj.diyalbume.rx

import com.shetj.diyalbume.test.Test

import io.reactivex.Observable
import me.shetj.base.tools.json.GsonKit

/**
 * **@packageName：** com.shetj.diyalbume.rx<br></br>
 * **@author：** shetj<br></br>
 * **@createTime：** 2019/3/15 0015<br></br>
 * **@company：**<br></br>
 * **@email：** 375105540@qq.com<br></br>
 * **@describe**<br></br>
 */
object RxS {
    fun Login(name: String, password: String): Observable<*> {
        return LoginObservable(name, password)
    }

    fun checkMsg(msg: String): Observable<*> {
        return CheckInfoObservable(msg)
    }
}
