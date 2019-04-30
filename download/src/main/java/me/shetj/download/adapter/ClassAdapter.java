package me.shetj.download.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.shetj.download.R;
import me.shetj.download.base.ClassInfo;

public class ClassAdapter extends BaseQuickAdapter<ClassInfo,TaskItemViewHolder>  {

	private Map<Integer,ClassInfo> infoMap = new HashMap<>();

	public ClassAdapter(@Nullable List<ClassInfo> data) {
		super(R.layout.item_layout_download_class_info,data);
	}

	@Override
	protected void convert(TaskItemViewHolder helper, ClassInfo item) {
		if (item.isDownload){
			helper.getView(R.id.fl_root).setAlpha(0.35f);
			helper.getView(R.id.iv_state).setAlpha(1);
			helper.getView(R.id.iv_state).setVisibility(View.VISIBLE);
		}else {
			helper.getView(R.id.fl_root).setSelected(item.isSelect);
			helper.getView(R.id.tv_title).setSelected(item.isSelect);
			helper.getView(R.id.tv_time).setSelected(item.isSelect);
			helper.getView(R.id.tv_size).setSelected(item.isSelect);
		}
		if (item.isSelect){
			infoMap.put(helper.getAdapterPosition(),item);
		}else {
			infoMap.remove(helper.getAdapterPosition());
		}
	}

	public void downloadAll(){
		for (Integer position : infoMap.keySet()){
			//下载
		}
	}

}
