package me.shetj.record;


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

		public void setTime(int audioLength) {
			createRecordUtils.setTime(audioLength);
		}

		public void statOrPause() {
			createRecordUtils.statOrPause();
		}

		public boolean hasRecord() {
			return createRecordUtils.hasRecord();
		}

		public void pause() {
			createRecordUtils.pause();
		}

		public void clear() {
			createRecordUtils.clear();
		}

		public boolean isRecording() {
			return createRecordUtils.isRecording();
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
		callBacks = callBack;
		stopForeground(true);
		return false;
	}


	@Override
	public void init() {
		createRecordUtils = new CreateRecordUtils(new RecordCallBack() {
			@Override
			public void start() {
				if (callBacks != null) {
					callBacks.start();
					startForeground("RecordService".hashCode(),RecordingNotification.INSTANCE.getNotification(1,RecordService.this));
				}
			}

			@Override
			public void onRecording(int time, int volume) {

			}

			@Override
			public void pause() {
				if (callBacks != null) {
					callBacks.pause();
					startForeground("RecordService".hashCode(),RecordingNotification.INSTANCE.getNotification(2,RecordService.this));
				}
			}

			@Override
			public void onSuccess(String file, int time) {
				if (callBacks != null) {
					callBacks.onSuccess(file,time);
					startForeground("RecordService".hashCode(),RecordingNotification.INSTANCE.getNotification(3,RecordService.this));
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

			@Override
			public void autoComplete(String file, int time) {
				if (callBacks != null) {
					callBacks.autoComplete(file, time);
				}
			}

			@Override
			public void needPermission() {
				if (callBacks != null) {
					callBacks.needPermission();
				}
			}
		});
		createRecordUtils.setMaxTime(1800);


	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		createRecordUtils.clear();
		stopForeground(true);
	}

	@Override
	public void onTaskRemoved(Intent rootIntent) {
		if (createRecordUtils.hasRecord()){
			createRecordUtils.recordComplete();
		}
		Log.i("RecordService","onTaskRemoved");
		//stop service
		super.onTaskRemoved(rootIntent);
	}


}
