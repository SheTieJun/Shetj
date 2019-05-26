package me.shetj.record.utils;

import android.util.Log;
import android.widget.SeekBar;

import com.chad.library.adapter.base.BaseViewHolder;

import me.shetj.record.R;

/**
 * <b>@packageName：</b> com.shetj.diyalbume.pipiti.utils<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2018/10/24 0024<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */
public class SimPlayerListener implements PlayerListener {
	private BaseViewHolder helper;
	private SeekBar seekBar;
	public SimPlayerListener(BaseViewHolder helper) {
		this.helper = helper;
		this.seekBar= helper.getView(R.id.seekBar_record);
	}

	@Override
	public void onPause() {
			helper.setImageResource(R.id.iv_play, R.drawable.selector_weike_record_play);
	}

	@Override
	public void onStart(String url) {
		helper.setImageResource(R.id.iv_play,R.drawable.selector_weike_record_pause);
	}

	@Override
	public void onResume() {
		helper.setImageResource(R.id.iv_play,R.drawable.selector_weike_record_pause);
	}

	@Override
	public void onStop() {
		seekBar.setProgress(0);
		helper.setImageResource(R.id.iv_play,R.drawable.selector_weike_record_play)
						.setText(R.id.tv_read_time,Util.formatSeconds3(0));;
	}

	@Override
	public void onCompletion() {
		seekBar.setProgress(0);
		helper.setText(R.id.tv_read_time, Util.formatSeconds3(0));
		helper.setImageResource(R.id.iv_play,R.drawable.selector_weike_record_play);
	}

	@Override
	public void onError(Throwable throwable) {
		Log.e("SimPlayerListener",throwable.getMessage());
	}

	@Override
	public boolean isLoop() {
		return false;
	}

	@Override
	public boolean isNext(MediaPlayerUtils mp) {
		return false;
	}

	@Override
	public void onProgress(int current, int size) {
		if (current != size) {
			seekBar.setProgress(current);
			helper.setText(R.id.tv_read_time, Util.formatSeconds3(current / 1000));
		}
	}

}
