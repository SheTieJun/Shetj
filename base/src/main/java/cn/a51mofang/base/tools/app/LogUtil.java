package cn.a51mofang.base.tools.app;

import android.util.Log;

/**
 * The type Log util.
 */
public class LogUtil {
    /**
     * The constant isShow.
     */
    public static boolean isShow = false;

    /**
     * Show.
     *
     * @param tag the tag
     * @param msg the msg
     */
    public static void show(String tag,String msg){
        if (isShow) {
            Log.i(tag, msg);
        }
    }

}
