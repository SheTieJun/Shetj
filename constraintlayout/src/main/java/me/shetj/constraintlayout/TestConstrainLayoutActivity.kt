package me.shetj.constraintlayout

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_test_constrain_layout.*
import me.shetj.base.base.BaseActivity
import me.shetj.base.kt.start
import me.shetj.constraintlayout.demo.*

class TestConstrainLayoutActivity : BaseActivity<ConstrainLayoutPresenter>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_constrain_layout)
    }

    override fun initData() {
    }

    override fun initView() {
        btn_flow.setOnClickListener {
            start(FlowActivity::class.java)
        }
        btn_Layer.setOnClickListener {
            start(LayerActivity::class.java)
        }
        btn_Helper.setOnClickListener {
            start(HelperActivity::class.java)
        }
        btn_other.setOnClickListener {
            start(OtherActivity::class.java)
        }
        btn_Filter.setOnClickListener {
            start(FilterViewActivity::class.java)
        }
        btn_Mock.setOnClickListener {
            start(MockViewActivity::class.java)
        }
        btn_MotionLayout.setOnClickListener {
            start(MotionLayoutActivity::class.java)
        }
    }
}
