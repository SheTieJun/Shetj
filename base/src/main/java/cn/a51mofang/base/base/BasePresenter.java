package cn.a51mofang.base.base;


import org.simple.eventbus.EventBus;
import org.xutils.common.util.LogUtil;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 *
 * 在HttpManager中获取数据，在view（Activity）中展示数据
 *
 *
 */
public  class BasePresenter implements IPresenter{

    protected final String TAG = this.getClass().getSimpleName();
    private CompositeDisposable mCompositeDisposable;

    public BasePresenter() {
        LogUtil.i("onStart");
        onStart();
    }




    @Override
    public void onStart() {
        if (useEventBus())//如果要使用 Eventbus 请将此方法返回 true
            EventBus.getDefault().register(this);//注册 Eventbus
    }



    /**
     *  Activity#onDestroy() 调用{@link IPresenter#onDestroy()}
     */
    @Override
    public void onDestroy() {
        if (useEventBus())//如果要使用 Eventbus 请将此方法返回 true
            EventBus.getDefault().unregister(this);//解除注册 Eventbus
        unDispose();//解除订阅
        this.mCompositeDisposable = null;
    }

    /**
     * 是否使用 {@link EventBus},默认为使用(true)，
     *
     * @return
     */
    public boolean useEventBus() {
        return true;
    }


    /**
     * 将 {@link Disposable} 添加到 {@link CompositeDisposable} 中统一管理
     * 可在 {onDestroy() 中使用 {@link #unDispose()} 停止正在执行的 RxJava 任务,避免内存泄漏
     *
     * @param disposable
     */
    public void addDispose(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);//将所有 Disposable 放入集中处理
    }

    /**
     * 停止集合中正在执行的 RxJava 任务
     */
    public void unDispose() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();//保证 Activity 结束时取消所有正在执行的订阅
        }
    }



}
