package com.shetj.diyalbume.rx;




import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

/**
 * <b>@packageName：</b> com.shetj.diyalbume.rx<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2019/3/15 0015<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */
class LoginObservable extends Observable<Object> {
	private String name;
	private String password;
	public LoginObservable(String name, String passWord) {
		this.name = name;
		this.password = passWord;
	}

	@Override
	protected void subscribeActual(Observer<? super Object> observer) {
		observer.onSubscribe(new Listener(observer));
	}

	static final class Listener extends MainThreadDisposable   {
		private final Observer<? super Object> observer;

		Listener( Observer<? super Object> observer) {
			this.observer = observer;
		}


		@Override
		protected void onDispose() {
			observer.onComplete();
		}
	}
}
