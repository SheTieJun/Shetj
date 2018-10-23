package com.shetj.diyalbume.pipiti.gesture

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.shetj.diyalbume.R
import me.shetj.base.base.BaseActivity
import me.shetj.base.base.BasePresenter

/**
 * 手势
 */
@Route(path = "/shetj/GestureActivity")
class GestureActivity : BaseActivity<BasePresenter<*>>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gesture)

    }

    override fun initView() {

    }

    override fun initData() {

    }

}
