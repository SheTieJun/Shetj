package cn.shetj.base.tools.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;

import java.security.MessageDigest;

/**
 * Created by jess on 2015/11/23.
 */
public class UiUtils {

    /**
    * 当你需要当前界面中的某个元素和新界面中的元素有关时，你可以使用这个动画。效果很赞~！
    */
    public static ActivityOptionsCompat getActivityOptions(Activity activity, View sharedCardView,String  TRANSITION_NAME_CARD) {
        return ActivityOptionsCompat
                .makeSceneTransitionAnimation(activity, sharedCardView, TRANSITION_NAME_CARD);
    }

    /**
     *让新的Activity从一个小的范围扩大到全屏
     */
    public static ActivityOptionsCompat getActivityOptions(View view) {
        return ActivityOptionsCompat.makeScaleUpAnimation(view, //The View that the new activity is animating from
                (int)view.getWidth()/2, (int)view.getHeight()/2, //拉伸开始的坐标
                0, 0);//拉伸开始的区域大小，这里用（0，0）表示从无到全屏
    }

    /**
     *多个元素和新的Activity相关的情况，注意下第二个参数Pair这个键值对后面有...，标明是可以传入多个Pair对象的
     */
    public static ActivityOptionsCompat getActivityOptions(Activity activity,Pair<View, String>... arg1) {
        return  ActivityOptionsCompat.makeSceneTransitionAnimation(activity, arg1);
    }

    public static void startNewAcitivity(Activity activity,ActivityOptionsCompat options,Class ActivityClass) {
        Intent intent = new Intent(activity,ActivityClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }



    /**
     * 移除孩子
     *
     * @param view
     */
    public static void removeChild(View view) {
        ViewParent parent = view.getParent();
        if (parent instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) parent;
            group.removeView(view);
        }
    }


    /**
     * MD5
     *
     * @param string
     * @return
     * @throws Exception
     */
    public static String MD5encode(String string) {
        byte[] hash = new byte[0];
        try {
            hash = MessageDigest.getInstance("MD5").digest(
                    string.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }


    /**
     * 全屏，并且沉侵式状态栏
     *
     * @param activity
     */
    public static void statuInScreen(Activity activity) {
        WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        activity.getWindow().setAttributes(attrs);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }


    /**
     * 全屏
     */
    public static void fullScreencall(Activity activity) {
        if (Build.VERSION.SDK_INT < 19) { // lower api
            View v = activity.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            View decorView = activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            if (Build.VERSION.SDK_INT >= 21) {
                activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
                activity.getWindow().setNavigationBarColor(Color.TRANSPARENT);
            }
        }
    }

    /**
     * 设置透明状态栏与导航栏
     *
     * @param navi true不设置导航栏|false设置导航栏
     */
    public static void setStatusBar(Activity activity,boolean navi) {
        //api>21,全透明状态栏和导航栏;api>19,半透明状态栏和导航栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            if (navi) {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN//状态栏不会被隐藏但activity布局会扩展到状态栏所在位置
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION//导航栏不会被隐藏但activity布局会扩展到导航栏所在位置
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_FULLSCREEN
                );
                window.setNavigationBarColor(Color.TRANSPARENT);
            } else {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (navi) {
                //半透明导航栏
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            }
            //半透明状态栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 配置recycleview
     *
     * @param recyclerView
     * @param layoutManager
     */
    public static void configRecycleView(final RecyclerView recyclerView
            , RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

}
