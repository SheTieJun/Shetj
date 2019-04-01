package me.shetj.record.utils;

import android.widget.ImageView;


import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import me.shetj.base.tools.file.SDCardUtils;
import me.shetj.base.tools.time.TimeUtil;

/**
 * <b>@packageName：</b> com.mobile.pipiti.ugc.previewUGC<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2017/12/19<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */

public class CreateQuesUtils {

	private RecordUtils recordUtils = new RecordUtils();
	private String mRecordFile = "";
	private RecordCallBack callBack;
	private CompositeDisposable mCompositeDisposable;
	private PublishSubject<Integer> progressSubject;
	private Disposable timeDisposable;
	//时间记录
	private int time = 0;
	//最大时间
	private int maxTime = 3599;


	public CreateQuesUtils(ImageView imageView, RecordCallBack callBack){
		this.callBack = callBack;
		imageView.setOnClickListener(v -> {
			statOrPause();
		});
		progressSubject = PublishSubject.create();
		//设置进度控制
		Disposable disposable = progressSubject
						.observeOn(AndroidSchedulers.mainThread())
						.throttleFirst(1,TimeUnit.MILLISECONDS)
						.subscribe(new Consumer<Integer>() {
							@Override
							public void accept(Integer integer) throws Exception {

							}
						});
		addDispose(disposable);

	}


	public void statOrPause() {
		if (recordUtils.getState() == RecordUtils.NORMAL) {
			mRecordFile = SDCardUtils.getPath("testRecord")+"/"+ TimeUtil.getTime()+".mp3";
			recordUtils.startFullRecord(mRecordFile);
			if (recordUtils.getState() == RecordUtils.RECORD_ING) {
				time = 0;
				callBack.start();
				startProgress();
			}
		} else if (recordUtils.getState() == RecordUtils.RECORD_ING) {
			callBack.pause();
			recordUtils.pauseFullRecord();
			stopProgress();
		}
	}


	public void stopFullRecord() {
		callBack.onSuccess(mRecordFile);
		recordUtils.stopFullRecord();

	}

	public void clear(){
		recordUtils.stopFullRecord();
		callBack = null;
		unDispose();
	}


	/*                **计时相关**                      */

	private void startProgress() {
		if (timeDisposable == null && recordUtils != null && recordUtils.getState() ==RecordUtils.RECORD_ING) {
			timeDisposable = Flowable.interval(0, maxTime, TimeUnit.MILLISECONDS)
							.subscribeOn(Schedulers.newThread())
							.subscribe(aLong -> {
								if (recordUtils != null && recordUtils.getState() == RecordUtils.RECORD_ING) {
									progressSubject.onNext(time++);
								}
								if (time>= maxTime){
									progressSubject.onComplete();
								}
							}, new Consumer<Throwable>() {
								@Override
								public void accept(Throwable throwable) throws Exception {
									//出现异常就停止
									stopProgress();
								}
							}, new Action() {
								@Override
								public void run() throws Exception {
									stopFullRecord();
								}
							});
			addDispose(timeDisposable);
		}
	}

	/**
	 * 停止计时
	 */
	private void stopProgress(){
		if (timeDisposable !=null && !timeDisposable.isDisposed()){
			timeDisposable.dispose();
			timeDisposable = null;
		}
	}


	/* ******             结束              ****    */

	private void addDispose(Disposable disposable) {
		if (mCompositeDisposable == null) {
			mCompositeDisposable = new CompositeDisposable();
		}
		mCompositeDisposable.add(disposable);
	}


	private void unDispose() {
		if (mCompositeDisposable != null) {
			mCompositeDisposable.clear();
		}
	}


}
