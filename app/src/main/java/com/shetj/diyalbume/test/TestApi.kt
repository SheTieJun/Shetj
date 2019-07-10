package com.shetj.diyalbume.test

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * **@packageName：** com.shetj.diyalbume.test<br></br>
 * **@author：** shetj<br></br>
 * **@createTime：** 2017/12/20<br></br>
 * **@company：**<br></br>
 * **@email：** 375105540@qq.com<br></br>
 * **@describe**<br></br>
 */

interface TestApi {

    @GET("api/data/{category}/{count}/{page}")
    fun getInfo(@Path("category") category: String): Observable<String>
}
