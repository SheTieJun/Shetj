package me.shetj.share.shareinfo;


/**
 * Created by shaohui on 2016/11/18.
 */

public abstract class ShareListener {


    public abstract void shareSuccess();

    public abstract void shareFailure();

    public abstract void shareCancel();

    // 用于缓解用户焦虑
    public void shareRequest() {
    }
}
