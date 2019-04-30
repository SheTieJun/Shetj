package me.shetj.download.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.shetj.download.R;
import me.shetj.download.base.ClassInfo;
import me.shetj.download.base.DownloadInfo;
import me.shetj.download.base.DownloadSampleListener;
import me.shetj.download.base.TasksManager;

public  class ChannelClassItemAdapter extends BaseQuickAdapter<ClassInfo,TaskItemViewHolder> {

	private boolean isDelModel = false;

	private Map<Integer,ClassInfo> downloadInfoMap = new HashMap<>();


	public ChannelClassItemAdapter(@Nullable List<ClassInfo> data) {
		super( R.layout.item_layout_downloaded,data);
	}

	@Override
	protected void convert(final TaskItemViewHolder helper, final ClassInfo model) {


		helper.setGone(R.id.checkbox_del,isDelModel);

		helper.getView(R.id.iv_action).setTag(helper);
		helper.setText(R.id.tv_name,model.title);

		helper.setOnCheckedChangeListener(R.id.checkbox_del, new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
					if (b){
						downloadInfoMap.put(helper.getAdapterPosition(),model);
					}else {
						downloadInfoMap.remove(helper.getAdapterPosition());
					}
			}
		});
	}

	@Override
	public int getItemCount() {
		return TasksManager.getImpl().getTaskCounts();
	}


	private void deleteDownload(int position) {
		remove(position);
	}

	public void delAll(){
		for (Integer position : downloadInfoMap.keySet()){
			remove(position);
		}
	}


	/**
	 * 设置是不是删除的model
	 * @param isDelModel 删除模式
	 */
	public void setDelModel(boolean isDelModel) {
		this.isDelModel = isDelModel;
		if (!isDelModel){
			downloadInfoMap.clear();
		}
		notifyDataSetChanged();
	}
}