package me.shetj.share;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;

import me.shetj.share.shareinfo.ShareImageObject;
import me.shetj.share.shareinfo.ShareListener;
import me.shetj.share.shareinfo.SharePlatform;
import me.shetj.share.shareinfo.instance.ShareInstance;
import me.shetj.share.shareinfo.instance.WeiboShareInstance;
import me.shetj.share.shareinfo.instance.WxShareInstance;
import me.shetj.share.wb.Constants;


/**
 * Created by shaohui on 2016/11/18.
 * update by shetj
 */

public class ShareUtil {
    /**
     * 测试case
     *
     * 1. 本地图片 vs 网络图片
     * 2. 图片大小限制
     * 3. 文字长度限制
     */

    public static final int TYPE = 798;

    public static ShareListener mShareListener;

    private static ShareInstance mShareInstance;

    private final static int TYPE_IMAGE = 1;
    private final static int TYPE_TEXT = 2;
    private final static int TYPE_MEDIA = 3;

    private static int mType;
    private static int mPlatform;
    private static String mText;
    private static ShareImageObject mShareImageObject;
    private static String mTitle;
    private static String mSummary;
    private static String mTargetUrl;

    public static void init(Application application, String wx, String wb) {
        ShareConfig config = ShareConfig.instance()
                .wxId(wx)
                .weiboId(wb);
        init(application,config);
    }


    public static void init(Application app, ShareConfig config) {
        ShareManager.init(config);
        WbSdk.install(app,new AuthInfo(app, Constants.APP_KEY,Constants.REDIRECT_URL, Constants.SCOPE));//创建微博API接口类对象

    }

    static void action(Activity activity) {
        mShareInstance = getShareInstance(mPlatform, activity);

        // 防止之后调用 NullPointException
        if (mShareListener == null) {
            activity.finish();
            return;
        }

        if (!mShareInstance.isInstall(activity)) {
            mShareListener.shareFailure();
            activity.finish();
            return;
        }

        switch (mType) {
            case TYPE_TEXT:
                mShareInstance.shareText(mPlatform, mText, activity, mShareListener);
                break;
            case TYPE_IMAGE:
                mShareInstance.shareImage(mPlatform, mShareImageObject, activity, mShareListener);
                break;
            case TYPE_MEDIA:
                mShareInstance.shareMedia(mPlatform, mTitle, mTargetUrl, mSummary,
                        mShareImageObject, activity, mShareListener);
                break;
        }
    }

    public static void shareText(Context context, @SharePlatform.Platform int platform, String text,
                                 ShareListener listener) {
        mType = TYPE_TEXT;
        mText = text;
        mPlatform = platform;
        mShareListener = buildProxyListener(listener);

        context.startActivity(_ShareActivity.newInstance(context, TYPE));
    }

    public static void shareImage(Context context, @SharePlatform.Platform final int platform,
            final String urlOrPath, ShareListener listener) {
        mType = TYPE_IMAGE;
        mPlatform = platform;
        mShareImageObject = new ShareImageObject(urlOrPath);
        mShareListener = buildProxyListener(listener);

        context.startActivity(_ShareActivity.newInstance(context, TYPE));
    }

    public static void shareImage(Context context, @SharePlatform.Platform final int platform,
            final Bitmap bitmap, ShareListener listener) {
        mType = TYPE_IMAGE;
        mPlatform = platform;
        mShareImageObject = new ShareImageObject(bitmap);
        mShareListener = buildProxyListener(listener);

        context.startActivity(_ShareActivity.newInstance(context, TYPE));
    }

    public static void shareMedia(Context context, @SharePlatform.Platform int platform,
            String title, String summary, String targetUrl, Bitmap thumb, ShareListener listener) {
        mType = TYPE_MEDIA;
        mPlatform = platform;
        mShareImageObject = new ShareImageObject(thumb);
        mSummary = summary;
        mTargetUrl = targetUrl;
        mTitle = title;
        mShareListener = buildProxyListener(listener);

        context.startActivity(_ShareActivity.newInstance(context, TYPE));
    }

    public static void shareMedia(Context context, @SharePlatform.Platform int platform,
            String title, String summary, String targetUrl, String thumbUrlOrPath,
            ShareListener listener) {
        mType = TYPE_MEDIA;
        mPlatform = platform;
        mShareImageObject = new ShareImageObject(thumbUrlOrPath);
        mSummary = summary;
        mTargetUrl = targetUrl;
        mTitle = title;
        mShareListener = buildProxyListener(listener);

        context.startActivity(_ShareActivity.newInstance(context, TYPE));
    }

    private static ShareListener buildProxyListener(ShareListener listener) {
        return new ShareListenerProxy(listener);
    }

    public static void handleResult(Intent data) {
        // 微博分享会同时回调onActivityResult和onNewIntent， 而且前者返回的intent为null
        if (mShareInstance != null && data != null) {
            mShareInstance.handleResult(data);
        } else if (data == null) {
            if (mPlatform != SharePlatform.WEIBO) {
                ShareLogger.e(ShareLogger.INFO.HANDLE_DATA_NULL);
            }
        } else {
            ShareLogger.e(ShareLogger.INFO.UNKNOWN_ERROR);
        }

    }

    private static ShareInstance getShareInstance(@SharePlatform.Platform int platform,
            Context context) {
        switch (platform) {
            case SharePlatform.WX:
            case SharePlatform.WX_TIMELINE:
                return new WxShareInstance(context, ShareManager.CONFIG.getWxId());
            case SharePlatform.WEIBO:
                return new WeiboShareInstance(context);
        }
        return null;
    }

    public static void recycle() {
        mTitle = null;
        mSummary = null;
        mShareListener = null;

        // bitmap recycle
        if (mShareImageObject != null
                && mShareImageObject.getBitmap() != null
                && !mShareImageObject.getBitmap().isRecycled()) {
            mShareImageObject.getBitmap().recycle();
        }
        mShareImageObject = null;

        if (mShareInstance != null) {
            mShareInstance.recycle();
        }
        mShareInstance = null;
    }



    private static class ShareListenerProxy extends ShareListener {

        private final ShareListener mShareListener;

        ShareListenerProxy(ShareListener listener) {
            mShareListener = listener;
        }

        @Override
        public void shareSuccess() {
            ShareUtil.recycle();
            mShareListener.shareSuccess();
        }

        @Override
        public void shareFailure() {

        }


        @Override
        public void shareCancel() {
            ShareUtil.recycle();
            mShareListener.shareCancel();
        }

        @Override
        public void shareRequest() {
            mShareListener.shareRequest();
        }
    }
}
