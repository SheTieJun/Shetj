package com.shetj.diyalbume.camera

import android.os.Bundle
import com.shetj.diyalbume.R
import me.shetj.base.base.BaseActivity
import me.shetj.base.base.BasePresenter

/**
 * androidx camera2 test
 */
class CameraxActivity : BaseActivity<BasePresenter<*>>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camerax)
        initView()
        initData()
    }

    override fun initData() {

    }

    override fun initView() {

    }
}
