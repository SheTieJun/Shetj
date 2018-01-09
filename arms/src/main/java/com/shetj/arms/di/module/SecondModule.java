package com.shetj.arms.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.shetj.arms.mvp.contract.SecondContract;
import com.shetj.arms.mvp.model.SecondModel;


@Module
public class SecondModule {
	private SecondContract.View view;

	/**
	 * 构建SecondModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
	 *
	 * @param view
	 */
	public SecondModule(SecondContract.View view) {
		this.view = view;
	}

	@ActivityScope
	@Provides
	SecondContract.View provideSecondView() {
		return this.view;
	}

	@ActivityScope
	@Provides
	SecondContract.Model provideSecondModel(SecondModel model) {
		return model;
	}
}