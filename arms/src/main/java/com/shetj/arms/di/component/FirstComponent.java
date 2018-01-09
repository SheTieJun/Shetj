package com.shetj.arms.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.shetj.arms.di.module.FirstModule;

import com.shetj.arms.mvp.ui.activity.FirstActivity;

@ActivityScope
@Component(modules = FirstModule.class, dependencies = AppComponent.class)
public interface FirstComponent {
	void inject(FirstActivity activity);
}