package me.shetj.download.base;


import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.liulishuo.filedownloader.model.FileDownloadStatus;

import me.shetj.simxutils.db.annotation.Column;
import me.shetj.simxutils.db.annotation.Table;

@Table(name = "download", onCreated = "CREATE UNIQUE INDEX index_name ON download(downloadUrl,fileSavePath)")
public class DownloadInfo implements MultiItemEntity {

    public DownloadInfo() {
    }

    @Column(name = "id", isId = true)
    private int id;

    @Column(name = "state")
    private int state = FileDownloadStatus.paused;//状态，目前有fileDownloader 控制

    @Column(name = "url")
    private String url;//图片

    @Column(name = "downloadUrl")
    private String downloadUrl;//下载地址

    @Column(name = "downloadId")
    private int downloadId;//下载的ID

    @Column(name = "soFarBytes")
    private long soFarBytes;//下载长度

    @Column(name = "totalBytes")
    private long totalBytes;//总长度

    @Column(name = "progress")
    private int progress;//进度

    @Column(name = "label")
    private String label;//名称

    @Column(name = "fileSavePath")
    private String fileSavePath;//保存地址

    @Column(name = "lectureId")
    private String lectureId;//课程id

    @Column(name = "channelId")
    private String channelId;//专栏id

    @Column(name = "liveRoomId")
    private String liveRoomId;//直播间

    @Column(name = "rawJson")
    private String rawJson;//课程内容Json

    public String getRawJson() {
        return rawJson;
    }

    public void setRawJson(String rawJson) {
        this.rawJson = rawJson;
    }

    public String getLiveRoomId() {
        return liveRoomId;
    }

    public void setLiveRoomId(String liveRoomId) {
        this.liveRoomId = liveRoomId;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getLectureId() {
        return lectureId;
    }

    public void setLectureId(String lectureId) {
        this.lectureId = lectureId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

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

    public long getSoFarBytes() {
        return soFarBytes;
    }

    public void setSoFarBytes(long soFarBytes) {
        this.soFarBytes = soFarBytes;
    }

    public long getTotalBytes() {
        return totalBytes;
    }

    public void setTotalBytes(long totalBytes) {
        this.totalBytes = totalBytes;
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
