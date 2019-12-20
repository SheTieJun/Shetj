package com.shetj.diyalbume.playVideo.video;


import android.text.TextUtils;

public class SwitchVideoModel {
    private String url;
    private String name;

    public SwitchVideoModel(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        if (TextUtils.isEmpty(name)){
            if (url.contains("f20")){
                return "流畅";
            }
            if (url.contains("f40")){
                return "高清";
            }
            if (url.contains("f30")){
                return "标清";
            }
            return "原画";
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}