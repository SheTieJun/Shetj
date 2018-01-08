package me.shetj.base.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.trello.rxlifecycle2.components.support.RxFragment;

import org.simple.eventbus.EventBus;

import java.lang.reflect.Field;

/**
 * fragment基类
 */
public abstract class BaseFragment extends RxFragment {
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";

    /**
     * The M activity.
     */
    protected Context mActivity;
    //是否可见状态
    protected boolean isVisible;
    //View已经初始化完成
    private boolean isPrepared;
    //是否第一次加载完
    private boolean isFirstLoad = true;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        isFirstLoad = true;
        //绑定View
        isPrepared = true;
        //初始化事件和获取数据, 在此方法中获取数据不是懒加载模式
        initEventAndData();
        //在此方法中获取数据为懒加载模式,如不需要懒加载,请在initEventAndData获取数据
        lazyLoad();
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = getActivity();
        if (useEventBus())//如果要使用eventbus请将此方法返回true
            EventBus.getDefault().register(this);//注册到事件主线
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (useEventBus())//如果要使用eventbus请将此方法返回true
            EventBus.getDefault().unregister(this);
        this.mActivity = null;
    }

    /**
     * 是否使用eventBus,默认为使用(true)，
     *
     * @return boolean
     */
    protected boolean useEventBus() {
        return true;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    @Override
    public void onAttach(Context context) {
        this.mActivity = context;
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()){
            isVisible = true;
            onVisible();
        }else {
            isVisible = false;
            onInvisible();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            isVisible = true;
            onVisible();
        }else {
            isVisible = false;
            onInvisible();
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    /**
     * 发布事件
     *
     * @param object the object
     */
    public void EventPost(Object object){
        EventBus.getDefault().post(object);
    }
    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /**
     * On visible.
     */
    protected void onVisible(){
        lazyLoad();
    }

    /**
     * On invisible.
     */
    protected void onInvisible(){}

    /**
     * Lazy load.
     */
    protected void lazyLoad(){
        if(!isPrepared || !isVisible || !isFirstLoad) return;
        isFirstLoad = false;
        lazyLoadData();
    }

    /**
     * Init event and data.
     */
    protected abstract void initEventAndData();

    /**
     * Lazy load data.
     */
    public abstract void lazyLoadData();
}
