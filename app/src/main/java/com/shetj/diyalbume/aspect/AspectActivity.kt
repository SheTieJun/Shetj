package com.shetj.diyalbume.aspect

import android.Manifest
import android.os.Bundle
import com.jakewharton.rxbinding2.view.RxView
import com.shetj.diyalbume.R
import com.shetj.diyalbume.R.id.btn_get_info
import com.shetj.diyalbume.R.id.btn_get_log
import kotlinx.android.synthetic.main.activity_aspect.*
import me.shetj.aspect.permission.MPermission
import me.shetj.base.base.BaseActivity
import me.shetj.base.base.BaseMessage
import me.shetj.base.tools.app.ArmsUtils

class AspectActivity : BaseActivity<AspectPresenter>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aspect)
        initView()
        initData()
    }

    override fun initData() {
        mPresenter = AspectPresenter(this);
    }


    override fun initView() {

        RxView.clicks(btn_get_info).subscribe{
           testAspect()
        }
        RxView.clicks(btn_get_log).subscribe{
            mPresenter.testAspect()
        }
    }

    @MPermission(value = [(Manifest.permission.CAMERA),(Manifest.permission.WRITE_EXTERNAL_STORAGE)])
    private fun testAspect() {
        ArmsUtils.makeText("testAspect ok")
    }

    override fun updateView(message: BaseMessage<*>?) {
        super.updateView(message)
    }

}
