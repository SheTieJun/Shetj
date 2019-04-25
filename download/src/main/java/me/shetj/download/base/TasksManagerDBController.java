package me.shetj.download.base;

import com.liulishuo.filedownloader.util.FileDownloadUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.shetj.base.tools.app.ArmsUtils;
import me.shetj.simxutils.DbManager;
import me.shetj.simxutils.DbUtils;
import me.shetj.simxutils.ex.DbException;

public  class TasksManagerDBController {

    private final DbManager db;

    public TasksManagerDBController() {
        db = DbUtils.getDbManager("download",3);
    }

    public List<DownloadInfo> getAllTasks() {
        try {
            List<DownloadInfo> all = db.selector(DownloadInfo.class).findAll();
            if (all!=null) {
                return all;
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public DownloadInfo addTask(final String url, final String path) {

        String fileSavePath = new File(path).getAbsolutePath();
        final int id = FileDownloadUtils.generateId(url, path);
        DownloadInfo downloadInfo = null;
        try {
            downloadInfo = db.selector(DownloadInfo.class)
                    .where("downloadUrl", "=", url)
                    .where("fileSavePath", "=", fileSavePath)
                    .findFirst();
            if (downloadInfo == null) {
                downloadInfo = new DownloadInfo();
                downloadInfo.setId(id);
                downloadInfo.setLabel(fileSavePath);
                downloadInfo.setDownloadUrl(url);
                downloadInfo.setFileSavePath(fileSavePath);
                db.save(downloadInfo);
                return  downloadInfo;
            }else {
                ArmsUtils.makeText("已经存在相同的");
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return  null;
    }


}