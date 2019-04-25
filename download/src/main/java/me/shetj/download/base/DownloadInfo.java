package me.shetj.download.base;


import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.liulishuo.filedownloader.model.FileDownloadStatus;

import me.shetj.simxutils.db.annotation.Column;
import me.shetj.simxutils.db.annotation.Table;

/**
 * Author: wyouflf
 * Date: 13-11-10
 * Time: 下午8:11
 */
@Table(name = "download", onCreated = "CREATE UNIQUE INDEX index_name ON download(label,fileSavePath)")
public class DownloadInfo implements MultiItemEntity {

    public DownloadInfo() {
    }

    @Column(name = "id", isId = true)
    private int id;

    @Column(name = "state")
    private int state = FileDownloadStatus.paused;

    @Column(name = "url")
    private String url;

    @Column(name = "downloadUrl")
    private String downloadUrl;


    @Column(name = "downloadId")
    private int downloadId;


    @Column(name = "label")
    private String label;

    @Column(name = "fileSavePath")
    private String fileSavePath;

    public int getDownloadId() {
        return downloadId;
    }

    public void setDownloadId(int downloadId) {
        this.downloadId = downloadId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getFileSavePath() {
        return fileSavePath;
    }

    public void setFileSavePath(String fileSavePath) {
        this.fileSavePath = fileSavePath;
    }


    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DownloadInfo)) return false;

        DownloadInfo that = (DownloadInfo) o;

        if (!downloadUrl.equals(that.downloadUrl)) return false;

        return true;
    }



    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public int getItemType() {
        return 0;
    }
}
