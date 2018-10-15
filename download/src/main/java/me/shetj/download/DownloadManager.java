package me.shetj.download;


import java.io.File;

import me.shetj.simxutils.xutils.DbManager;
import me.shetj.simxutils.xutils.DbUtils;
import me.shetj.simxutils.xutils.ex.DbException;

/**
 */
public final class DownloadManager {


    private static volatile DownloadManager instance;


    private final DbManager db;

    private DownloadManager() {
        db = DbUtils.getDbManager("download",1);
    }

    public static DownloadManager getInstance() {
        if (instance == null) {
            synchronized (DownloadManager.class) {
                if (instance == null) {
                    instance = new DownloadManager();
                }
            }
        }
        return instance;
    }

    public void updateDownloadInfo(DownloadInfo info) throws DbException {
        db.update(info);
    }

    public int getDownloadListCount() {
        return 0;
    }

    public DownloadInfo getDownloadInfo(int index) {
        return null;
    }

    public synchronized void startDownload(String url, String label, String savePath,
                                           boolean autoResume, boolean autoRename) throws DbException {

        String fileSavePath = new File(savePath).getAbsolutePath();
        DownloadInfo downloadInfo = db.selector(DownloadInfo.class)
                .where("label", "=", label)
                .and("fileSavePath", "=", fileSavePath)
                .findFirst();

        // create download info
        if (downloadInfo == null) {
            downloadInfo = new DownloadInfo();
            downloadInfo.setUrl(url);
            downloadInfo.setAutoRename(autoRename);
            downloadInfo.setAutoResume(autoResume);
            downloadInfo.setLabel(label);
            downloadInfo.setFileSavePath(fileSavePath);
            db.saveBindingId(downloadInfo);
        }

    }

    public void stopDownload(int index) {
    }

    public void stopDownload(DownloadInfo downloadInfo) {
    }

    public void stopAllDownload() {
    }

    public void removeDownload(int index) throws DbException {
    }

    public void removeDownload(DownloadInfo downloadInfo) throws DbException {
    }
}
