package com.shetj.diyalbume.main.view

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.jakewharton.rxbinding3.view.clicks
import com.shetj.diyalbume.R
import kotlinx.android.synthetic.main.activity_main_3.*
import me.shetj.base.base.BaseActivity
import me.shetj.base.base.BasePresenter
import me.shetj.router.RouterUtils
import me.shetj.tencentx5.WebPageActivity

@Route(path = "/shetj/Main3Activity")
class Main3Activity : BaseActivity<BasePresenter<*>>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_3)
        initView()
    }
    override fun initView() {

        bt_create.setOnClickListener {
            RouterUtils.startOpen("/shetj/CreateActivity")
        }
        btn_paly.setOnClickListener {
            RouterUtils.startOpen("/shetj/PlayVideoActivity")
        }

        btn_custom.setOnClickListener {
            RouterUtils.startOpen("/shetj/CustomActivity")
        }
        btn_behavior.setOnClickListener {
            RouterUtils.startOpen("/shetj/MiUIActivity")
        }

        btn_openGL.setOnClickListener{
            RouterUtils.startOpen("/shetj/GlTestActivity")
        }

        btn_openGL3D.clicks().subscribe {
            RouterUtils.startOpen("/shetj/OpenGL3DActivity")
        }
        btn_jiami.clicks().subscribe {
            RouterUtils.startOpen("/shetj/EncryptActivity")
        }
        btn_tup.clicks().subscribe{
            RouterUtils.startOpen("/shetj/ImageTestActivity")
        }

        btn_treadPool.clicks().subscribe {
            RouterUtils.startOpen("/shetj/ExecutorsActivity")
        }
        btn_animator.clicks().subscribe {
            RouterUtils.startOpen("/shetj/AnimatorActivity")
        }
        btn_bluetooth.clicks().subscribe {
            RouterUtils.startOpen("/shetj/BluetoothActivity")
        }

        btn_lottie.clicks().subscribe{
            RouterUtils.startOpen("/shetj/TestLottieActivity")
        }
        btn_finger.clicks().subscribe{
            RouterUtils.startOpen("/shetj/FingerPrintActivity")
        }

        btn_CustomTabs.clicks().subscribe {
            CustomTabsHelper.openUrl(rxContext,"https://github.com/SheTieJun")
        }
        btn_Aspect.clicks().subscribe {
            RouterUtils.startOpen("/shetj/AspectActivity")
        }

        btn_x5.clicks()
                .subscribe {
                    WebPageActivity.startBrowserActivity(this,"",1)
                }

        btn_map.clicks()
                .subscribe {
                    RouterUtils.startOpen("/shetj/BDMapActivity")
                }
        btn_fresco.clicks()
                .subscribe {
                    RouterUtils.startOpen("/shetj/FrescoActivity")
                }
    }

    override fun initData() {

    }
}
