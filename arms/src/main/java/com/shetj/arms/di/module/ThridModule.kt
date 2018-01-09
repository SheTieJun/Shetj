package com.shetj.arms.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.shetj.arms.mvp.contract.ThridContract
import com.shetj.arms.mvp.model.ThridModel


@Module
class ThridModule
/**
 * 构建ThridModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
 *
 * @param view
 */
(private val view: ThridContract.View) {

    @ActivityScope
    @Provides
    internal fun provideThridView(): ThridContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    internal fun provideThridModel(model: ThridModel): ThridContract.Model {
        return model
    }
}