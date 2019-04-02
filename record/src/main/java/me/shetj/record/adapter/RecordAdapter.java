package me.shetj.record.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import me.shetj.record.R;
import me.shetj.record.bean.Record;
import me.shetj.record.utils.LifecycleListener;
import me.shetj.record.utils.MediaPlayerUtils;
import me.shetj.record.utils.PlayerListener;
import me.shetj.record.utils.SimPlayerListener;
import me.shetj.record.utils.Util;

/**
 * 录音列表
 * @author shetj
 */
public class RecordAdapter extends BaseQuickAdapter<Record, BaseViewHolder> implements LifecycleListener {

	private int position = -1;
	private MediaPlayerUtils mediaUtils;
	public RecordAdapter(@Nullable List<Record> data) {
		super(R.layout.item_view_root,data);
		mediaUtils = new MediaPlayerUtils();
	}

	@Override
	protected void convert(BaseViewHolder helper, Record item) {
		helper.setText(R.id.tv_name,item.getAudioName())
						.setGone(R.id.tv_time,position != helper.getLayoutPosition())
						.setGone(R.id.rl_record_view,position == helper.getLayoutPosition())
						.setText(R.id.tv_time_all,Util.formatSeconds2(item.getAudioLength()))
						.setText(R.id.tv_read_time,Util.formatSeconds2(0))
						.setText(R.id.tv_time, "时长："+Util.formatSeconds2(item.getAudioLength()))
						.setProgress(R.id.progressBar_record,0,item.getAudioLength());
		Util.showMPTime2(item.getAudio_url());

		helper.getView(R.id.iv_play).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				playMusic(item.getAudio_url(),new SimPlayerListener(){
					@Override
					public void onPause() {
						super.onPause();
						helper.setImageResource(R.id.iv_play,R.mipmap.icon_record_start);
					}

					@Override
					public void onStart(String url) {
						super.onStart(url);
						helper.setImageResource(R.id.iv_play,R.mipmap.icon_record_pause);
					}

					@Override
					public void onResume() {
						super.onResume();
						helper.setImageResource(R.id.iv_play,R.mipmap.icon_record_pause);
					}

					@Override
					public void onStop() {
						super.onStop();
						helper.setProgress(R.id.progressBar_record,0);
						helper.setImageResource(R.id.iv_play,R.mipmap.icon_record_start);
					}

					@Override
					public void onCompletion() {
						super.onCompletion();
						helper.setImageResource(R.id.iv_play,R.mipmap.icon_record_start);
					}

					@Override
					public void onProgress(int current, int size) {
						super.onProgress(current, size);
						helper.setProgress(R.id.progressBar_record,current,size);
					}
				});
			}
		});

	}

	public void setPlayPosition(int targetPos) {

		if (position != targetPos) {
			if (null != mediaUtils) {
				mediaUtils.stopPlay();
			}
		}

		if (targetPos == -1 && position != -1){
			int old = position;
			position = -1;
			notifyItemChanged(old);
			return;
		}
		if (position != targetPos){
			int old = position;
			this.position = targetPos;
			notifyItemChanged(old);
			notifyItemChanged(targetPos);
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
		}
	}

}