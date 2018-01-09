package com.shetj.arms.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.shetj.arms.di.module.SecondModule;

import com.shetj.arms.mvp.ui.activity.SecondActivity;
import com.shetj.arms.mvp.ui.fragment.SecondFragment;

@ActivityScope
@Component(modules = SecondModule.class, dependencies = AppComponent.class)
public interface SecondComponent {
	void inject(SecondActivity activity);

	void inject(SecondFragment fragment);
}