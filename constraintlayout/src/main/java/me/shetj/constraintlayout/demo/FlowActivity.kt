package me.shetj.constraintlayout.demo

import android.os.Bundle
import me.shetj.base.base.BaseActivity
import me.shetj.constraintlayout.ConstrainLayoutPresenter
import me.shetj.constraintlayout.R

class FlowActivity : BaseActivity<ConstrainLayoutPresenter>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flow)
    }

    override fun initData() {
    }

    override fun initView() {
    }
}
