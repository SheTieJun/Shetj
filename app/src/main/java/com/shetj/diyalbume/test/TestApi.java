package com.shetj.diyalbume.test;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * <b>@packageName：</b> com.shetj.diyalbume.test<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2017/12/20<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */

public interface TestApi {

	@GET("api/data/{category}/{count}/{page}")
	Observable<String > getInfo(@Path("category") String category);
 }
