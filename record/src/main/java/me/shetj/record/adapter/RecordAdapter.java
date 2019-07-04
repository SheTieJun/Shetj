package me.shetj.record.adapter;

import android.animation.ValueAnimator;
import android.graphics.Color;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.io.File;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import me.shetj.base.tools.app.ArmsUtils;
import me.shetj.record.R;
import me.shetj.record.bean.Record;
import me.shetj.record.utils.LifecycleListener;
import me.shetj.record.utils.MediaPlayerUtils;
import me.shetj.record.utils.PlayerListener;
import me.shetj.record.utils.SimPlayerListener;
import me.shetj.record.utils.Util;


/**
 * 录音列表
 * 上传动画描述：
 *  1.首先是获取token 时，使用一闪一闪动画 showAlphaAnimator
 *  2.获取到token的时候，上传先执行一个1.5秒的进度动画（0~40）
 *  3.如果在1.5秒执行成功，就在执行一个平滑动画（progress-100）
 *  4.如果1.5后还么有上传成功，就回调变化进度
 *  5.上传成功后重置
 * @author shetj
 */
public class RecordAdapter extends BaseQuickAdapter<Record, BaseViewHolder> implements LifecycleListener {

	private int position = -1;
	private MediaPlayerUtils mediaUtils;//播放
	private CompositeDisposable mCompositeDisposable;
	private int color = Color.parseColor("#FFFFBB22");
	private boolean isUploading = false;

	public RecordAdapter(@Nullable List<Record> data) {
		super(R.layout.item_record_view,data);
		mediaUtils = new MediaPlayerUtils();
	}

	/**
	 * 是否是上传中,上传的时候不能点击其他区域，可以返回
	 */
	public boolean isUploading() {
		return isUploading;
	}

	@Override
	protected void convert(BaseViewHolder helper, Record item) {
		int itemPosition = helper.getLayoutPosition() - getHeaderLayoutCount();
		helper.setText(R.id.tv_name,item.getAudioName())
						.setGone(R.id.tv_time,position != itemPosition)
						.setGone(R.id.rl_record_view2,position == itemPosition)
						.setText(R.id.tv_time_all, Util.formatSeconds3(item.getAudioLength()))
						.setText(R.id.tv_read_time,Util.formatSeconds3(0))
						.setProgress(R.id.progressbar_upload,0)
						.setText(R.id.tv_time, Util.formatSeconds2(item.getAudioLength()))
						.addOnClickListener(R.id.tv_more);
		SeekBar seekBar = helper.getView(R.id.seekBar_record);
		seekBar.setMax(item.getAudioLength()*1000);
		SimPlayerListener listener = new SimPlayerListener(helper);
		//拖动
		seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				if (null != mediaUtils ) {
					if (mediaUtils.getCurrentUrl().equals(item.getAudio_url())) {
						mediaUtils.seekTo(seekBar.getProgress());
					}
				}
				helper.setText(R.id.tv_read_time,Util.formatSeconds3(seekBar.getProgress()/1000));
			}
		});

		if (position == itemPosition){
			//每次点击就切换播放源
			if (new File(item.getAudio_url()).exists()) {
				mediaUtils.playNoStart(item.getAudio_url(), listener);
			}else {
				ArmsUtils.makeText("当前选中文件已经丢失~，请删除该记录后重新录制！");
			}
		}
		//播放
		helper.getView(R.id.iv_play).setOnClickListener(view -> playMusic(item.getAudio_url(), listener));
		//上传
		helper.getView(R.id.tv_upload).setOnClickListener(v -> startUpload(helper, item));
	}

	/**
	 * 把界面收起来，停止播放音乐，开始上传
	 */
	private void startUpload(BaseViewHolder helper, Record item) {
		helper.setGone(R.id.rl_record_view2,false)
						.setVisible(R.id.tv_time,true);
		position = -1;
		if (null != mediaUtils &&!mediaUtils.isPause()) {
			mediaUtils.pause();
		}
		uploadMusic(item.getAudio_url(),helper.getView(R.id.progressbar_upload),helper.getView(R.id.tv_progress));
	}

	/**
	 * 上传
	 */
	private void uploadMusic(String audioUrl, ProgressBar progressBar, TextView tvProgress) {
		if (!new File(audioUrl).exists()){
			ArmsUtils.makeText("当前选中文件已经丢失~，请删除该记录后重新录制！");
			return;
		}
		isUploading = true;
		getRecyclerView().setAlpha(0.7f);
		ValueAnimator valueAnimator =  showAnimator(progressBar,tvProgress,0,100,1500);
		progressBar.setAlpha(1);
		//开始执行进度动画
		valueAnimator.start();
	}

	/**
	 * 展示进度动画
	 */
	private ValueAnimator showAnimator(ProgressBar progressBar, TextView tvProgress, int start , int end, int time){
		ValueAnimator valueAnimator  = ValueAnimator.ofInt(start,end);
		valueAnimator.setDuration(time);
		valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
		valueAnimator.addUpdateListener(animation ->{
			int animatedValue = (int) animation.getAnimatedValue();
			progressBar.setProgress(animatedValue);
			progressBar.setVisibility(View.VISIBLE);
			tvProgress.setVisibility(View.VISIBLE);
			tvProgress.setText(String.format("%s%%", String.valueOf(animatedValue)));
		});
		return valueAnimator;
	}



	/**
	 * 设置选中的位置
	 */
	public void setPlayPosition(int targetPos) {
		if (isUploading){
			ArmsUtils.makeText( "正在上传...");
			return;
		}
		//停止音乐
		if (targetPos==-1 || position != targetPos) {
			if (null != mediaUtils &&!mediaUtils.isPause()) {
				mediaUtils.pause();
			}
		}
		//如果不相等，说明有变化
		if (position != targetPos){
			int old = position;
			this.position = targetPos;
			// -1 表示默认不做任何变化
			if (old != -1) {
				notifyItemChanged(old + getHeaderLayoutCount());
			}
			if(targetPos !=-1) {
				notifyItemChanged(targetPos + getHeaderLayoutCount());
			}
		}
		if (position  != -1){
			getRecyclerView().smoothScrollToPosition(targetPos+1);
		}

	}

	public int getCurPosition(){
		return position;
	}


	public void playMusic(String url, PlayerListener listener) {
		if ( mediaUtils != null ) {
			mediaUtils.playOrStop(url, listener);
		}
	}

	@Override
	public void onStart() {
	}

	@Override
	public void onStop() {
		if (null != mediaUtils) {
			mediaUtils.stopPlay();
		}
	}

	@Override
	public void onResume() {
		if (null != mediaUtils) {
			mediaUtils.onResume();
		}
	}

	@Override
	public void onPause() {
		if (null != mediaUtils) {
			mediaUtils.onPause();
		}
	}

	@Override
	public void onDestroy() {
		if (null != mediaUtils) {
			mediaUtils.onDestroy();
			unDispose();
		}
	}
	/**
	 * 将 {@link Disposable} 添加到 {@link CompositeDisposable} 中统一管理
	 * 可在 {onDestroy() 中使用 {@link #unDispose()} 停止正在执行的 RxJava 任务,避免内存泄漏
	 *
	 * @param disposable
	 */
	public void addDispose(Disposable disposable) {
		if (mCompositeDisposable == null) {
			mCompositeDisposable = new CompositeDisposable();
		}
		mCompositeDisposable.add(disposable);
	}

	/**
	 * 停止集合中正在执行的 RxJava 任务
	 */
	public void unDispose() {
		if (mCompositeDisposable != null) {
			mCompositeDisposable.clear();
		}
	}

}