package me.shetj.download.base;

import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadConnectListener;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

import org.simple.eventbus.EventBus;

import java.util.List;

import me.shetj.base.tools.app.ArmsUtils;
import me.shetj.download.adapter.TaskItemViewHolder;
import timber.log.Timber;

public   class TasksManager {


  private final static class HolderClass {
    private final static TasksManager INSTANCE
            = new  TasksManager();
  }

  public static TasksManager getImpl() {
    return HolderClass.INSTANCE;
  }

  private TasksDBController dbController;

  //同步保存数据库的数据
  private List<DownloadInfo> modelList;

  //用来记录所有的task
  private SparseArray<BaseDownloadTask> taskSparseArray = new SparseArray<>();

  //服务是否连接的监听
  private FileDownloadConnectListener listener;

  private TasksManager() {
    dbController = new TasksDBController();
    modelList = dbController.getAllTasks();
  }

  public void onCreate( ) {
    if (!isReady()) {
      FileDownloader.getImpl().bindService();
      registerServiceConnectionListener();
    }
  }

  public boolean isReady() {
    return FileDownloader.getImpl().isServiceConnected();
  }


  /************************** task 和 viewHolder 之间的操作 ****************************************/

  /**
   * 添加task进行全局控制
   * @param task
   */
  public void addTaskForViewHolder(final BaseDownloadTask task) {
    taskSparseArray.put(task.getId(), task);
  }

  /**
   * 移除task
   * @param id
   */
  public void removeTaskForViewHolder(final int id) {
    taskSparseArray.remove(id);
  }

  /**
   * 给task 重新绑定 holder
   * @param id
   * @param holder
   * @param taskDownloadListener
   */
  public void updateViewHolder(final int id, final TaskItemViewHolder holder, FileDownloadListener taskDownloadListener) {
    final BaseDownloadTask task = taskSparseArray.get(id);
    if (task == null) {
      return;
    }
    task.setTag(holder)
            .setListener(taskDownloadListener);
  }

  /************************** public method  ****************************************/


  /**
   * 获取所有的任务
   * @return
   */
  public List<DownloadInfo> getAllTask() {
    return modelList;
  }

  /**
   * 添加任务
   * @param url
   * @return
   */
  public DownloadInfo addTask(final String url) {
    return addTask(url, createPath(url));
  }

  /**
   * 暂停任务
   * @param downloadInfo
   */
  public void pause(DownloadInfo downloadInfo){
    FileDownloader.getImpl().pause(downloadInfo.getDownloadId());
  }


  /**
   * 暂停所有任务
   */
  public void pauseAll(){
    for (DownloadInfo downloadInfo : modelList){
      pause(downloadInfo);
    }
  }

  /**
   * 获取任务
   * @param model
   * @return
   */
  public BaseDownloadTask getTask(DownloadInfo model) {
    return taskSparseArray.get(model.getDownloadId());
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
   * 更新数据
   * @param downloadInfo
   */
  public void updateDb(DownloadInfo downloadInfo){
    dbController.updateDownloadInfo(downloadInfo);
  }

  /**
   * 删除数据
   * @param downloadInfo
   */
  public void delDb(DownloadInfo downloadInfo) {
    getAllTask().remove(downloadInfo);
    dbController.delDownloadInfo(downloadInfo);
  }

  /**
   * 结束
   */
  public void onDestroy() {
    unregisterServiceConnectionListener();
  }


  /**
   * @param status Download Status
   * @return has already downloaded
   * @see FileDownloadStatus
   */
  public boolean isDownloaded(final int status) {
    return status == FileDownloadStatus.completed;
  }


  /**
   * 开始下载
   * @param model
   */
  public void startDownload( DownloadInfo model) {
    BaseDownloadTask task = FileDownloader.getImpl().create(model.getDownloadUrl())
            .setPath(model.getFileSavePath())
            .setAutoRetryTimes(5)
            .setCallbackProgressTimes(100);
    addTaskForViewHolder(task);
    task.start();
  }

  /******************************* FileDownloader method *************************************/

  /**
   * 通过url 和保存地址，判断这个task的状态
   * @return
   */
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

  /**
   * 添加任务
   * @param url
   * @param path
   * @return
   */
  public DownloadInfo addTask(final String url, final String path) {
    if (TextUtils.isEmpty(url) || TextUtils.isEmpty(path)) {
      return null;
    }

    //获取downloadId
    final int id = FileDownloadUtils.generateId(url, path);
    DownloadInfo model = getById(id);
    //如果已经在下载列表 就不要下载了
    if (model != null) {
      ArmsUtils.makeText("已经存在相同的");
      return null;
    }

    //添加一条数据到数据库，默认值存在一个
    final DownloadInfo newModel = dbController.addTask(url, path);
    //如果不在，就加入到列表下载了
    if (newModel != null) {
      modelList.add(newModel);
    }

    return newModel;
  }


  /********************** private method start *********************************/

  private void registerServiceConnectionListener( ) {
    if (listener != null) {
      FileDownloader.getImpl().removeServiceConnectListener(listener);
    }

    listener = new FileDownloadConnectListener() {

      @Override
      public void connected() {
        //刷新
        EventBus.getDefault().post("refresh","refresh");
      }

      @Override
      public void disconnected() {
        Timber.i("disconnected");
      }
    };
    FileDownloader.getImpl().addServiceConnectListener(listener);
  }

  private void unregisterServiceConnectionListener() {
    FileDownloader.getImpl().removeServiceConnectListener(listener);
    listener = null;
  }

  private String createPath(final String url) {
    if (TextUtils.isEmpty(url)) {
      return null;
    }
    return FileDownloadUtils.getDefaultSaveFilePath(url);
  }




}