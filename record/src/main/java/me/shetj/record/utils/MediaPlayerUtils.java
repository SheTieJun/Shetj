package me.shetj.record.utils;

import android.media.AudioManager;
import android.media.MediaPlayer;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import me.shetj.base.tools.json.EmptyUtils;

/**
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2018/10/23 0023<br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b> {@link MediaPlayerUtils} 音乐播放</b><br>
 *
 *   <b> 播放 {@link MediaPlayerUtils#playOrStop(String url,PlayerListener listener)}}</b><br>
 *   <b> 暂停  {@link MediaPlayerUtils#pause()} <br/>
 *   <b> 恢复  {@link MediaPlayerUtils#resume()} ()} <br/>
 *   <b> 停止  {@link MediaPlayerUtils#stopPlay()} ()} <br/>
 * <br>
 */
@SuppressWarnings("deprecation")
public class MediaPlayerUtils implements LifecycleListener,
				MediaPlayer.OnPreparedListener,
				MediaPlayer.OnErrorListener,
				MediaPlayer.OnCompletionListener,
				MediaPlayer.OnSeekCompleteListener
{
	private CompositeDisposable mCompositeDisposable;
	private MediaPlayer mediaPlayer;
	private PlayerListener listener;
	private String currentUrl = "";
	private PublishSubject<Integer> progressSubject;
	private Disposable timeDisposable;



	public MediaPlayerUtils(){
		initMedia();
	}

	/**
	 * 重新播放url
	 * @param url
	 * @param listener
	 */
	private void play(String url,PlayerListener listener){
		if (null != listener) {
			this.listener = listener;
		}else {
			this.listener = new SimPlayerListener();
		}
		setCurrentUrl(url);
		if (null == mediaPlayer){
			initMedia();
		}
		try {
			mediaPlayer.reset();
			mediaPlayer.setDataSource(url);
			mediaPlayer.prepareAsync();
			//监听
			mediaPlayer.setOnPreparedListener(this);
			mediaPlayer.setOnErrorListener(this);
			mediaPlayer.setOnCompletionListener(this);
			mediaPlayer.setOnSeekCompleteListener(this);
			//是否循环
			if (listener !=null) {
				mediaPlayer.setLooping(listener.isLoop());
			}
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
	public void setCurrentUrl(String url){
		currentUrl = url;
	}
	/**
	 * 通过url 比较 进行播放 还是暂停操作
	 * @param url 播放的url
	 * @param listener 监听变化
	 */
	public  void playOrStop(String url,PlayerListener listener) {

		//判断是否是当前播放的url
		if (url.equals(getCurrentUrl()) && mediaPlayer != null){
			if (listener!=null) {
				this.listener = listener;
			}else {
				this.listener = new SimPlayerListener();
			}
			if (mediaPlayer.isPlaying()){
				pause();
				this.listener.onPause();
			}else {
				resume();
				this.listener.onResume();
			}
		}else {
			//直接播放
			play(url,listener);
		}
	}

	/**
	 * 修改是否循环
	 * @param isLoop
	 */
	public void changeLoop(boolean isLoop){
		if (mediaPlayer != null){
			mediaPlayer.setLooping(isLoop);
		}
	}

	/**
	 * 外部设置进度变化
	 */
	public void  progressChange(int changeSize){
		if (mediaPlayer != null && EmptyUtils.isNotEmpty(getCurrentUrl())) {
			mediaPlayer.seekTo(changeSize);
		}
	}


	/**
	 * 清空播放信息
	 */
	private void release(){
		unDispose();
		if (EmptyUtils.isNotEmpty(currentUrl)){
			currentUrl = "";
		}
		//释放MediaPlay
		if (null != mediaPlayer) {
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}

	/**
	 * 开始计时
	 */
	private void startProgress() {
		if (timeDisposable == null && mediaPlayer != null) {
			timeDisposable = Flowable.interval(0, 500, TimeUnit.MILLISECONDS)
							.subscribeOn(Schedulers.newThread())
							.subscribe(new Consumer<Long>() {
								@Override
								public void accept(Long aLong) throws Exception {
									if (mediaPlayer != null && mediaPlayer.isPlaying()) {
										progressSubject.onNext(mediaPlayer.getCurrentPosition());
									}
								}
							}, new Consumer<Throwable>() {
								@Override
								public void accept(Throwable throwable) throws Exception {
									stopProgress();
								}
							});
			addDispose(timeDisposable);
		}
	}

	/**
	 * 停止计时
	 */
	private void stopProgress(){
		if (timeDisposable !=null && !timeDisposable.isDisposed()){
			timeDisposable.dispose();
			timeDisposable = null;
		}
	}

	/**
	 * 暂停，并且停止计时
	 */
	public void pause(){
		if (mediaPlayer !=null && mediaPlayer.isPlaying()
						&& EmptyUtils.isNotEmpty(getCurrentUrl())) {
			stopProgress();
			mediaPlayer.pause();
			listener.onPause();
		}
	}
	/**
	 * 恢复，并且开始计时
	 */
	public void resume(){
		if (mediaPlayer !=null && !mediaPlayer.isPlaying()
						&& EmptyUtils.isNotEmpty(getCurrentUrl())) {
			mediaPlayer.start();
			listener.onResume();
			startProgress();
		}
	}

	/**
	 * 是否暂停
	 * @return
	 */
	public  boolean isPause() {
		return  !(mediaPlayer!=null && mediaPlayer.isPlaying());
	}


	public  void  stopPlay(){
		if (null !=mediaPlayer ){
			startProgress();
			mediaPlayer.stop();
			listener.onStop();
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

			if (null == listener) {
				listener = new SimPlayerListener();
			}

			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			progressSubject = PublishSubject.create();
			//设置进度控制
			Disposable disposable = progressSubject
							.observeOn(AndroidSchedulers.mainThread())
							.throttleFirst(1,TimeUnit.MILLISECONDS)
							.subscribe(aLong -> listener.onProgress(aLong, mediaPlayer.getDuration()));
			addDispose(disposable);
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
		startProgress();
		listener.onStart(getCurrentUrl());
	}



	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		listener.onError(new Throwable(String.format("what = %d extra = %d", what, extra)));
		release();
		return true;
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		if (!listener.isNext(this)){
			listener.onCompletion();
			stopProgress();
			release();
		}
	}

	/**
	 * 将 {@link Disposable} 添加到 {@link CompositeDisposable} 中统一管理
	 * 可在 {onDestroy() 中使用 {@link #unDispose()} 停止正在执行的 RxJava 任务,避免内存泄漏
	 *
	 * @param disposable
	 */
	private void addDispose(Disposable disposable) {
		if (mCompositeDisposable == null) {
			mCompositeDisposable = new CompositeDisposable();
		}
		mCompositeDisposable.add(disposable);
	}

	/**
	 * 停止集合中正在执行的 RxJava 任务
	 */
	private void unDispose() {
		if (mCompositeDisposable != null) {
			mCompositeDisposable.clear();
		}
	}

	@Override
	public void onSeekComplete(MediaPlayer mp) {
		if (null != mediaPlayer) {
			progressSubject.onNext(mediaPlayer.getCurrentPosition());
		}
	}
}
