package com.shetj.arms.mvp.model

import android.app.Application

import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.ActivityScope

import javax.inject.Inject

import com.shetj.arms.mvp.contract.ThridContract


@ActivityScope
class ThridModel
@Inject
constructor(repositoryManager: IRepositoryManager, private var mGson: Gson?,
            private var mApplication: Application?) : BaseModel(repositoryManager), ThridContract.Model {

    override fun onDestroy() {
        super.onDestroy()
        this.mGson = null
        this.mApplication = null
    }

}