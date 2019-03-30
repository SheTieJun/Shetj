package com.shetj.diyalbume.rx;


import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;
import me.shetj.base.tools.json.GsonKit;

/**
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2019/3/22<br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b>  <br>
 */
class CheckInfoObservable extends Observable<Object> {

	private String msg;


	public CheckInfoObservable(String msg) {
		this.msg = msg;
	}

	@Override
	protected void subscribeActual(Observer<? super Object> observer) {
		observer.onSubscribe(new CheckListener(GsonKit.jsonToBean(msg,ReturnMsg.class),observer));
	}
	static final class CheckListener extends MainThreadDisposable{

		CheckListener(ReturnMsg returnMsg,Observer<? super Object> observer ){
			if (null == returnMsg){
				observer.onError(new Throwable("msg is null"));
			}
			if (returnMsg.getCode() == 1){
				observer.onNext(returnMsg);
			}else if (returnMsg.getCode() == 422) {
				observer.onError(new Throwable("登录失效"));
			}else{
				observer.onError(new Throwable(returnMsg.getMessage()));
			}
			observer.onComplete();
		}
		@Override
		protected void onDispose() {

		}
	}
}
