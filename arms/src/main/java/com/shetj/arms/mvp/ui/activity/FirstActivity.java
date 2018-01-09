package com.shetj.arms.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.shetj.arms.di.component.DaggerFirstComponent;
import com.shetj.arms.di.module.FirstModule;
import com.shetj.arms.mvp.contract.FirstContract;
import com.shetj.arms.mvp.presenter.FirstPresenter;

import com.shetj.arms.R;


import static com.jess.arms.utils.Preconditions.checkNotNull;


public class FirstActivity extends BaseActivity<FirstPresenter> implements FirstContract.View {


	@Override
	public void setupActivityComponent(AppComponent appComponent) {
		DaggerFirstComponent //如找不到该类,请编译一下项目
						.builder()
						.appComponent(appComponent)
						.firstModule(new FirstModule(this))
						.build()
						.inject(this);
	}

	@Override
	public int initView(Bundle savedInstanceState) {
		return R.layout.activity_first; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
	}

	@Override
	public void initData(Bundle savedInstanceState) {

	}


	@Override
	public void showLoading() {

	}

	@Override
	public void hideLoading() {

	}

	@Override
	public void showMessage(@NonNull String message) {
		checkNotNull(message);
		ArmsUtils.snackbarText(message);
	}

	@Override
	public void launchActivity(@NonNull Intent intent) {
		checkNotNull(intent);
		ArmsUtils.startActivity(intent);
	}

	@Override
	public void killMyself() {
		finish();
	}


}
