package me.shetj.record.activity;

import android.support.transition.ChangeBounds;
import android.support.transition.Scene;
import android.support.transition.TransitionManager;
import android.widget.FrameLayout;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import me.shetj.record.bean.Record;

/**
 * 录音界面切换
 */
public class RecordAction {

	private FrameLayout mRoot;
	private Record record;
	private Scene scene1;	//标准界面
	private Scene  scene2;	//带播放界面
	private CompositeDisposable mCompositeDisposable;
	public RecordAction(FrameLayout mRoot, Record record) {
		this.mRoot = mRoot;
		this.record = record;
	}

	public void playMusic(String url ) {
	}



	private void initData(Record question) {
			scene1 =  getScene1(question,mRoot);
			scene2 = getScene2(question,mRoot);

		TransitionManager.go(scene1,new ChangeBounds());
	}

	private Scene getScene2(Record question, FrameLayout mRoot) {
		return new Scene(mRoot);
	}

	private Scene getScene1(Record question, FrameLayout mRoot) {
		return new Scene(mRoot);
	}

	public void startShow() {
		addDispose(Flowable.timer(100, TimeUnit.MILLISECONDS)
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(aLong -> {
							ChangeBounds changeBounds = new ChangeBounds();
							changeBounds.setDuration(1000);
							TransitionManager.go(scene2, changeBounds);
						} ));
	}



	public void stopAction(){
		unDispose();
	}

	/**
	 * 将 {@link Disposable} 添加到 {@link CompositeDisposable} 中统一管理
	 * 可在 {onDestroy() 中使用 {@link #unDispose()} 停止正在执行的 RxJava 任务,避免内存泄漏
	 *
	 * @param disposable
	 */
	public void addDispose(Disposable disposable) {
		if (mCompositeDisposable == null) {
			mCompositeDisposable = new CompositeDisposable();
		}
		mCompositeDisposable.add(disposable);
	}

	/**
	 * 停止集合中正在执行的 RxJava 任务
	 */
	public void unDispose() {
		if (mCompositeDisposable != null) {
			mCompositeDisposable.clear();
		}
	}


}