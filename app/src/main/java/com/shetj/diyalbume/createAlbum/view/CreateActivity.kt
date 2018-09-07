package com.shetj.diyalbume.createAlbum.view

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.Gravity
import android.view.View.VISIBLE
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.shetj.diyalbume.R
import com.shetj.diyalbume.createAlbum.presenter.CreatePresenter
import kotlinx.android.synthetic.main.activity_create.*
import kotlinx.android.synthetic.main.content_create.*
import me.shetj.base.base.BaseActivity
import me.shetj.base.tools.app.ArmsUtils

class CreateActivity : BaseActivity<CreatePresenter>() {


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
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show()
        }

        iv_preview.post( {
            var screenHeight = iv_preview.height
            var screenWidth  = iv_preview.width
            val x = screenWidth / screenHeight
            val f = 16f / 9f
            if (x > f) {
                screenWidth = (f * screenHeight).toInt()
            } else {
                screenHeight = (9f / 16f * screenWidth).toInt()
            }
            val params = LinearLayout.LayoutParams(screenWidth, screenHeight)
            params.gravity = Gravity.CENTER
            val margin = ArmsUtils.dip2px(10f)
            params.setMargins(margin,margin,margin,margin)
            iv_preview.layoutParams = params
        })
    }

    fun addView() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
