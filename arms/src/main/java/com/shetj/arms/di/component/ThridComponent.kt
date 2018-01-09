package com.shetj.arms.di.component

import com.jess.arms.di.scope.ActivityScope

import dagger.Component

import com.jess.arms.di.component.AppComponent

import com.shetj.arms.di.module.ThridModule

import com.shetj.arms.mvp.ui.activity.ThridActivity

@ActivityScope
@Component(modules = [(ThridModule::class)], dependencies = [(AppComponent::class)])
interface ThridComponent {
    fun inject(activity: ThridActivity)
}