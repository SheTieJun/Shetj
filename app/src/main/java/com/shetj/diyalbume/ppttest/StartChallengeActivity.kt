package com.shetj.diyalbume.ppttest

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

import com.shetj.diyalbume.R

import me.shetj.base.base.BaseActivity
import me.shetj.base.base.BasePresenter

class StartChallengeActivity : BaseActivity<BasePresenter<*>>() {

    private var mIRecyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_challenge)
    }

    override fun initView() {

        mIRecyclerView = findViewById(R.id.iRecyclerView)


    }

    override fun initData() {

    }
}
