package com.shetj.arms.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.shetj.arms.mvp.contract.FirstContract;
import com.shetj.arms.mvp.model.FirstModel;


@Module
public class FirstModule {
	private FirstContract.View view;

	/**
	 * 构建FirstModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
	 *
	 * @param view
	 */
	public FirstModule(FirstContract.View view) {
		this.view = view;
	}

	@ActivityScope
	@Provides
	FirstContract.View provideFirstView() {
		return this.view;
	}

	@ActivityScope
	@Provides
	FirstContract.Model provideFirstModel(FirstModel model) {
		return model;
	}
}