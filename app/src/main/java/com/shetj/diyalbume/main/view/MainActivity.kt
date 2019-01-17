package com.shetj.diyalbume.main.view

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.jakewharton.rxbinding2.view.RxView
import com.shetj.diyalbume.animator.AnimatorActivity
import com.shetj.diyalbume.aspect.AspectActivity
import com.shetj.diyalbume.bluetooth.BluetoothActivity
import com.shetj.diyalbume.encrypt.EncryptActivity
import com.shetj.diyalbume.executors.ExecutorsActivity
import com.shetj.diyalbume.fingerprint.FingerPrintActivity
import com.shetj.diyalbume.gltest.GlTestActivity
import com.shetj.diyalbume.gltest.OpenGL3DActivity
import com.shetj.diyalbume.image.FrescoActivity
import com.shetj.diyalbume.image.ImageTestActivity
import com.shetj.diyalbume.lottie.TestLottieActivity
import com.shetj.diyalbume.main.presenter.MainPresenter
import com.shetj.diyalbume.map.BDMapActivity
import com.shetj.diyalbume.miui.MiUIActivity
import com.shetj.diyalbume.pipiti.gesture.GestureActivity
import com.shetj.diyalbume.pipiti.localMusic.LocalMusicActivity
import com.shetj.diyalbume.playVideo.PlayVideoActivity
import com.shetj.diyalbume.ppttest.PPtTestActivity
import com.shetj.diyalbume.test.CustomActivity
import com.shetj.diyalbume.test.MobileInfoUtils
import com.shetj.diyalbume.utils.SneakerUtils
import com.tencent.mm.opensdk.modelbiz.JumpToBizProfile
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import me.shetj.base.base.BaseActivity
import me.shetj.base.tools.app.ArmsUtils
import me.shetj.base.tools.app.Utils
import me.shetj.download.DownloadService
import me.shetj.luck.StartAidlInterface
import me.shetj.tencentx5.WebPageActivity
import com.shetj.diyalbume.R

class MainActivity : BaseActivity<MainPresenter>(){
    private var iMyAidlInterface: StartAidlInterface? =null
    private val conn =object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            Log.i("xx","连接断开.....")
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            //连接成功
            Log.i("xx","连接.....")
            iMyAidlInterface = StartAidlInterface.Stub.asInterface(service)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ArmsUtils.statuInScreen(this,true)
        initView()
        initData()
        mPresenter =  MainPresenter(this)
        fab.setOnClickListener { view ->
            startActivity(Intent(this,Main3Activity::class.java))

        }
    }
    override fun initData() {
        val intent = Intent("me.shetj.StartService")
        intent.`package` = "me.shetj.luck"
        bindService(intent,conn, AppCompatActivity.BIND_AUTO_CREATE)
    }

    override fun initView() {
        val toolbarTitle = findViewById<TextView>(R.id.toolbar_title)
          findViewById<View>(R.id.toolbar_back).visibility = View.GONE;
        toolbarTitle.text = "主页"
        bt_create.setOnClickListener {
           mPresenter.showIntroduce()
        }
        RxView.clicks(btn_play).subscribe {
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
                    WebPageActivity.startBrowserActivity(this,"https://gitee.com/shetj",1)
                }

        RxView.clicks(btn_scheme)
                .subscribe {
                    WebPageActivity.startBrowserActivity(this, "file:///android_asset/test.html",1)
                }
        RxView.clicks(btn_notification)
                .subscribe {
                    mPresenter.showNotification()
                }
        RxView.clicks(btn_map)
                .subscribe {
                    ArmsUtils.startActivity(this,BDMapActivity::class.java)
                }
        RxView.clicks(btn_fresco)
                .subscribe {
                    ArmsUtils.startActivity(this,FrescoActivity::class.java)
                }
        RxView.clicks(btn_test_auto).subscribe {
                ArmsUtils.startActivity(this,ASAutoCodeActivity::class.java)
        }
        RxView.clicks(btn_view).subscribe {
            ArmsUtils.startActivity(this,ViewActivity::class.java)
        }
        RxView.clicks(btn_local_music).subscribe {
            ArmsUtils.startActivity(this,LocalMusicActivity::class.java)
        }
        RxView.clicks(btn_Service_1).subscribe {
            val apkName = DownloadService.getApkName("1.0.0", "app-toyo.apk");
            DownloadService.install(Utils.getApp(),"1.0.0",apkName,
                    "http://oss.qcshendeng.com/app-toyo.apk")
        }

        RxView.clicks(btn_ges).subscribe {
            ArmsUtils.startActivity(this,GestureActivity::class.java)
        }

        RxView.clicks(btn_open_luck).subscribe {
            iMyAidlInterface?.start()
        }
        RxView.clicks(btn_auto_open).subscribe {
            MobileInfoUtils.jumpStartInterface(this)
            MobileInfoUtils.toSelfSetting(this)
        }

        RxView.clicks(btn_sneaker).subscribe {
            SneakerUtils.sneakError(this,"错误提示","测试~测试")
        }

        RxView.clicks(btn_ppt).subscribe{
            ArmsUtils.startActivity(this,PPtTestActivity::class.java)
        }

        RxView.clicks(btn_account).subscribe {
            val appId = "wxf683bc1904cc8adb"//开发者平台ID
            val api = WXAPIFactory.createWXAPI(this, appId, false)

            if (api.isWXAppInstalled()) {
                val req = JumpToBizProfile.Req()
                req.toUserName = "gh_310dae0b822c" // 公众号原始ID
                req.extMsg = "shetj_test"
                req.profileType = JumpToBizProfile.JUMP_TO_NORMAL_BIZ_PROFILE // 普通公众号
                api.sendReq(req)
            } else {
                Toast.makeText(this, "微信未安装", Toast.LENGTH_SHORT).show()
            }

        }
    }

}
