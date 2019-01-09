package com.shetj.diyalbume.main.view

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.jakewharton.rxbinding2.view.RxView
import com.shetj.diyalbume.R
import kotlinx.android.synthetic.main.activity_main_3.*
import me.shetj.base.base.BaseActivity
import me.shetj.base.base.BasePresenter
import me.shetj.base.base.BaseSwipeBackActivity
import me.shetj.router.RouterUtils
import me.shetj.tencentx5.WebPageActivity

@Route(path = "/shetj/Main3Activity")
class Main3Activity : BaseSwipeBackActivity<BasePresenter<*>>() {


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
        btn_miui.setOnClickListener {
            RouterUtils.startOpen("/shetj/MiUIActivity")
        }

        btn_openGL.setOnClickListener{
            RouterUtils.startOpen("/shetj/GlTestActivity")
        }

        RxView.clicks(btn_openGL3D).subscribe {
            RouterUtils.startOpen("/shetj/OpenGL3DActivity")
        }
        RxView.clicks(btn_jiami).subscribe {
            RouterUtils.startOpen("/shetj/EncryptActivity")
        }
        RxView.clicks(btn_tup).subscribe{
            RouterUtils.startOpen("/shetj/ImageTestActivity")
        }

        RxView.clicks(btn_treadPool).subscribe {
            RouterUtils.startOpen("/shetj/ExecutorsActivity")
        }
        RxView.clicks(btn_animator).subscribe {
            RouterUtils.startOpen("/shetj/AnimatorActivity")
        }
        RxView.clicks(btn_bluetooth).subscribe {
            RouterUtils.startOpen("/shetj/BluetoothActivity")
        }

        RxView.clicks(btn_lottie).subscribe{
            RouterUtils.startOpen("/shetj/TestLottieActivity")
        }
        RxView.clicks(btn_finger).subscribe{
            RouterUtils.startOpen("/shetj/FingerPrintActivity")
        }

        RxView.clicks(btn_CustomTabs).subscribe {
            CustomTabsHelper.openUrl(rxContext,"https://github.com/SheTieJun")
        }
        RxView.clicks(btn_Aspect).subscribe {
            RouterUtils.startOpen("/shetj/AspectActivity")
        }

        RxView.clicks(btn_x5)
                .subscribe {
                    WebPageActivity.startBrowserActivity(this,"",1)
                }

        RxView.clicks(btn_map)
                .subscribe {
                    RouterUtils.startOpen("/shetj/BDMapActivity")
                }
        RxView.clicks(btn_fresco)
                .subscribe {
                    RouterUtils.startOpen("/shetj/FrescoActivity")
                }
    }

    override fun initData() {

    }
}
