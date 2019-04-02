package me.shetj.record.utils;

/**
 * 录音回调
 * @author shetj
 */
public class SimRecordCallBack {

	/**
	 * 开始/重新 录音
	 */
	void start(){

	}

	/**
	 * 暂停
	 */
	void pause(){

	}
	/**
	 * 录制成功
	 * @param file
	 * @param time
	 */
	void onSuccess(String file, int time){

	}

	/**
	 * 返回录制时间长
	 * @param time
	 */
	void onProgress(int time){

	}

	/**
	 * 设置最大进度条
	 */
	void onMaxProgress(int time){

	}

	/**
	 * 计算时间错误时
	 */
	void onError(Exception e){

	}
}