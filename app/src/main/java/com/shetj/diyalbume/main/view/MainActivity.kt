package com.shetj.diyalbume.main.view

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.TextView
import cn.a51mofang.base.base.BaseActivity
import cn.a51mofang.base.tools.app.SnackbarUtil
import cn.a51mofang.base.tools.app.UiUtils
import com.shetj.diyalbume.R
import com.shetj.diyalbume.main.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : BaseActivity() {


    private lateinit var mPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initData()
        mPresenter =  MainPresenter(this)
        fab.setOnClickListener { view ->
            SnackbarUtil.ShortSnackbar(view,"show",SnackbarUtil.Warning).show()
        }
    }
    override fun initData() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initView() {
        val toolbarTitle = findViewById<TextView>(R.id.toolbar_title)
        toolbarTitle.text = "主页"
        UiUtils.configRecycleView(iRecyclerView, LinearLayoutManager(this))

        bt_create.setOnClickListener({
            mPresenter.startCreateAlbum()
        })


    }


}
