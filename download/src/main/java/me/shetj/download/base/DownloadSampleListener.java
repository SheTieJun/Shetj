package me.shetj.download.base;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.model.FileDownloadStatus;

import me.shetj.download.R;
import me.shetj.download.adapter.TaskItemViewHolder;

public class DownloadSampleListener extends FileDownloadSampleListener {

	private TaskItemViewHolder checkCurrentHolder(final BaseDownloadTask task) {
		final TaskItemViewHolder tag = (TaskItemViewHolder) task.getTag();
		if (tag.id != task.getId()) {
			return null;
		}
		return tag;
	}

	@Override
	protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
		super.pending(task, soFarBytes, totalBytes);
		final TaskItemViewHolder tag = checkCurrentHolder(task);
		if (tag == null) {
			return;
		}
		tag.updateDownloading(FileDownloadStatus.pending, soFarBytes
						, totalBytes,task.getSpeed());
		tag.setText(R.id.task_status_tv,R.string.tasks_manager_demo_status_pending);
	}

	@Override
	protected void started(BaseDownloadTask task) {
		super.started(task);
		final TaskItemViewHolder tag = checkCurrentHolder(task);
		if (tag == null) {
			return;
		}
		tag.setText(R.id.task_status_tv,R.string.tasks_manager_demo_status_started);
	}

	@Override
	protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
		super.connected(task, etag, isContinue, soFarBytes, totalBytes);
		final TaskItemViewHolder tag = checkCurrentHolder(task);
		if (tag == null) {
			return;
		}
		tag.updateDownloading(FileDownloadStatus.connected, soFarBytes, totalBytes,task.getSpeed());
		tag.setText(R.id.task_status_tv,R.string.tasks_manager_demo_status_connected);
	}

	@Override
	protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
		super.progress(task, soFarBytes, totalBytes);
		final TaskItemViewHolder tag = checkCurrentHolder(task);
		if (tag == null) {
			return;
		}

		tag.updateDownloading(FileDownloadStatus.progress, soFarBytes, totalBytes,task.getSpeed());
	}

	@Override
	protected void error(BaseDownloadTask task, Throwable e) {
		super.error(task, e);
		final TaskItemViewHolder tag = checkCurrentHolder(task);
		if (tag == null) {
			return;
		}

		tag.updateNotDownloaded(FileDownloadStatus.error, task.getLargeFileSoFarBytes()
						, task.getLargeFileTotalBytes());
		TasksManager.getImpl().removeTaskForViewHolder(task.getId());
	}

	@Override
	protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
		super.paused(task, soFarBytes, totalBytes);
		final TaskItemViewHolder tag = checkCurrentHolder(task);
		if (tag == null) {
			return;
		}
		tag.updateNotDownloaded(FileDownloadStatus.paused, soFarBytes, totalBytes);
		TasksManager.getImpl().removeTaskForViewHolder(task.getId());
	}

	@Override
	protected void completed(BaseDownloadTask task) {
		super.completed(task);
		final TaskItemViewHolder tag = checkCurrentHolder(task);
		if (tag == null) {
			return;
		}

		tag.updateDownloaded();
		TasksManager.getImpl().removeTaskForViewHolder(task.getId());
	}
}
