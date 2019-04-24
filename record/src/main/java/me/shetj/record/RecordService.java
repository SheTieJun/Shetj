package me.shetj.record;


import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

import me.shetj.base.base.BaseService;
import me.shetj.record.bean.Record;
import me.shetj.record.utils.CreateRecordUtils;
import me.shetj.record.utils.RecordCallBack;

public class RecordService extends BaseService {


	private List<RecordCallBack> callBacks = new LinkedList<>();
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
		if (callBacks != null) {
			callBacks.add(callBack);
		}
	}

	/**
	 * 注销接口 false注销失败
	 *
	 * @param callBack
	 * @return
	 */
	public boolean unRegisterCallBack(RecordCallBack callBack) {
		if (callBacks != null && callBacks.contains(callBack)) {
			return callBacks.remove(callBack);
		}
		return false;
	}


	@Override
	public void init() {
		createRecordUtils = new CreateRecordUtils(new RecordCallBack() {
			@Override
			public void start() {
				for (RecordCallBack callBack : callBacks) {
					callBack.start();
				}

			}

			@Override
			public void pause() {
				for (RecordCallBack callBack : callBacks) {
					callBack.pause();
				}
			}

			@Override
			public void onSuccess(String file, int time) {
				for (RecordCallBack callBack : callBacks) {
					callBack.onSuccess(file,time);
				}
			}

			@Override
			public void onProgress(int time) {
				for (RecordCallBack callBack : callBacks) {
					callBack.onProgress(time);
				}
			}

			@Override
			public void onMaxProgress(int time) {
				for (RecordCallBack callBack : callBacks) {
					callBack.onMaxProgress(time);
				}
			}

			@Override
			public void onError(Exception e) {
				for (RecordCallBack callBack : callBacks) {
					callBack.onError(e);
				}
			}
		});
		createRecordUtils.setMaxTime(1800);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		createRecordUtils.clear();
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
