package com.shetj.diyalbume.main.view

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.TooltipCompat
import com.jakewharton.rxbinding3.view.clicks
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import com.shetj.diyalbume.R
import com.shetj.diyalbume.animator.AnimatorActivity
import com.shetj.diyalbume.aspect.AspectActivity
import com.shetj.diyalbume.behavior.BehaviorActivity
import com.shetj.diyalbume.bluetooth.BluetoothActivity
import com.shetj.diyalbume.bubble.BubbleUtils
import com.shetj.diyalbume.camera.CameraxActivity
import com.shetj.diyalbume.encrypt.EncryptActivity
import com.shetj.diyalbume.executors.ExecutorsActivity
import com.shetj.diyalbume.fingerprint.FingerPrintActivity
import com.shetj.diyalbume.gltest.GlTestActivity
import com.shetj.diyalbume.gltest.OpenGL3DActivity
import com.shetj.diyalbume.image.FrescoActivity
import com.shetj.diyalbume.image.ImageTestActivity
import com.shetj.diyalbume.jobscheduler.JobSchedulerActivity
import com.shetj.diyalbume.lottie.TestLottieActivity
import com.shetj.diyalbume.main.presenter.MainPresenter
import com.shetj.diyalbume.map.BDMapActivity
import com.shetj.diyalbume.markdown.MarkDownActivity
import com.shetj.diyalbume.pipiti.gesture.GestureActivity
import com.shetj.diyalbume.pipiti.localMusic.LocalMusicActivity
import com.shetj.diyalbume.playVideo.media.MediaActivity
import com.shetj.diyalbume.playVideo.video.PlayVideoActivity
import com.shetj.diyalbume.ppttest.PPtTestActivity
import com.shetj.diyalbume.swipcard.SwipCardActivity
import com.shetj.diyalbume.test.CustomActivity
import com.shetj.diyalbume.utils.DownloadService
import com.shetj.diyalbume.utils.SneakerUtils
import com.shetj.diyalbume.worker.WorkerActivity
import com.tencent.mm.opensdk.modelbiz.JumpToBizProfile
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import kotlinx.android.synthetic.main.content_main.*
import me.shetj.base.base.BaseActivity
import me.shetj.base.tools.app.ArmsUtils
import me.shetj.base.tools.app.Utils
import me.shetj.constraintlayout.TestConstrainLayoutActivity
import me.shetj.download.view.DownloadActivity
import me.shetj.luck.StartAidlInterface
import me.shetj.mvvm.MvvmActivity
import me.shetj.tencentx5.WebPageActivity
import timber.log.Timber

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
        QMUIStatusBarHelper.setStatusBarDarkMode(this)
        mPresenter =  MainPresenter(this)
    }
    override fun initData() {
//        val intent = Intent("me.shetj.StartService")
//        intent.`package` = "me.shetj.luck"
//        bindService(intent,conn, AppCompatActivity.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        super.onDestroy()
//        unbindService(conn)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Timber.i("onAttachedToWindow")
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Timber.i("onDetachedFromWindow")
    }

    @SuppressLint("CheckResult")
    override fun initView() {
        bt_create.setOnClickListener {
           mPresenter?.showIntroduce()
        }
        btn_play.clicks().subscribe {
            startActivity(Intent(this, PlayVideoActivity::class.java))
        }
        btn_custom.setOnClickListener {
            startActivity(Intent(this,CustomActivity::class.java))
        }

        btn_openGL.setOnClickListener{ArmsUtils.startActivity(this,GlTestActivity::class.java)}

        btn_openGL3D.clicks().subscribe {
            ArmsUtils.startActivity( this,OpenGL3DActivity::class.java)
        }
        btn_jiami.clicks( ).subscribe {
            ArmsUtils.startActivity( this,EncryptActivity::class.java)
        }
        btn_tup.clicks( ).subscribe{
            ArmsUtils.startActivity( this,ImageTestActivity::class.java)
        }
        btn_bubble.clicks().subscribe {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                BubbleUtils.createBunbble2(this)
            }
        }

        btn_treadPool.clicks( ).subscribe {
            ArmsUtils.startActivity( this,ExecutorsActivity::class.java)
        }
        btn_animator.clicks().subscribe {
            ArmsUtils.startActivity( this,AnimatorActivity::class.java)
        }
        btn_bluetooth.clicks().subscribe {
            ArmsUtils.startActivity(this,BluetoothActivity::class.java)
        }

        btn_lottie.clicks().subscribe{
            ArmsUtils.startActivity(this,TestLottieActivity::class.java)
        }
        btn_finger.clicks().subscribe{
            ArmsUtils.startActivity(this,FingerPrintActivity::class.java)
        }

        btn_CustomTabs.clicks().subscribe {
            CustomTabsHelper.openUrl(rxContext,"https://github.com/SheTieJun")
        }
        btn_Aspect.clicks().subscribe {
            ArmsUtils.startActivity(this,AspectActivity::class.java)
        }
        btn_hotfix.clicks().subscribe {
            ArmsUtils.makeText("只是接入 未测试 阿里-hotfix！")
        }
        btn_behavior.setOnClickListener {
            ArmsUtils.startActivity(this,BehaviorActivity::class.java)
        }

        btn_x5.clicks()
                .subscribe {
                    WebPageActivity.startBrowserActivity(this,"https://gitee.com/shetj",1)
                }

        btn_scheme.clicks()
                .subscribe {
                    WebPageActivity.startBrowserActivity(this, "file:///android_asset/test.html",1)
                }
        btn_notification.clicks()
                .subscribe {
                    mPresenter?.showNotification()
                    TooltipCompat.setTooltipText(btn_notification,"通知栏")
                }
        btn_map.clicks()
                .subscribe {
                    ArmsUtils.startActivity(this,BDMapActivity::class.java)
                }
        btn_fresco.clicks( )
                .subscribe {
                    ArmsUtils.startActivity(this,FrescoActivity::class.java)
                }
        btn_test_auto.clicks().subscribe {
                ArmsUtils.startActivity(this,ASAutoCodeActivity::class.java)
        }
        btn_view.clicks().subscribe {
            ArmsUtils.startActivity(this,ViewActivity::class.java)
        }
        btn_local_music.clicks().subscribe {
            ArmsUtils.startActivity(this,LocalMusicActivity::class.java)
        }
        btn_Service_1.clicks().subscribe {
            val apkName = DownloadService.getApkName("1.0.0", "app-toyo.apk");
            DownloadService.install(Utils.app,"1.0.0",apkName,
                    "http://oss.qcshendeng.com/app-toyo.apk")
        }

        btn_ges.clicks().subscribe {
            ArmsUtils.startActivity(this,GestureActivity::class.java)
        }

        btn_open_luck.clicks().subscribe {
            iMyAidlInterface?.start()
        }
        btn_Worker.clicks().subscribe {
            ArmsUtils.startActivity(this,WorkerActivity::class.java)
        }

        btn_sneaker.clicks().subscribe {
            SneakerUtils.sneakError(this,"错误提示","测试~测试")
        }

        btn_ppt.clicks().subscribe{
            ArmsUtils.startActivity(this,PPtTestActivity::class.java)
        }

        btn_account.clicks().subscribe {
            val appId = "wx"//开发者平台ID
            val api = WXAPIFactory.createWXAPI(this, appId, false)

            if (api.isWXAppInstalled) {
                val req = JumpToBizProfile.Req()
                req.toUserName = "gh_310dae0b822c" // 公众号原始ID
                req.extMsg = "shetj_test"
                req.profileType = JumpToBizProfile.JUMP_TO_NORMAL_BIZ_PROFILE // 普通公众号
                api.sendReq(req)
            } else {
                Toast.makeText(this, "微信未安装", Toast.LENGTH_SHORT).show()
            }
        }
        btn_markdown.clicks().subscribe{
            ArmsUtils.startActivity(this,MarkDownActivity::class.java)
        }
        btn_download.clicks().subscribe {
            ArmsUtils.startActivity(this, DownloadActivity::class.java)
        }

        btn_job_scheduler.clicks().subscribe {
            ArmsUtils.startActivity(this, JobSchedulerActivity::class.java)
        }

        music.setOnClickListener {
            ArmsUtils.startActivity(this, MediaActivity::class.java)
        }

        btn_LayoutManager.setOnClickListener {
            ArmsUtils.startActivity(this,SwipCardActivity::class.java)
        }

        btn_camera.setOnClickListener {
            ArmsUtils.startActivity(this,CameraxActivity::class.java)
        }
        mvvm.setOnClickListener {
            ArmsUtils.startActivity(this,MvvmActivity::class.java)
        }
        btn_ConstrainLayout.setOnClickListener {
            ArmsUtils.startActivity(this, TestConstrainLayoutActivity::class.java)
        }
        btn_dark_mode.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(mPresenter!!.getModel())
        }
    }

}
