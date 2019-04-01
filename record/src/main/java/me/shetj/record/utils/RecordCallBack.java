package me.shetj.record.utils;

public interface RecordCallBack {

	void start();

	void pause();
	/**
	 * 录制成功
	 * @param file
	 */
	void onSuccess(String file) ;

	/**
	 * 返回录制时间长
	 * @param time
	 */
	void onProgress(int time);
}