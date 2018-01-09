package com.shetj.arms.mvp.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.shetj.arms.mvp.contract.SecondContract;


@ActivityScope
public class SecondPresenter extends BasePresenter<SecondContract.Model, SecondContract.View> {
	private RxErrorHandler mErrorHandler;
	private Application mApplication;
	private ImageLoader mImageLoader;
	private AppManager mAppManager;

	@Inject
	public SecondPresenter(SecondContract.Model model, SecondContract.View rootView
					, RxErrorHandler handler, Application application
					, ImageLoader imageLoader, AppManager appManager) {
		super(model, rootView);
		this.mErrorHandler = handler;
		this.mApplication = application;
		this.mImageLoader = imageLoader;
		this.mAppManager = appManager;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		this.mErrorHandler = null;
		this.mAppManager = null;
		this.mImageLoader = null;
		this.mApplication = null;
	}

}
