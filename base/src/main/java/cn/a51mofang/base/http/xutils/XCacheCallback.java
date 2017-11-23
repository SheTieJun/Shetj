package cn.a51mofang.base.http.xutils;

import org.xutils.common.Callback;

/**
 * Created by shetj on 2017/6/20.
 * 抽象方法
 * 公共方法
 * 私有方法
 */


public class XCacheCallback<ResultType>  implements  Callback.CacheCallback<ResultType> {
	@Override
	public boolean onCache(ResultType result) {
		return false;
	}

	@Override
	public void onSuccess(ResultType result) {

	}

	@Override
	public void onError(Throwable ex, boolean isOnCallback) {

	}

	@Override
	public void onCancelled(CancelledException cex) {

	}

	@Override
	public void onFinished() {

	}
}
