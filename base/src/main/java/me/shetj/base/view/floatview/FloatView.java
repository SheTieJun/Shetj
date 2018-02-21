package me.shetj.base.view.floatview;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

import me.shetj.base.tools.app.ResourceUtils;


public class FloatView extends ImageView {
    private float mTouchX;
    private float mTouchY;
    private float x;
    private float y;
    private OnClickListener mClickListener;

    private WindowManager windowManager = (WindowManager) getContext()
            .getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
    // 此windowManagerParams变量为获取的全局变量，用以保存悬浮窗口的属性
    private LayoutParams windowManagerParams;

    //保存当前是否为移动模式
    private boolean  isMove = false;
    //保存当前悬浮球在左边还是右边
    private boolean  isRight = false;


    private FloatViewPreferManager mPreferenceManager = null;

    //是否触摸悬浮窗
    private boolean isTouch = false;
    private boolean isCanMove = false;
    private Timer timer;
    //定时器取消
    private boolean isCancel;
    private TimerTask timerTask;

    private static final int HIDE = 1;


    public FloatView(Activity context, LayoutParams windowManagerParams) {
        super(context);
        isMove = false;
        isRight = false;
        this.windowManagerParams = windowManagerParams;
        mPreferenceManager = new FloatViewPreferManager(getContext());
        windowManagerParams.type = LayoutParams.FLAG_NOT_FOCUSABLE ;
        windowManagerParams.format = PixelFormat.RGBA_8888; // 设置图片格式，效果为背景透明
        windowManagerParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
                | LayoutParams.FLAG_NOT_FOCUSABLE
                | LayoutParams.FLAG_FULLSCREEN;
        //LayoutParams

        // 调整悬浮窗口至左上角，便于调整坐标
        windowManagerParams.gravity = Gravity.LEFT | Gravity.TOP;
        // 以屏幕左上角为原点，设置x、y初始值
        windowManagerParams.x = (int)mPreferenceManager.getFloatX();
        windowManagerParams.y = (int)mPreferenceManager.getFloatY()-getStatusHeight(context);
        // 设置悬浮窗口长宽数据
        windowManagerParams.width = Utils.dip2px(getContext(),40);
        windowManagerParams.height =  Utils.dip2px(getContext(),40);
        isCanMove = mPreferenceManager.getIsCanMove();
        isRight = mPreferenceManager.isDisplayRight();
        setImageResource(ResourceUtils.getIdByName(context,"drawable","icon_mf_move_logo"));
        if (mPreferenceManager.getFirstFloatView()) {
            //第一次登陆出现悬浮球
            startTimerCount(6000);
        }else {
            startTimerCount(3000);
        }

    }

    private android.os.Handler handler = new android.os.Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case HIDE:
                    cancelTimerCount();
                    break;
            }
        };
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        isTouch = true;
        int statusBarHeight = getStatusHeight(getContext());
        // 获取相对屏幕的坐标，即以屏幕左上角为原点
        x = event.getRawX();
        y = event.getRawY() - statusBarHeight; // statusBarHeight是系统状态栏的高度

        int screenWidth = getContext().getResources().getDisplayMetrics().widthPixels;
        int screenHeight = getContext().getResources().getDisplayMetrics().heightPixels;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // 捕获手指触摸按下动作
                // 获取相对View的坐标，即以此View左上角为原点
                mTouchX = event.getX();
                mTouchY = event.getY();
                isMove = false;
                cancelTimerCount();
                break;

            case MotionEvent.ACTION_MOVE: // 捕获手指触摸移动动作
                int xMove = Math.abs((int) (event.getX() - mTouchX));
                int yMove = Math.abs((int) (event.getY() - mTouchY));
                if (xMove > 10 || yMove > 10) {
                    isMove = true;
                    updateViewPosition();
                }
                break;
            case MotionEvent.ACTION_UP: // 捕获手指触摸离开动作
                isTouch = false;
                if (isMove) {
                    isMove = false;
                    float halfScreen = screenWidth / 2;
                    if (x <= halfScreen) {
                        x = 0;
                        isRight = false;
                    } else {
                        x = (screenHeight > screenWidth ? screenHeight :screenWidth)
                                +Math.abs((int) mTouchX) + getNavigationHeight(getContext());
                        isRight = true;
                    }
                    updateViewPosition();
                    if (isCanMove) {
                        mPreferenceManager.setFloatX(x);
                        mPreferenceManager.setFloatY(y);
                        mPreferenceManager.setDisplayRight(isRight);
                    }
                    startTimerCount(3000);
                } else {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (mClickListener != null) {
                        mClickListener.onClick(this);
                    }
                }

                mTouchX = mTouchY = 0;
                break;
        }
        return true;
    }
    @Override
    public void setOnClickListener(OnClickListener l) {
        this.mClickListener = l;
    }
    private void updateViewPosition() {
        if (isCanMove) {
            // 更新浮动窗口位置参数
            windowManagerParams.x = (int) (x - mTouchX);
            windowManagerParams.y = (int) (y - mTouchY);
            windowManager.updateViewLayout(this, windowManagerParams); // 刷新显示
        }
    }

    public void startTimerCount(long delay){
        isCancel = false;
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if(!isTouch&&!isCancel){
                    handler.sendEmptyMessage(HIDE);
                }
            }
        };
        timer.schedule(timerTask, delay);
    }

    public void cancelTimerCount(){
        isCancel = true;
        if(timer!=null){
            timer.cancel();
            timer =null;
        }
        if(timerTask!=null){
            timerTask.cancel();
            timerTask = null;
        }
    }

    /**
     * 获得状态栏的高度
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context)  {
        int statusHeight = -1;
        try
        {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return statusHeight;
    }


    /**
     * 获得导航栏的高度
     * @param context
     * @return
     */
    public static int getNavigationHeight(Context context)  {
        int statusHeight = 0;
        int resourceId=context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");;
        if (resourceId>0) {
            statusHeight=context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusHeight;
    }
}
