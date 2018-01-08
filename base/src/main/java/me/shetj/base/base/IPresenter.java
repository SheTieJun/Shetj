package me.shetj.base.base;

public interface IPresenter {

    /**
     * 做一些初始化操作
     */
    void onStart();

    /**
     *  Activity#onDestroy() 调用{@link IPresenter#onDestroy()}
     */
    void onDestroy();
}