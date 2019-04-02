package me.shetj.record.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import me.shetj.record.R;
import me.shetj.record.bean.Record;
import me.shetj.record.utils.Util;

/**
 * 录音列表
 * @author shetj
 */
public class RecordAdapter extends BaseQuickAdapter<Record, BaseViewHolder> {


	public RecordAdapter(@Nullable List<Record> data) {
		super(R.layout.item_view_root,data);
	}

	@Override
	protected void convert(BaseViewHolder helper, Record item) {
		helper.setText(R.id.tv_name,item.getAudioName())
						.setText(R.id.tv_time, "时长："+Util.formatSeconds2(item.getAudioLength()));
		Util.showMPTime2(item.getAudio_url());
	}

}