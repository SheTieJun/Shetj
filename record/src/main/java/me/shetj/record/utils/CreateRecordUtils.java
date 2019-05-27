package me.shetj.record.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.widget.ImageView;


import org.simple.eventbus.EventBus;

import java.io.File;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.shetj.base.tools.app.ArmsUtils;
import me.shetj.base.tools.file.FileUtils;
import me.shetj.base.tools.file.SDCardUtils;
import me.shetj.base.tools.time.TimeUtil;

/**
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2017/12/19<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b>创建录音工具<br>
 */
public class CreateRecordUtils {

	private RecordUtils recordUtils;//录音工具
	private String mRecordFile = "";//录音文件
	private RecordCallBack callBack;//回调
	private CompositeDisposable mCompositeDisposable;
	private Disposable timeDisposable;//即时
	private Disposable volumeDisposable;//即时
	private int startTime = 0;
	//时间记录
	private int time = 0;
	//最大时间
	private int maxTime = 3600;
	private int remindTime = 3480;



	public CreateRecordUtils(RecordCallBack recordCallBack) {
		this.callBack = recordCallBack;
		recordUtils = new RecordUtils(callBack);//录音工具
	}


	/**
	 * 设置开始录制时间
	 * @param startTime 已经录制的时间
	 */
	public void setTime(int startTime) {
		this.startTime = startTime;
		this.time = startTime;
		if (callBack !=null){
			callBack.onProgress(startTime);
		}
	}

	/**
	 * 设置最大录制时间
	 */
	public void setMaxTime(int maxTime) {
		this.maxTime = maxTime;
		this.remindTime = maxTime - 120;
		if (callBack !=null){
			callBack.onMaxProgress(maxTime);
		}
	}

	/**
	 * 开始或者暂停
	 */
	public void statOrPause() {
		if (recordUtils.getState() == RecordUtils.NORMAL) {
			mRecordFile = SDCardUtils.getPath("record")+"/"+ TimeUtil.getTime()+".mp3";
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

	public boolean isRecording(){
		return recordUtils.getState() == RecordUtils.RECORD_ING;
	}

	/**
	 * 暂停
	 */
	public void pause(){
		if (recordUtils.getState() == RecordUtils.RECORD_ING) {
			callBack.pause();
			recordUtils.pauseFullRecord();
			stopProgress();
		}
	}


	/**
	 * 重新录制
	 */
	public void reRecord() {
		stopProgress();
		recordUtils.stopFullRecord();
		if (mRecordFile != null){
			FileUtils.deleteFile(new File(mRecordFile));
		}
	}

	/**
	 * 完成录制
	 */
	public void recordComplete() {
		stopProgress();
		recordUtils.stopFullRecord();
		callBack.onSuccess(mRecordFile, time);
	}

	/**
	 * 清理
	 */
	public void clear(){
		recordUtils.stopFullRecord();
		callBack = null;
		unDispose();
	}

	public void stop() {
		recordUtils.stopFullRecord();
		if (mRecordFile != null){
			FileUtils.deleteFile(new File(mRecordFile));
		}
		stopProgress();
	}
	/*                **计时相关**                      */

	/**
	 * 是否产生了录音
	 * @return
	 */
	public boolean hasRecord(){
		return time-startTime >0;
	}

	/**
	 * 开始计时
	 */
	private void startProgress() {
		if (recordUtils != null && recordUtils.getState() == RecordUtils.RECORD_ING && (timeDisposable == null ||timeDisposable.isDisposed())) {
			timeDisposable = Flowable.interval(500, 1000, TimeUnit.MILLISECONDS)
							.take(maxTime-time+1)
							.observeOn(AndroidSchedulers.mainThread())
							.subscribeOn(Schedulers.io())
							.subscribe(aLong -> {
								//如果是录制中，每一秒+1
								if (recordUtils != null && recordUtils.getState() == RecordUtils.RECORD_ING) {
									callBack.onProgress(time++);
								}
								//如果到最大录制时间，进入自动结束回调
								if (time >= maxTime+1){
									recordUtils.stopFullRecord();
									stopProgress();
									callBack.autoComplete(mRecordFile,time);
								}

								if (time == remindTime){
									//Tip 告诉用户已经录制58分钟
									ArmsUtils.makeText("已录制"+(time/60)+"分钟，本条录音还可以继续录制2分钟");
								}

							}, throwable -> {
								//出现异常就停止
								stopProgress();
								callBack.onError(new Exception("计算时间错误~"));
							});
			volumeDisposable = Flowable.interval(0, 500, TimeUnit.MILLISECONDS)
							.take(maxTime-time)
							.observeOn(AndroidSchedulers.mainThread())
							.subscribeOn(Schedulers.io())
							.subscribe(aLong -> {
								//如果是录制中，每一秒+1
								if (recordUtils != null && recordUtils.getState() == RecordUtils.RECORD_ING) {
									callBack.onRecording(time,recordUtils.getRealVolume());
								}
							}, throwable -> {
								//出现异常就停止
								stopProgress();
								callBack.onError(new Exception("计算时间错误~"));
							});
			addDispose(timeDisposable);
			addDispose(volumeDisposable);
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
		if (volumeDisposable !=null && !volumeDisposable.isDisposed()){
			volumeDisposable.dispose();
			volumeDisposable = null;
		}
	}

	/* ******           计算时间 结束              ****    */

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
