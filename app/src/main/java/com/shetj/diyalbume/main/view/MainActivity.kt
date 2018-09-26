package com.shetj.diyalbume.main.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.TextView
import com.jakewharton.rxbinding2.view.RxView
import com.shetj.diyalbume.R
import com.shetj.diyalbume.animator.AnimatorActivity
import com.shetj.diyalbume.aspect.AspectActivity
import com.shetj.diyalbume.bluetooth.BluetoothActivity
import com.shetj.diyalbume.encrypt.EncryptActivity
import com.shetj.diyalbume.executors.ExecutorsActivity
import com.shetj.diyalbume.fingerprint.FingerPrintActivity
import com.shetj.diyalbume.gltest.GlTestActivity
import com.shetj.diyalbume.gltest.OpenGL3DActivity
import com.shetj.diyalbume.image.ImageTestActivity
import com.shetj.diyalbume.lottie.TestLottieActivity
import com.shetj.diyalbume.main.presenter.MainPresenter
import com.shetj.diyalbume.miui.MiUIActivity
import com.shetj.diyalbume.playVideo.PlayVideoActivity
import com.shetj.diyalbume.test.CustomActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import me.shetj.base.base.BaseActivity
import me.shetj.base.tools.app.ArmsUtils
import me.shetj.base.tools.app.SnackbarUtil
import me.shetj.tencentx5.WebPageActivity

class MainActivity : BaseActivity<MainPresenter>(){


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
    }

    override fun initView() {
        val toolbarTitle = findViewById<TextView>(R.id.toolbar_title)
        toolbarTitle.text = "主页"
        ArmsUtils.configRecycleView(iRecyclerView, LinearLayoutManager(this))
        bt_create.setOnClickListener({
            mPresenter.startCreateAlbum()
        })
        btn_paly.setOnClickListener {
            startActivity(Intent(this,PlayVideoActivity::class.java))
        }

        btn_custom.setOnClickListener {
            startActivity(Intent(this,CustomActivity::class.java))
        }
        btn_miui.setOnClickListener { ArmsUtils.startActivity(this,MiUIActivity::class.java) }

        btn_openGL.setOnClickListener{ArmsUtils.startActivity(this,GlTestActivity::class.java)}

        RxView.clicks(btn_openGL3D).subscribe {
            ArmsUtils.startActivity( this,OpenGL3DActivity::class.java)
        }
        RxView.clicks(btn_jiami).subscribe {
            ArmsUtils.startActivity( this,EncryptActivity::class.java)
        }
        RxView.clicks(btn_tup).subscribe{
            ArmsUtils.startActivity( this,ImageTestActivity::class.java)
        }

        RxView.clicks(btn_treadPool).subscribe {
            ArmsUtils.startActivity( this,ExecutorsActivity::class.java)
        }
        RxView.clicks(btn_animator).subscribe {
            ArmsUtils.startActivity( this,AnimatorActivity::class.java)
        }
        RxView.clicks(btn_bluetooth).subscribe {
            ArmsUtils.startActivity(this,BluetoothActivity::class.java)
        }

        RxView.clicks(btn_lottie).subscribe{
            ArmsUtils.startActivity(this,TestLottieActivity::class.java)
        }
        RxView.clicks(btn_finger).subscribe{
            ArmsUtils.startActivity(this,FingerPrintActivity::class.java)
        }

        RxView.clicks(btn_CustomTabs).subscribe {
            CustomTabsHelper.openUrl(rxContext,"https://github.com/SheTieJun")
        }
        RxView.clicks(btn_Aspect).subscribe {
            ArmsUtils.startActivity(this,AspectActivity::class.java)
        }
        RxView.clicks(btn_hotfix).subscribe {
            ArmsUtils.makeText("只是接入 未测试 阿里-hotfix！")
        }

        RxView.clicks(btn_x5)
                .subscribe {
                    ArmsUtils.startActivity(this,WebPageActivity::class.java)
                }

        RxView.clicks(btn_notification)
                .subscribe {
                    mPresenter.showNotification()
                }
    }


}
