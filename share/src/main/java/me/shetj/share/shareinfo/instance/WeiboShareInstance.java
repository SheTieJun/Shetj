package me.shetj.share.shareinfo.instance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;

import me.shetj.share.ShareUtil;
import me.shetj.share.shareinfo.ShareImageObject;
import me.shetj.share.shareinfo.ShareListener;

/**
 */

public class WeiboShareInstance implements ShareInstance ,WbShareCallback{

    private static final int TARGET_SIZE = 1024;

    private static final int TARGET_LENGTH = 2097152;
    private WbShareHandler shareHandler ;

    public WeiboShareInstance(Context context) {
        shareHandler =new  WbShareHandler((Activity) context);
        shareHandler.registerApp();
    }

    @Override
    public void shareText(int platform, String text, Activity activity, ShareListener listener) {
        TextObject textObject = new TextObject();
        textObject.text = text;
        WeiboMultiMessage message = new WeiboMultiMessage();
        message.textObject = textObject;

        sendRequest(activity, message);
    }

    @Override
    public void shareMedia(int platform, final String title, final String targetUrl, String summary,
            ShareImageObject shareImageObject, final Activity activity,
            final ShareListener listener) {
        String content = String.format("%s %s", title, targetUrl);
        ImageObject imageObject = new ImageObject();
        imageObject.imagePath = shareImageObject.getPathOrUrl();

        WeiboMultiMessage message = new WeiboMultiMessage();
        message.imageObject = imageObject;
        if (!TextUtils.isEmpty(title)) {
            TextObject textObject = new TextObject();
            textObject.text = title;
            message.textObject = textObject;
        }

        sendRequest(activity, message);

    }

    @Override
    public void shareImage(int platform, ShareImageObject shareImageObject, Activity activity,
            ShareListener listener) {
        ImageObject imageObject = new ImageObject();
        imageObject.imagePath = shareImageObject.getPathOrUrl();

        WeiboMultiMessage message = new WeiboMultiMessage();
        message.imageObject = imageObject;
        sendRequest(activity, message);
    }


    @Override
    public void handleResult(Intent intent) {
        shareHandler.doResultIntent(intent, this);

    }

    @Override
    public boolean isInstall(Context context) {
        return shareHandler.isWbAppInstalled();
    }

    @Override
    public void recycle() {
        shareHandler = null;
    }


    private void sendRequest(Activity activity, WeiboMultiMessage message) {
        ShareUtil.mShareListener.shareRequest();
        shareHandler.shareMessage(message, false);
    }

    @Override
    public void onWbShareSuccess() {
        ShareUtil.mShareListener.shareSuccess();
    }

    @Override
    public void onWbShareCancel() {
        ShareUtil.mShareListener.shareCancel();
    }

    @Override
    public void onWbShareFail() {
        ShareUtil.mShareListener.shareFailure();
    }
}
