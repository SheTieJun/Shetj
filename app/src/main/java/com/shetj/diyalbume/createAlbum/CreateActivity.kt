package com.shetj.diyalbume.createAlbum

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View.VISIBLE
import android.widget.RelativeLayout
import android.widget.TextView
import cn.a51mofang.base.base.BaseActivity
import com.shetj.diyalbume.R

import kotlinx.android.synthetic.main.activity_create.*

class CreateActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        initView()
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    private fun initView() {
        val title = findViewById<TextView>(R.id.toolbar_title)
        title.text = "创建相册"
        val back = findViewById<RelativeLayout>(R.id.toolbar_back)
        back.visibility = VISIBLE
        back.setOnClickListener { finish() }

    }


}
