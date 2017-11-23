package com.shetj.diyalbume

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import cn.a51mofang.base.base.BaseActivity
import cn.a51mofang.base.tools.app.SnackbarUtil
import cn.a51mofang.base.tools.app.UiUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initView()

        fab.setOnClickListener { view ->
            SnackbarUtil.ShortSnackbar(view,"show",SnackbarUtil.Warning).show()
        }
    }

    private fun initView() {

        UiUtils.configRecycleView(iRecyclerView, LinearLayoutManager(this))

    }


}
