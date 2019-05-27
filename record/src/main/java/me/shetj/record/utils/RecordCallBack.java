package me.shetj.record.utils;

/**
 * 录音回调
 * @author shetj
 */
public interface RecordCallBack {

	/**
	 * 开始/重新 录音
	 */
	void start();

	/**
	 * 正在录音
	 */
	void onRecording(int time, int volume);
	/**
	 * 暂停
	 */
	void pause();
	/**
	 * 录制成功
	 */
	void onSuccess(String file, int time) ;

	/**
	 * 返回录制时间长，每一秒触发一次
	 * @param time
	 */
	void onProgress(int time);

	/**
	 * 设置最大进度条，触发
	 */
	void onMaxProgress(int time);

	/**
	 * 计算时间错误时
	 */
	void onError(Exception e);

	/**
	 * 时间到了自动完成除非的操作
	 */
	void autoComplete(String file, int time);

	/**
	 * 触发回去权限
	 */
	void needPermission();

}