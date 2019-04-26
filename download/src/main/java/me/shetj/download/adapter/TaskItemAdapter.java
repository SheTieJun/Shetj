package me.shetj.download.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadList;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
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

		helper.getView(R.id.task_action_btn).setTag(helper);
		helper.setText(R.id.task_name_tv,model.getLabel());
		helper.getView(R.id.task_action_btn).setOnClickListener(taskActionOnClickListener);

		//给task 绑定helper
		TasksManager.getImpl().updateViewHolder(helper.id, helper,taskDownloadListener);

		//如果已经连接服务
		if (TasksManager.getImpl().isReady()) {
			//下载服务启动
			helper.getView(R.id.task_action_btn).setEnabled(true);

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
		}else {
			//下载服务还未启动
			helper.setText(R.id.task_action_btn,R.string.tasks_manager_demo_status_loading);
			helper.getView(R.id.task_action_btn).setEnabled(false);
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

			CharSequence action = ((TextView) v).getText();
			if (action.equals(v.getResources().getString(R.string.pause))) {
				// to pause
				FileDownloader.getImpl().pause(holder.id);
			} else if (action.equals(v.getResources().getString(R.string.start))) {
				// to start
				final DownloadInfo model = TasksManager.getImpl().get(holder.position);
				startDownload(holder, model);
			} else if (action.equals(v.getResources().getString(R.string.delete))) {
				// to delete
				new File(TasksManager.getImpl().get(holder.position).getFileSavePath()).delete();
				holder.getView(R.id.task_action_btn).setEnabled(true);
				TasksManager.getImpl().delDb(holder.downloadInfo);
			}
		}
	};

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