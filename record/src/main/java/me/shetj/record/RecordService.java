package me.shetj.record;


import android.app.Notification;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.lang.ref.WeakReference;

import me.shetj.base.base.BaseService;
import me.shetj.record.bean.Record;
import me.shetj.record.utils.CreateRecordUtils;
import me.shetj.record.utils.RecordCallBack;

public class RecordService extends BaseService {


	private  RecordCallBack  callBacks = null;
	private Work work;
	private Notification notification;

	private WeakReference<RecordService> myService = new WeakReference<>(RecordService.this);
	private CreateRecordUtils createRecordUtils;
	private Work getWork() {
		if (work == null) {
			work = new Work();
		}
		return work;
	}



	@Override
	public IBinder onBind(Intent intent) {
		return getWork();
	}


	public class Work extends Binder {
		public void startWork(Record oldRecord) {
			if (oldRecord != null){
				createRecordUtils.setTime(oldRecord.getAudioLength());
			}
			createRecordUtils.statOrPause();
		}

		public void stop(){
			createRecordUtils.recordComplete();
		}


		public RecordService getMyService() {
			return myService.get();
		}

		public void reRecord(Record oldRecord) {
			if (oldRecord != null){
				createRecordUtils.setTime(oldRecord.getAudioLength());
			}
			createRecordUtils.statOrPause();
		}

		public void recordComplete() {
			createRecordUtils.recordComplete();
		}
	}



	//注册接口
	public void registerCallBack(RecordCallBack callBack) {
		if (callBack != null) {
			this.callBacks = callBack;
		}
	}

	/**
	 * 注销接口 false注销失败
	 *
	 * @param callBack
	 * @return
	 */
	public boolean unRegisterCallBack(RecordCallBack callBack) {
		callBacks =null;
		return false;
	}


	@Override
	public void init() {
		createRecordUtils = new CreateRecordUtils(new RecordCallBack() {
			@Override
			public void start() {
				if (callBacks != null) {
					callBacks.start();
				}
			}

			@Override
			public void pause() {
				if (callBacks != null) {
					callBacks.pause();
				}
			}

			@Override
			public void onSuccess(String file, int time) {
				if (callBacks != null) {
					callBacks.onSuccess(file,time);
				}
			}

			@Override
			public void onProgress(int time) {
				if (callBacks != null) {
					callBacks.onProgress(time);
				}
			}

			@Override
			public void onMaxProgress(int time) {
				if (callBacks != null) {
					callBacks.onMaxProgress(time);
				}
			}

			@Override
			public void onError(Exception e) {
				if (callBacks != null) {
					callBacks.onError(e);
				}
			}
		});
		createRecordUtils.setMaxTime(1800);

		notification = DownloadNotification.notify(this, R.drawable.icon_loading, "录音中",
						"录音中", 100);
		 startForeground(1,notification);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		createRecordUtils.clear();
		stopForeground(true);
	}

	public void onTaskRemoved(Intent rootIntent) {
		if (createRecordUtils.hasRecord()){
			createRecordUtils.recordComplete();
		}
		Log.i("RecordService","onTaskRemoved");
		//stop service
		super.onTaskRemoved(rootIntent);
	}


}
