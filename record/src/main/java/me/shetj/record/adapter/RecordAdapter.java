package me.shetj.record.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import me.shetj.record.R;
import me.shetj.record.bean.Record;

public class RecordAdapter extends BaseQuickAdapter<Record, BaseViewHolder> {

	public RecordAdapter(@Nullable List<Record> data) {
		super(R.layout.item_view,data);
	}

	@Override
	protected void convert(BaseViewHolder helper, Record item) {
		helper.setText(R.id.tv_name,item.name)
						.setText(R.id.tv_time,item.time);

	}
}