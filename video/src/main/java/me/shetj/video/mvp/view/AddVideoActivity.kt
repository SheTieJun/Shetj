package me.shetj.video.mvp.view

import android.os.Bundle
import me.shetj.base.base.BaseActivity
import me.shetj.video.R
import me.shetj.base.kt.statusInScreen
import me.shetj.video.mvp.presenter.VideoPresenter

/**
 * 添加视频界面
 */
class AddVideoActivity : BaseActivity<VideoPresenter>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_video)
        statusInScreen()
    }

    override fun initData() {

    }

    override fun initView() {

    }
}
