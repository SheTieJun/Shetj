package com.shetj.arms.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.shetj.arms.mvp.contract.FirstContract;


@ActivityScope
public class FirstModel extends BaseModel implements FirstContract.Model {
	private Gson mGson;
	private Application mApplication;

	@Inject
	public FirstModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
		super(repositoryManager);
		this.mGson = gson;
		this.mApplication = application;

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		this.mGson = null;
		this.mApplication = null;
	}

}