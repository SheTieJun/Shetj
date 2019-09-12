package com.shetj.diyalbume.aspect

import android.Manifest
import android.os.Bundle
import android.os.Message
import com.alibaba.android.arouter.facade.annotation.Route
import com.jakewharton.rxbinding3.view.clicks
import com.shetj.diyalbume.R
import kotlinx.android.synthetic.main.activity_aspect.*
import me.shetj.aspect.permission.MPermission
import me.shetj.base.base.BaseActivity
import me.shetj.base.tools.app.ArmsUtils

@Route(path = "/shetj/AspectActivity")
class AspectActivity : BaseActivity<AspectPresenter>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aspect)
    }

    override fun initData() {
        mPresenter = AspectPresenter(this)
    }


    override fun initView() {

        btn_get_info.clicks().subscribe{
           testAspect()
        }
        btn_get_log.clicks().subscribe{
            mPresenter?.testAspect()
        }
        btn_net_work.clicks().subscribe {
            mPresenter?.testNetWork()
        }
    }

    @MPermission(value = [(Manifest.permission.CAMERA),(Manifest.permission.WRITE_EXTERNAL_STORAGE)])
    private fun testAspect() {
        ArmsUtils.makeText("testAspect ok")
    }


    override fun updateView(message: Message) {
        super.updateView(message)

    }

}
