package me.shetj.record.utils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
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

public class CreateRecordUtils {

	private RecordUtils recordUtils = new RecordUtils();
	private String mRecordFile = "";
	private RecordCallBack callBack;
	private CompositeDisposable mCompositeDisposable;
	private Disposable timeDisposable;
	private int startTime = 0;
	//时间记录
	private int time = 0;
	//最大时间
	private int maxTime = 3600;


	public CreateRecordUtils(RecordCallBack callBack){
		this.callBack = callBack;
	}

	/**
	 * 设置开始录制时间
	 * @param startTime
	 */
	public void setTime(int startTime) {
		this.startTime = startTime;
		this.time = startTime;
		if (callBack !=null){
			callBack.onProgress(startTime);
		}
	}

	public void setMaxTime(int maxTime) {
		this.maxTime = maxTime;
	}

	public void statOrPause() {
		if (recordUtils.getState() == RecordUtils.NORMAL) {
			mRecordFile = SDCardUtils.getPath("testRecord")+"/"+ TimeUtil.getTime()+".mp3";
			recordUtils.startFullRecord(mRecordFile);
			if (recordUtils.getState() == RecordUtils.RECORD_ING) {
				time = startTime;
				callBack.start();
				startProgress();
			}
		} else if (recordUtils.getState() == RecordUtils.RECORD_ING) {
			callBack.pause();
			recordUtils.pauseFullRecord();
			stopProgress();
		}else if (recordUtils.getState() == RecordUtils.RECORD_PAUSE){
			callBack.start();
			recordUtils.onResumeFullRecord();
			startProgress();
		}
	}

	public void reRecord() {
		recordUtils.stopFullRecord();
		statOrPause();
	}
	public void recordComplete() {
		stopProgress();
		callBack.onSuccess(mRecordFile,time);
		recordUtils.stopFullRecord();

	}

	public void clear(){
		recordUtils.stopFullRecord();
		callBack = null;
		unDispose();
	}

	public void pause(){
		if (recordUtils.getState() == RecordUtils.RECORD_ING) {
			callBack.pause();
			recordUtils.pauseFullRecord();
			stopProgress();
		}
	}

	/**
	 * 是否产生了录音
	 * @return
	 */
	public boolean hasRecord(){
		return time >0;
	}

	/*                **计时相关**                      */

	private void startProgress() {
		if (recordUtils != null && recordUtils.getState() == RecordUtils.RECORD_ING) {
			timeDisposable = Flowable.interval(0, 1, TimeUnit.SECONDS)
							.take(maxTime-time)
							.observeOn(AndroidSchedulers.mainThread())
							.subscribeOn(Schedulers.io())
							.subscribe(aLong -> {
								if (recordUtils != null && recordUtils.getState() == RecordUtils.RECORD_ING) {
									callBack.onProgress(time++);
								}
								if (time>= maxTime){
									recordComplete();
								}
							}, new Consumer<Throwable>() {
								@Override
								public void accept(Throwable throwable) throws Exception {
									//出现异常就停止
									stopProgress();
									callBack.onError(new Exception("计算时间错误~"));
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
