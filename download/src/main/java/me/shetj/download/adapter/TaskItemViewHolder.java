package me.shetj.download.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.liulishuo.filedownloader.model.FileDownloadStatus;

import me.shetj.download.R;

public  class TaskItemViewHolder extends BaseViewHolder {


  /**
   * viewHolder position
   */
  public int position;
  /**
   * download id
   */
  public int id;

  public TaskItemViewHolder(View view) {
    super(view);
  }

  public void update(final int id, final int position) {
    this.id = id;
    this.position = position;
  }


  public void updateDownloaded() {
    setMax(R.id.task_pb,1);
    setProgress(R.id.task_pb,1);
    setText(R.id.task_status_tv,R.string.tasks_manager_demo_status_completed);
    setText(R.id.task_action_btn,"删除");
  }

  public void updateNotDownloaded(final int status, final long sofar, final long total) {
    if (sofar > 0 && total > 0) {
      final float percent = sofar
              / (float) total;

      setMax(R.id.task_pb,100);
      setProgress(R.id.task_pb,(int) (percent * 100));
    } else {
      setMax(R.id.task_pb,1);
      setProgress(R.id.task_pb,0);
    }
    switch (status) {
      case FileDownloadStatus.error:
       setText(R.id.task_status_tv,R.string.tasks_manager_demo_status_error);
        break;
      case FileDownloadStatus.paused:
        setText(R.id.task_status_tv,R.string.tasks_manager_demo_status_paused);
        break;
      default:
        setText(R.id.task_status_tv,R.string.tasks_manager_demo_status_not_downloaded);
        break;
    }
    setText(R.id.task_action_btn,R.string.start);
  }

  public void updateDownloading(final int status, final long sofar, final long total) {
    final float percent = sofar
            / (float) total;
    setMax(R.id.task_pb,100);
    setProgress(R.id.task_pb,(int) (percent * 100));

    switch (status) {
      case FileDownloadStatus.pending:
        setText(R.id.task_status_tv,R.string.tasks_manager_demo_status_pending);
        break;
      case FileDownloadStatus.started:
        setText(R.id.task_status_tv,R.string.tasks_manager_demo_status_started);
        break;
      case FileDownloadStatus.connected:
        setText(R.id.task_status_tv,R.string.tasks_manager_demo_status_connected);
        break;
      case FileDownloadStatus.progress:
        setText(R.id.task_status_tv,R.string.tasks_manager_demo_status_progress);
        break;
      default:
        setText(R.id.task_status_tv, String.format("状态: 正在下载 %s", String.valueOf(status)));
        break;
    }
    setText(R.id.task_action_btn,R.string.pause);
  }


}