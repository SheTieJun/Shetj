package me.shetj.upload;

import androidx.annotation.Keep;

/**
 * 上传管理
 * @author shet
 */
@Keep
public interface UploadFileCallBack<T> {
	/**
	 * 进度
	 * @param size 已经上传了几个
	 * @param allSize 一共要上传的数量
	 */
	void progress(long size, long allSize);

	/**
	 * 上传成功
	 * @param file
	 */
	void succeed(T file);

	/**
	 * 错误信息
	 * @param msg
	 */
	void onFail(String msg);
}