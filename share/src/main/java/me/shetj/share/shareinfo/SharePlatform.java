package me.shetj.share.shareinfo;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by shaohui on 2016/11/18.
 */

public class SharePlatform {

    @IntDef({WEIBO, WX, WX_TIMELINE })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Platform{}

    public static final int WX = 3;
    public static final int WX_TIMELINE = 4;
    public static final int WEIBO = 5;


}
