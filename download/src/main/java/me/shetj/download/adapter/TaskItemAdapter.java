package me.shetj.download.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

import java.io.File;
import java.util.List;

import me.shetj.download.R;
import me.shetj.download.base.DownloadInfo;
import me.shetj.download.base.DownloadSampleListener;
import me.shetj.download.base.TasksManager;

public  class TaskItemAdapter extends BaseQuickAdapter<DownloadInfo,TaskItemViewHolder> {

	//下载进度回调
	private FileDownloadListener taskDownloadListener = new DownloadSampleListener();


	public TaskItemAdapter(@Nullable List<DownloadInfo> data) {
		super( R.layout.item_layout_download,data);
	}

	@Override
	protected void convert(final TaskItemViewHolder helper, DownloadInfo model) {
		int  position = helper.getLayoutPosition();

		helper.update(model.getDownloadId(), position,model);

		helper.getView(R.id.iv_action).setTag(helper);
		helper.setText(R.id.tv_name,model.getLabel());
		helper.getView(R.id.iv_action).setOnClickListener(taskActionOnClickListener);

		//给task 绑定helper
		TasksManager.getImpl().updateViewHolder(helper.id, helper,taskDownloadListener);

		//如果已经连接服务
		if (TasksManager.getImpl().isReady()) {
			//下载服务启动
			helper.setVisible(R.id.iv_action,true);
			final int status = TasksManager.getImpl().getStatus(model.getDownloadId(), model.getFileSavePath());
			if (status == FileDownloadStatus.pending || status == FileDownloadStatus.started ||
							status == FileDownloadStatus.connected) {
				//开始下载
				helper.updateDownloading(status, TasksManager.getImpl().getSoFar(model.getDownloadId())
								, TasksManager.getImpl().getTotal(model.getDownloadId()),0);
			} else if (!new File(model.getFileSavePath()).exists() &&
							!new File(FileDownloadUtils.getTempPath(model.getFileSavePath())).exists()) {
				// 下载了部分
				helper.updateNotDownloaded(status,TasksManager.getImpl().getSoFar(model.getDownloadId()),
								TasksManager.getImpl().getTotal(model.getDownloadId()));
			} else if (TasksManager.getImpl().isDownloaded(status)) {
				// 下载完成
				helper.updateDownloaded();
			} else if (status == FileDownloadStatus.progress) {
				// 下载中ing
				helper.updateDownloading(status, TasksManager.getImpl().getSoFar(model.getDownloadId())
								, TasksManager.getImpl().getTotal(model.getDownloadId()),0);

			} else {
				//可能下载了部分
				helper.updateNotDownloaded(status, TasksManager.getImpl().getSoFar(model.getDownloadId()),
								TasksManager.getImpl().getTotal(model.getDownloadId()));
			}
			model.setState(status);
			TasksManager.getImpl().updateDb(model);
		}else {
			//下载服务还未启动
			helper.setVisible(R.id.iv_action,false);
		}


	}

	@Override
	public int getItemCount() {
		return TasksManager.getImpl().getTaskCounts();
	}

	private View.OnClickListener taskActionOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if (v.getTag() == null) {
				return;
			}
			TaskItemViewHolder holder = (TaskItemViewHolder) v.getTag();
			int state = holder.downloadInfo.getState();
			if (state != FileDownloadStatus.paused && state != FileDownloadStatus.completed) {
				// to pause
				FileDownloader.getImpl().pause(holder.id);
				holder.downloadInfo.setState(FileDownloadStatus.paused);
			} else if (state == FileDownloadStatus.paused || state == FileDownloadStatus.error) {
				// to start
				final DownloadInfo model = TasksManager.getImpl().get(holder.position);
				startDownload(holder, model);
				model.setState(FileDownloadStatus.started);
			}
		}
	};

	private void deleteDownload(int position) {
		new File(TasksManager.getImpl().get( position).getFileSavePath()).delete();
		TasksManager.getImpl().delDb(getData().get(position));
		remove(position);
	}

	/**
	 * 开始下载
	 * @param holder
	 * @param model
	 */
	private void startDownload(TaskItemViewHolder holder, DownloadInfo model) {
		BaseDownloadTask task1 = TasksManager.getImpl().getTask(model);
		if (task1 != null && !task1.isRunning()){
			task1.start();
		}else {
			final BaseDownloadTask task = FileDownloader.getImpl().create(model.getDownloadUrl())
							.setPath(model.getFileSavePath())
							.setCallbackProgressTimes(100)
							.setTag(holder)
							.setListener(taskDownloadListener);
			TasksManager.getImpl()
							.addTaskForViewHolder(task);
			holder.update(task.getId(), holder.position, model);
			task.start();
		}
	}

}