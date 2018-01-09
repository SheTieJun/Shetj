package com.shetj.arms.mvp.ui.activity

import android.content.Intent
import android.os.Bundle

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.shetj.arms.di.component.DaggerThridComponent
import com.shetj.arms.di.module.ThridModule
import com.shetj.arms.mvp.contract.ThridContract
import com.shetj.arms.mvp.presenter.ThridPresenter

import com.shetj.arms.R


import com.jess.arms.utils.Preconditions.checkNotNull


class ThridActivity : BaseActivity<ThridPresenter>(), ThridContract.View {


    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerThridComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .thridModule(ThridModule(this))
                .build()
                .inject(this)
    }

    override fun initView(savedInstanceState: Bundle): Int {
        return R.layout.activity_thrid //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    override fun initData(savedInstanceState: Bundle) {

    }


    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showMessage(message: String) {
        checkNotNull(message)
        ArmsUtils.snackbarText(message)
    }

    override fun launchActivity(intent: Intent) {
        checkNotNull(intent)
        ArmsUtils.startActivity(intent)
    }

    override fun killMyself() {
        finish()
    }


}
