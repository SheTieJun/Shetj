package me.shetj.record.utils;

/**
 * <b>@packageName：</b> com.shetj.diyalbume.pipiti.utils<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2018/10/24 0024<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */
public interface PlayerListener {

	/**
	 * 开始播放
	 * @param url 播放路径
	 */
	void onStart(String url);

	/**
	 * 暂停
	 */
	void onPause();

	/**
	 * 继续播放
	 */
	void onResume();

	/**
	 * 停止释放
	 */
	void onStop();

	/**
	 * 播放结束
	 */
	void onCompletion();

	/**
	 * 错误
	 * @param throwable
	 */
	void onError(Throwable throwable);

	/**
	 * 是否循环
	 * @return  true 是  false 结束
	 */
	boolean isLoop();

	/**
	 * 是否下一首
	 * @param mp 音乐播放器
	 * @return true 是  false 结束
	 */
	boolean isNext(MediaPlayerUtils mp);

	/**
	 * 进度条
	 * @param current 当前播放位置
	 * @param size 一共
	 */
	void onProgress(int current, int size);
}
