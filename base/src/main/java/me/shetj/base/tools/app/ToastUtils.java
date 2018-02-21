package me.shetj.base.tools.app;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by weedys on 2017/2/25.
 */
public class ToastUtils {
    /**
     * Show.
     *
     * @param c   the c
     * @param msg the msg
     */
    public static void show(Context c,String msg){
        Toast.makeText(c,msg,Toast.LENGTH_SHORT).show();
    }

    /**
     * Long show.
     *
     * @param c   the c
     * @param msg the msg
     */
    public static void longShow(Context c,String msg){
        Toast.makeText(c,msg,Toast.LENGTH_LONG).show();
    }

    /**
     * Short show.
     *
     * @param c   the c
     * @param msg the msg
     */
    public static void shortShow(Context c,String msg){
        Toast.makeText(c,msg,Toast.LENGTH_SHORT).show();
    }
}
