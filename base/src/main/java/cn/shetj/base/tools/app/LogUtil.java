package cn.shetj.base.tools.app;

import android.util.Log;

/**
 * The type Log util.
 */
public class LogUtil {
    /**
     * The constant isShow.
     */
    public static boolean isShow = true;

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
    /**
     * Show.
     *
     * @param msg the msg
     */
    public static void show(String msg){
        if (isShow) {
            Log.i("diy", msg);
        }
    }
}
