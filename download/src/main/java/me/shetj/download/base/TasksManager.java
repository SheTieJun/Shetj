package me.shetj.download.base;

import android.app.Activity;
import android.text.TextUtils;
import android.util.SparseArray;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadConnectListener;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.simple.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.util.List;

import me.shetj.base.tools.app.ArmsUtils;
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

  public void updateViewHolder(final int id, final TaskItemViewHolder holder, FileDownloadListener taskDownloadListener) {
    final BaseDownloadTask task = taskSparseArray.get(id);
    if (task == null) {
      return;
    }
    task.setTag(holder)
        .setListener(taskDownloadListener);
  }

  public void releaseTask() {
    taskSparseArray.clear();
  }
  public List<DownloadInfo> getAllTask() {
    return modelList;
  }
  private FileDownloadConnectListener listener;

  private void registerServiceConnectionListener(final WeakReference<RxAppCompatActivity>
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
        EventBus.getDefault().post("refresh","refresh");
      }

      @Override
      public void disconnected() {
        if (activityWeakReference == null
                || activityWeakReference.get() == null) {
          return;
        }
        //刷新
        EventBus.getDefault().post("refresh","refresh");
      }
    };

    FileDownloader.getImpl().addServiceConnectListener(listener);
  }

  private void unregisterServiceConnectionListener() {
    FileDownloader.getImpl().removeServiceConnectListener(listener);
    listener = null;
  }

  public void onCreate(final WeakReference<RxAppCompatActivity> activityWeakReference) {
    if (!isReady()) {
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
      if (model.getDownloadId() == id) {
        return model;
      }
    }

    return null;
  }

  /**
   * 开始下载
   * @param model
   */
  public void startDownload( DownloadInfo model) {
    BaseDownloadTask task = FileDownloader.getImpl().create(model.getDownloadUrl())
            .setPath(model.getFileSavePath())
            .setCallbackProgressTimes(100);
    addTaskForViewHolder(task);
    task.start();
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
    //如果已经在下载列表 就不要下载了
    if (model != null) {
      ArmsUtils.makeText("已经存在相同的");
      return null;
    }

    final DownloadInfo newModel = dbController.addTask(url, path);
    //如果不在，就加入到列表下载了
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

  public void updateDb(DownloadInfo downloadInfo){
    dbController.updateDownloadInfo(downloadInfo);
  }

  public void delDb(DownloadInfo downloadInfo) {
    getAllTask().remove(downloadInfo);
    dbController.delDownloadInfo(downloadInfo);
  }


}