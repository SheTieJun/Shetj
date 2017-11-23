package cn.a51mofang.base.base;

import android.support.annotation.NonNull;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;


/**
 * Created by jess on 16/4/22.
 */
public interface IView {

    /**
     * 显示加载
     */
    void showLoading(String  msg);

    /**
     * 隐藏加载
     */
    void hideLoading();

    /**
     * 显示信息
     */
    void showMessage(@NonNull String message);
    /**
     * 返回当前的activity
     * @return
     */
    RxAppCompatActivity getRxContext();

}
