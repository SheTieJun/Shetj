package me.shetj.record.utils;

public interface MpCallback {
	/**
	 * 录制成功
	 * @param time
	 */
	void onSuccess( int time) ;


	/**
	 * 计算时间错误时
	 */
	void onError(Exception e);
}
