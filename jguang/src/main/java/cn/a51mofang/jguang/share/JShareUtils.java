package cn.a51mofang.jguang.share;

import android.app.Activity;
import android.app.Application;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import cn.a51mofang.jguang.share.weight.ShareBoard;
import cn.a51mofang.jguang.share.weight.ShareBoardlistener;
import cn.a51mofang.jguang.share.weight.SnsPlatform;
import cn.jiguang.share.android.api.JShareInterface;
import cn.jiguang.share.android.api.PlatActionListener;
import cn.jiguang.share.android.api.Platform;
import cn.jiguang.share.android.api.ShareParams;
import cn.jiguang.share.qqmodel.QQ;
import cn.jiguang.share.qqmodel.QZone;
import cn.jiguang.share.wechat.Wechat;
import cn.jiguang.share.wechat.WechatFavorite;
import cn.jiguang.share.wechat.WechatMoments;
import cn.jiguang.share.weibo.SinaWeibo;
import cn.jiguang.share.weibo.SinaWeiboMessage;

/**
 *
 * Created by admin on 2017/8/15.
 */

public class JShareUtils {
    public static void init(Application app, boolean logDebug) {
        JShareInterface.setDebugMode(logDebug);
        JShareInterface.init(app);
    }

    //    private static String ImagePath;
    private static Activity mActivity;
    public static void showBroadView(final Activity activity, final String title, final String url, final String text) {
        mActivity = activity;
        ShareBoard mShareBoard = new ShareBoard(activity);
        List<String> platforms = JShareInterface.getPlatformList();
        if (platforms != null) {
            Iterator var2 = platforms.iterator();
            while (var2.hasNext()) {
                String temp = (String) var2.next();
                SnsPlatform snsPlatform = createSnsPlatform(temp);
                mShareBoard.addPlatform(snsPlatform);
            }
        }
        mShareBoard.setShareboardclickCallback( new ShareBoardlistener() {
            @Override
            public void onclick(SnsPlatform snsPlatform, String platform) {
                //这里以分享链接为例
                ShareParams shareParams = new ShareParams();
                shareParams.setShareType(Platform.SHARE_WEBPAGE);
                shareParams.setTitle( title);
                shareParams.setText( text);
                shareParams.setShareType(Platform.SHARE_WEBPAGE);
                shareParams.setUrl( url);
//                    shareParams.setImagePath( ImagePath);
                JShareInterface.share(platform, shareParams, mShareListener);
            }
        });
        mShareBoard.show();
    }

    public static void showBroadViewPic(final Activity activity, final String url) {
        mActivity = activity;
        ShareBoard mShareBoard = new ShareBoard(activity);
        List<String> platforms = JShareInterface.getPlatformList();
        if (platforms != null) {
            Iterator var2 = platforms.iterator();
            while (var2.hasNext()) {
                String temp = (String) var2.next();
                SnsPlatform snsPlatform = createSnsPlatform(temp);
                mShareBoard.addPlatform(snsPlatform);
            }
        }
        mShareBoard.setShareboardclickCallback( new ShareBoardlistener() {
            @Override
            public void onclick(SnsPlatform snsPlatform, String platform) {
                ShareParams shareParams = new ShareParams();
                shareParams.setShareType(Platform.SHARE_IMAGE);
                shareParams.setImagePath(url);
                JShareInterface.share(platform, shareParams, mShareListener);
            }
        });
        mShareBoard.show();
    }


    private static PlatActionListener mShareListener = new PlatActionListener() {
        @Override
        public void onComplete(Platform platform, int action, HashMap<String, Object> data) {
            Toast.makeText(mActivity,"分享成功！",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(Platform platform, int action, int errorCode, Throwable error) {
            Toast.makeText(mActivity,"分享失败！",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(Platform platform, int action) {
            Toast.makeText(mActivity,"分享取消！",Toast.LENGTH_SHORT).show();
        }
    };



    private static SnsPlatform createSnsPlatform(String platformName) {
        String mShowWord = platformName;
        String mIcon = "";
        String mGrayIcon = "";
        String mKeyword = platformName;
        if (platformName.equals(Wechat.Name)) {
            mIcon = "jiguang_socialize_wechat";
            mGrayIcon = "jiguang_socialize_wechat";
            mShowWord = "jiguang_socialize_text_weixin_key";
        } else if (platformName.equals(WechatMoments.Name)) {
            mIcon = "jiguang_socialize_wxcircle";
            mGrayIcon = "jiguang_socialize_wxcircle";
            mShowWord = "jiguang_socialize_text_weixin_circle_key";

        } else if (platformName.equals(WechatFavorite.Name)) {
            mIcon = "jiguang_socialize_wxfavorite";
            mGrayIcon = "jiguang_socialize_wxfavorite";
            mShowWord = "jiguang_socialize_text_weixin_favorite_key";

        } else if (platformName.equals(SinaWeibo.Name)) {
            mIcon = "jiguang_socialize_sina";
            mGrayIcon = "jiguang_socialize_sina";
            mShowWord = "jiguang_socialize_text_sina_key";
        } else if (platformName.equals(SinaWeiboMessage.Name)) {
            mIcon = "jiguang_socialize_sina";
            mGrayIcon = "jiguang_socialize_sina";
            mShowWord = "jiguang_socialize_text_sina_msg_key";
        } else if (platformName.equals(QQ.Name)) {
            mIcon = "jiguang_socialize_qq";
            mGrayIcon = "jiguang_socialize_qq";
            mShowWord = "jiguang_socialize_text_qq_key";

        } else if (platformName.equals(QZone.Name)) {
            mIcon = "jiguang_socialize_qzone";
            mGrayIcon = "jiguang_socialize_qzone";
            mShowWord = "jiguang_socialize_text_qq_zone_key";
        }
        return ShareBoard.createSnsPlatform(mShowWord, mKeyword, mIcon, mGrayIcon, 0);
    }


}
