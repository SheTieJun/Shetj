package me.shetj.share.shareinfo.instance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import me.shetj.share.ShareUtil;
import me.shetj.share.shareinfo.ShareImageObject;
import me.shetj.share.shareinfo.ShareListener;
import me.shetj.share.shareinfo.SharePlatform;
import me.shetj.share.utils.ImageUtils;

/**
 * Created by shaohui on 2016/11/18.
 */

public class WxShareInstance implements ShareInstance {

    /**
     * 微信分享限制thumb image必须小于32Kb，否则点击分享会没有反应
     */

    private IWXAPI mIWXAPI;

    private static final long THUMB_SIZE = 32 * 1024 * 8;

    private static final int TARGET_SIZE = 200;

    public WxShareInstance(Context context, String appId) {
        mIWXAPI = WXAPIFactory.createWXAPI(context, appId, true);
        mIWXAPI.registerApp(appId);
    }

    @Override
    public void shareText(int platform, String text, Activity activity, ShareListener listener) {
        WXTextObject textObject = new WXTextObject();
        textObject.text = text;

        WXMediaMessage message = new WXMediaMessage();
        message.mediaObject = textObject;
        message.description = text;

        sendMessage(platform, message, buildTransaction("text"));
    }


    @Override
    public void shareMedia(
            final int platform, final String title, final String targetUrl, final String description,
            final ShareImageObject shareImageObject, final Activity activity, final ShareListener listener) {
        WXWebpageObject webpageObject = new WXWebpageObject();
        webpageObject.webpageUrl = targetUrl;

        WXMediaMessage message = new WXMediaMessage(webpageObject);
        message.title = title;
        message.description = description;
        Bitmap thumbBitmap = Bitmap.createScaledBitmap(shareImageObject.getBitmap(), 150, 150, true);
        message.thumbData= ImageUtils.bitmap2Bytes(thumbBitmap, Bitmap.CompressFormat.JPEG);
        Log.d("thumbData","thumbData = "+message.thumbData.length+"");
        sendMessage(platform, message, buildTransaction("webPage"));
    }

    @Override
    public void shareImage(final int platform, final ShareImageObject shareImageObject,
            final Activity activity, final ShareListener listener) {

        WXImageObject imageObject = new WXImageObject(shareImageObject.getBitmap());

        WXMediaMessage message = new WXMediaMessage();
        message.mediaObject = imageObject;
        Bitmap thumbBitmap = Bitmap.createScaledBitmap(shareImageObject.getBitmap(), 150, 150, true);
        message.thumbData= ImageUtils.bitmap2Bytes(thumbBitmap, Bitmap.CompressFormat.JPEG);
        Log.d("thumbData","thumbData = "+message.thumbData.length+"");
        sendMessage(platform, message, buildTransaction("image"));
    }


    @Override
    public void handleResult(Intent data) {
        mIWXAPI.handleIntent(data, new IWXAPIEventHandler() {
            @Override
            public void onReq(BaseReq baseReq) {
                ShareUtil.mShareListener.shareRequest();
            }

            @Override
            public void onResp(BaseResp baseResp) {
                switch (baseResp.errCode) {
                    case BaseResp.ErrCode.ERR_OK:
                        ShareUtil.mShareListener.shareSuccess();
                        break;
                    case BaseResp.ErrCode.ERR_USER_CANCEL:
                        ShareUtil.mShareListener.shareCancel();
                        break;
                    default:
                        ShareUtil.mShareListener.shareFailure();
                }
            }
        });
    }

    @Override
    public boolean isInstall(Context context) {
        return mIWXAPI.isWXAppInstalled();
    }

    @Override
    public void recycle() {
        mIWXAPI.detach();
    }

    private void sendMessage(int platform, WXMediaMessage message, String transaction) {
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = transaction;
        req.message = message;
        req.scene = platform == SharePlatform.WX_TIMELINE ? SendMessageToWX.Req.WXSceneTimeline
                : SendMessageToWX.Req.WXSceneSession;
        mIWXAPI.sendReq(req);
    }

    private String buildTransaction(String type) {
        return System.currentTimeMillis() + type;
    }

}
