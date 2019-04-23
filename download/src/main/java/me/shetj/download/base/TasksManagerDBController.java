package me.shetj.download.base;

import java.util.ArrayList;
import java.util.List;

public  class TasksManagerDBController {

    public TasksManagerDBController() {
    }

    public List<DownloadInfo> getAllTasks() {

        return new ArrayList<>();
    }

    public DownloadInfo addTask(final String url, final String path) {
        return  new DownloadInfo();
    }


}