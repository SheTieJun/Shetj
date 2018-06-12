package com.shetj.diyalbume.main.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.TextView
import me.shetj.base.base.BaseActivity
import me.shetj.base.tools.app.ArmsUtils
import me.shetj.base.tools.app.SnackbarUtil
import me.shetj.base.tools.app.UiUtils
import com.shetj.diyalbume.R
import com.shetj.diyalbume.main.presenter.MainPresenter
import com.shetj.diyalbume.miui.MiUIActivity
import com.shetj.diyalbume.playVideo.PlayVideoActivity
import com.shetj.diyalbume.test.CustomActivity
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
            SnackbarUtil.ShortSnackbar(view,"show", SnackbarUtil.Warning).show()
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
        btn_paly.setOnClickListener({
            startActivity(Intent(this,PlayVideoActivity::class.java))
        })

        btn_custom.setOnClickListener {
            startActivity(Intent(this,CustomActivity::class.java))
        }
        btn_miui.setOnClickListener { ArmsUtils.startActivity(this,MiUIActivity::class.java) }



    }


}
