package me.shetj.download.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import me.shetj.download.base.DownloadInfo;

public class DownloadAdapter extends BaseMultiItemQuickAdapter<DownloadInfo,BaseViewHolder> {

	public DownloadAdapter(List data) {
		super(data);
	}

	@Override
	protected void convert(BaseViewHolder helper, DownloadInfo item) {

	}

	@Override
	public void onBindViewHolder(@NonNull BaseViewHolder viewHolder, int i) {

	}
}
