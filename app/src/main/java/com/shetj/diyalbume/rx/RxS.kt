package com.shetj.diyalbume.rx


import io.reactivex.Observable

/**
 * **@packageName：** com.shetj.diyalbume.rx<br></br>
 * **@author：** shetj<br></br>
 * **@createTime：** 2019/3/15 0015<br></br>
 * **@company：**<br></br>
 * **@email：** 375105540@qq.com<br></br>
 * **@describe**<br></br>
 */
object RxS {
    fun Login(name: String, password: String)  {
          LoginObservable(name, password)
    }

    fun  checkMsg(msg: String): CheckInfoObservable {
        return CheckInfoObservable(msg)
    }
}
