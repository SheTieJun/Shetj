package me.shetj.base.base;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.simple.eventbus.EventBus;

import me.shetj.base.R;
import me.shetj.base.tools.app.ArmsUtils;
import me.shetj.base.tools.app.HideUtil;
import me.shetj.base.view.LoadingDialog;

import static me.shetj.base.tools.app.ThirdViewUtil.convertAutoView;

@Keep
public abstract class BaseSwipeActivity extends BaseSwipeBackActivity implements IView {

    private Dialog dialog;
    private SystemBarTintManager mTintManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (useEventBus())//如果要使用eventbus请将此方法返回true
            EventBus.getDefault().register(this);//注册到事件主线
        HideUtil.init(this);
        startAnimation();
        setSwipeBackEnable(false);
    }



    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = convertAutoView(name, context, attrs);
        return view == null ? super.onCreateView(name, context, attrs) : view;
    }

    /**
     * 全屏
     */
    public void fullScreencall() {
        if (Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            if (Build.VERSION.SDK_INT >= 21) {
                getWindow().setStatusBarColor(Color.TRANSPARENT);
                getWindow().setNavigationBarColor(Color.TRANSPARENT);
            }
        }
    }

    /**
     * 设置沉浸式   颜色
     */
    public void setStatusBarState(int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        mTintManager = new SystemBarTintManager(this);
        mTintManager.setStatusBarTintEnabled(true);
        // 使StatusBarTintView 和 actionbar的颜色保持一致，风格统一。
        mTintManager.setStatusBarTintResource(colorId);
        // 设置状态栏的文字颜色
        //  mTintManager.setStatusBarDarkMode(false, this);
        mTintManager.setNavigationBarTintColor(colorId);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    protected void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * 针对6.0动态请求权限问题
     * 判断是否允许此权限
     *
     * @param permissions  权限
     * @return hasPermission
     */
    protected boolean hasPermission(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    /**
     * 是否使用eventBus,默认为使用(true)，
     *
     * @return useEventBus
     */
    protected boolean useEventBus() {
        return true;
    }



    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    protected void onDestroy() {
        if (useEventBus())//如果要使用eventbus请将此方法返回true
            EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void showLoading(String msg) {
        hideLoading();
        dialog = LoadingDialog.showLoading(this, msg, true);
    }


    /**
     * 界面开始动画 (此处输入方法执行任务.)
     */
    protected void startAnimation() {
        overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
    }

    /**
     * 界面回退动画 (此处输入方法执行任务.)
     */
    protected void endAnimation() {// 开始动画
        overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);
    }




    @Override
    public void finish() {// 设置回退动画
        super.finish();
    }

    /**
     * 返回
     */
    public void back() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            finish();
        }
    }

    @Override
    public void scrollToFinishActivity() {
        super.scrollToFinishActivity();
    }


    @Override
    public void hideLoading() {
        if (dialog != null){
            dialog.cancel();
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
        ArmsUtils.shortSnackbar(this,message);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        endAnimation();
        back();
    }

    @Override
    public RxAppCompatActivity getRxContext() {
        return this;
    }


}
