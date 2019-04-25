package me.shetj.download.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

import java.io.File;
import java.util.List;

import me.shetj.download.R;
import me.shetj.download.base.DownloadInfo;
import me.shetj.download.base.TasksManager;

public  class TaskItemAdapter extends BaseQuickAdapter<DownloadInfo,TaskItemViewHolder> {


    private FileDownloadListener taskDownloadListener = new FileDownloadSampleListener() {

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
                    , totalBytes);
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
            tag.updateDownloading(FileDownloadStatus.connected, soFarBytes, totalBytes);
            tag.setText(R.id.task_status_tv,R.string.tasks_manager_demo_status_connected);
        }

        @Override
        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            super.progress(task, soFarBytes, totalBytes);
            final TaskItemViewHolder tag = checkCurrentHolder(task);
            if (tag == null) {
                return;
            }

            tag.updateDownloading(FileDownloadStatus.progress, soFarBytes, totalBytes);
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
    };
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
                final BaseDownloadTask task = FileDownloader.getImpl().create(model.getDownloadUrl())
                        .setPath(model.getFileSavePath())
                        .setCallbackProgressTimes(100)
                        .setTag(holder)
                        .setListener(taskDownloadListener);
                holder.update(task.getId(),holder.position);
                TasksManager.getImpl()
                        .addTaskForViewHolder(task);
                task.start();
            } else if (action.equals(v.getResources().getString(R.string.delete))) {
                // to delete
                new File(TasksManager.getImpl().get(holder.position).getFileSavePath()).delete();
                holder.getView(R.id.task_action_btn).setEnabled(true);
                holder.updateNotDownloaded(FileDownloadStatus.INVALID_STATUS, 0, 0);
            }
        }
    };

    public TaskItemAdapter(@Nullable List<DownloadInfo> data) {
        super( R.layout.item_layout_download,data);
    }

    @Override
    protected void convert(final TaskItemViewHolder helper, DownloadInfo model) {
        int  position = helper.getLayoutPosition();
        helper.update(model.getId(), position);
        TasksManager.getImpl()
                .updateViewHolder(helper.id, helper);
        helper.getView(R.id.task_action_btn).setTag(helper);
        helper.setText(R.id.task_name_tv,model.getLabel());
        helper.getView(R.id.task_action_btn).setOnClickListener(taskActionOnClickListener);
        if (TasksManager.getImpl().isReady()) {
            helper.getView(R.id.task_action_btn).setEnabled(true);
            final int status = TasksManager.getImpl().getStatus(model.getId(), model.getFileSavePath());
            if (status == FileDownloadStatus.pending || status == FileDownloadStatus.started ||
                    status == FileDownloadStatus.connected) {
                // start task, but file not created yet
                helper.updateDownloading(status, TasksManager.getImpl().getSoFar(model.getId())
                        , TasksManager.getImpl().getTotal(model.getId()));
            } else if (!new File(model.getFileSavePath()).exists() &&
                    !new File(FileDownloadUtils.getTempPath(model.getFileSavePath())).exists()) {
                // not exist file
                helper.updateNotDownloaded(status, 0, 0);
            } else if (TasksManager.getImpl().isDownloaded(status)) {
                // already downloaded and exist
                helper.updateDownloaded();
            } else if (status == FileDownloadStatus.progress) {
                // downloading
                helper.updateDownloading(status, TasksManager.getImpl().getSoFar(model.getId())
                        , TasksManager.getImpl().getTotal(model.getId()));
            } else {
                // not start
                helper.updateNotDownloaded(status, TasksManager.getImpl().getSoFar(model.getId())
                        , TasksManager.getImpl().getTotal(model.getId()));
            }
        } else {
            helper.setText(R.id.task_action_btn,R.string.tasks_manager_demo_status_loading);
            helper.getView(R.id.task_action_btn).setEnabled(false);
        }
    }

    @Override
    public int getItemCount() {
        return TasksManager.getImpl().getTaskCounts();
    }
}