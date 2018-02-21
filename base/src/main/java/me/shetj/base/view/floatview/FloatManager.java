package me.shetj.base.view.floatview;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;



/**
 * 悬浮球管理类
 */
public class FloatManager {
    private WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams();
    private FloatView floatView = null;
    private boolean isDisplay = false;
    private Activity activity;
    private static FloatManager instance = null;

    public static FloatManager getFloatManager(Activity activity) {
        if (instance == null) {
            instance = new FloatManager(activity);
        }
        return instance;
    }

    private FloatManager(Activity activity) {
        this.activity = activity;
    }

    public WindowManager.LayoutParams getWindowParams() {
        return windowParams;
    }


    public void createFloatWindow() {
        if (isDisplay){
            return;
        }
        if (floatView != null){
            return;
        }
        floatView = new FloatView(activity, windowParams);
        floatView.setOnClickListener(floatViewClick);
        WindowManager windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(floatView, windowParams);
        isDisplay = true;
    }

    private OnClickListener floatViewClick = new OnClickListener() {

        public void onClick(View v) {
        }
    };


    /**
     * 程序进入后台或者退出事调用
     */
    public void destroyFloat() {
        if (!isDisplay)
            return;
        if (floatView != null) {
            floatView.cancelTimerCount();
        }
        WindowManager windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        if (floatView!= null) {
            windowManager.removeView(floatView);
        }
        windowManager = null;
        floatView = null;
        floatViewClick = null;
        isDisplay = false;
        instance = null;
    }


}
