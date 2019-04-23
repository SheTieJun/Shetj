package me.shetj.download.base;

import android.app.Activity;
import android.text.TextUtils;
import android.util.SparseArray;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadConnectListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

import java.lang.ref.WeakReference;
import java.util.List;

import me.shetj.download.adapter.TaskItemViewHolder;

public   class TasksManager {
  private final static class HolderClass {
    private final static TasksManager INSTANCE
            = new  TasksManager();
  }

  public static TasksManager getImpl() {
    return HolderClass.INSTANCE;
  }

  private TasksManagerDBController dbController;
  private List<DownloadInfo> modelList;

  private TasksManager() {
    dbController = new TasksManagerDBController();
    modelList = dbController.getAllTasks();
  }

  private SparseArray<BaseDownloadTask> taskSparseArray = new SparseArray<>();

  public void addTaskForViewHolder(final BaseDownloadTask task) {
    taskSparseArray.put(task.getId(), task);
  }

  public void removeTaskForViewHolder(final int id) {
    taskSparseArray.remove(id);
  }

  public void updateViewHolder(final int id, final TaskItemViewHolder holder) {
    final BaseDownloadTask task = taskSparseArray.get(id);
    if (task == null) {
      return;
    }

    task.setTag(holder);
  }

  public void releaseTask() {
    taskSparseArray.clear();
  }

  private FileDownloadConnectListener listener;

  private void registerServiceConnectionListener(final WeakReference<Activity>
                                                         activityWeakReference) {
    if (listener != null) {
      FileDownloader.getImpl().removeServiceConnectListener(listener);
    }

    listener = new FileDownloadConnectListener() {

      @Override
      public void connected() {
        if (activityWeakReference == null
                || activityWeakReference.get() == null) {
          return;
        }
        //刷新
//                    activityWeakReference.get().postNotifyDataChanged();
      }

      @Override
      public void disconnected() {
        if (activityWeakReference == null
                || activityWeakReference.get() == null) {
          return;
        }
        //刷新
//                    activityWeakReference.get().postNotifyDataChanged();
      }
    };

    FileDownloader.getImpl().addServiceConnectListener(listener);
  }

  private void unregisterServiceConnectionListener() {
    FileDownloader.getImpl().removeServiceConnectListener(listener);
    listener = null;
  }

  public void onCreate(final WeakReference<Activity> activityWeakReference) {
    if (!FileDownloader.getImpl().isServiceConnected()) {
      FileDownloader.getImpl().bindService();
      registerServiceConnectionListener(activityWeakReference);
    }
  }

  public void onDestroy() {
    unregisterServiceConnectionListener();
    releaseTask();
  }

  public boolean isReady() {
    return FileDownloader.getImpl().isServiceConnected();
  }

  public DownloadInfo get(final int position) {
    return modelList.get(position);
  }

  public DownloadInfo getById(final int id) {
    for (DownloadInfo model : modelList) {
      if (model.getId() == id) {
        return model;
      }
    }

    return null;
  }

  /**
   * @param status Download Status
   * @return has already downloaded
   * @see FileDownloadStatus
   */
  public boolean isDownloaded(final int status) {
    return status == FileDownloadStatus.completed;
  }

  public int getStatus(final int id, String path) {
    return FileDownloader.getImpl().getStatus(id, path);
  }

  public long getTotal(final int id) {
    return FileDownloader.getImpl().getTotal(id);
  }

  public long getSoFar(final int id) {
    return FileDownloader.getImpl().getSoFar(id);
  }

  public int getTaskCounts() {
    return modelList.size();
  }

  public DownloadInfo addTask(final String url) {
    return addTask(url, createPath(url));
  }

  public DownloadInfo addTask(final String url, final String path) {
    if (TextUtils.isEmpty(url) || TextUtils.isEmpty(path)) {
      return null;
    }

    final int id = FileDownloadUtils.generateId(url, path);
    DownloadInfo model = getById(id);
    if (model != null) {
      return model;
    }
    final DownloadInfo newModel = dbController.addTask(url, path);
    if (newModel != null) {
      modelList.add(newModel);
    }

    return newModel;
  }

  public String createPath(final String url) {
    if (TextUtils.isEmpty(url)) {
      return null;
    }

    return FileDownloadUtils.getDefaultSaveFilePath(url);
  }
}