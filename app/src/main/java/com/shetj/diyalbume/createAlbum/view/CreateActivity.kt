package com.shetj.diyalbume.createAlbum.view

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View.VISIBLE
import android.widget.RelativeLayout
import android.widget.TextView
import cn.a51mofang.base.base.BaseActivity
import com.shetj.diyalbume.R
import com.shetj.diyalbume.createAlbum.presenter.CreatePresenter

import kotlinx.android.synthetic.main.activity_create.*
import kotlinx.android.synthetic.main.content_create.*

class CreateActivity : BaseActivity() {

    private lateinit var mPresenter : CreatePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        initView()
        initData()

    }

    override fun initData() {
        mPresenter = CreatePresenter(this)
        mPresenter.createAlbum()
    }

    override fun initView() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        val title = findViewById<TextView>(R.id.toolbar_title)
        title.text = "创建相册"
        val back = findViewById<RelativeLayout>(R.id.toolbar_back)
        back.visibility = VISIBLE
        back.setOnClickListener { finish() }
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        iv_preview.post( {

        })
    }

    fun addView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
