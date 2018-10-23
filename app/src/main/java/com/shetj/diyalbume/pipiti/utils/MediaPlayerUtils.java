package com.shetj.diyalbume.pipiti.utils;

import android.media.AudioManager;
import android.media.MediaPlayer;

import me.shetj.base.base.BaseCallback;
import me.shetj.base.tools.json.EmptyUtils;
import timber.log.Timber;

/**
 * <b>@packageName：</b> com.shetj.diyalbume.pipiti.utils<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2018/10/23 0023<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */
public class MediaPlayerUtils implements LifecycleListener,
				MediaPlayer.OnPreparedListener,
				MediaPlayer.OnErrorListener,
				MediaPlayer.OnCompletionListener
{
	private MediaPlayer mediaPlayer;
	private BaseCallback<Boolean> mBaseCallBack;
	private String currentUrl = "";

	public MediaPlayerUtils(){
		initMedia();
	}

	/**
	 * 重新播放url
	 * @param url
	 * @param baseCallback
	 */
	private void play(String url,BaseCallback<Boolean> baseCallback){
		this.mBaseCallBack = baseCallback;
		this.currentUrl = url;
		if (null == mediaPlayer){
			initMedia();
		}
		try {
			mediaPlayer.reset();
			mediaPlayer.setDataSource(url);
			mediaPlayer.prepareAsync();
			mediaPlayer.setOnPreparedListener(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取当前播放的url
	 * @return currentUrl
	 */
	public String getCurrentUrl(){
		return currentUrl;
	}

	/**
	 * 通过url 比较 进行播放 还是暂停操作
	 * @param url 播放的url
	 * @param simBaseCallBack 回调
	 */
	public  void playOrStop(String url,BaseCallback<Boolean> simBaseCallBack) {
		//判断是否是当前播放的url
		if (url.equals(getCurrentUrl())){
			if (mediaPlayer != null){
				if (mediaPlayer.isPlaying()){
					mediaPlayer.pause();
					simBaseCallBack.onSuccess(false);
				}else {
					mediaPlayer.start();
					simBaseCallBack.onSuccess(true);
				}
			}
		}else {
			//直接播放
			play(url,simBaseCallBack);
		}
	}


	/**
	 * 清空播放信息
	 */
	private void release(){
		if (EmptyUtils.isNotEmpty(currentUrl)){
			currentUrl = "";
		}
		//释放MediaPlay
		if (null != mediaPlayer) {
			mediaPlayer.release();
			mediaPlayer = null;
		}

	}

	public void pause(){
		if (mediaPlayer !=null && mediaPlayer.isPlaying()) {
			mediaPlayer.pause();
		}
	}

	public void resume(){
		if (mediaPlayer !=null && !mediaPlayer.isPlaying()) {
			mediaPlayer.start();
		}
	}

	public  boolean isPause() {
		return  !(mediaPlayer!=null && mediaPlayer.isPlaying());
	}


	public  void  stopPlay(){
		if (null !=mediaPlayer ){
			mediaPlayer.stop();
			release();
		}
	}

	/**
	 * 设置媒体
	 */
	@SuppressWarnings("AliDeprecation")
	private void initMedia() {
		if (mediaPlayer == null) {
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		}
	}

	@Override
	public void onStart() {
	}

	@Override
	public void onStop() {
		stopPlay();
	}

	@Override
	public void onResume() {
		resume();
	}

	@Override
	public void onPause() {
		pause();
	}

	@Override
	public void onDestroy() {
		release();
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		mp.start();
		mBaseCallBack.onSuccess(true);
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		Timber.i("what = %d extra = %d", what, extra);
		mp.release();
		return false;
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		mBaseCallBack.onSuccess(false);
		release();
	}
}
