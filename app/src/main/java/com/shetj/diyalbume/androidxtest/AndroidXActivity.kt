package com.shetj.diyalbume.androidxtest

import android.os.Bundle
import com.shetj.diyalbume.R
import me.shetj.base.base.BaseActivity

class AndroidXActivity : BaseActivity<AndroidxPresenter>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_android_x)
    }
    override fun initData() {
    }

    override fun initView() {
    }
}
