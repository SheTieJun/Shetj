package com.shetj.diyalbume.animator

import android.os.Bundle
import com.shetj.diyalbume.R
import me.shetj.base.base.BaseActivity

class AnimatorActivity : BaseActivity<AnimatorPresenter>() {
    override fun initView() {
    }

    override fun initData() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animator)
    }
}
