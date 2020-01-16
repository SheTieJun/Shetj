package com.shetj.diyalbume.playVideo.video

import android.content.res.Configuration
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.snackbar.Snackbar
import com.shetj.diyalbume.R
import kotlinx.android.synthetic.main.activity_paly_video.*
import kotlinx.android.synthetic.main.content_play_video.*
import me.shetj.base.base.BaseActivity
import me.shetj.base.base.BasePresenter
import me.shetj.base.kt.addFragmentToActivity
import me.shetj.base.kt.showToast
import me.shetj.base.kt.toJson
import me.shetj.base.tools.json.GsonKit
import me.shetj.dlna.DLNAManager
import me.shetj.dlna.DLNAPlayer
import me.shetj.dlna.bean.DeviceInfo
import me.shetj.dlna.bean.MediaInfo
import me.shetj.dlna.bean.MediaInfo.Companion.TYPE_VIDEO
import me.shetj.dlna.listener.DLNAControlCallback
import me.shetj.dlna.listener.DLNADeviceConnectListener
import me.shetj.dlna.listener.DLNARegistryListener
import me.shetj.dlna.listener.DLNAStateCallback
import org.fourthline.cling.model.action.ActionInvocation
import org.fourthline.cling.model.meta.Device
import org.fourthline.cling.registry.Registry
import org.fourthline.cling.support.model.DIDLObject
import timber.log.Timber
import java.util.*

@Route(path = "/shetj/PlayVideoActivity")
class PlayVideoActivity : BaseActivity<BasePresenter<*>>()  , DLNADeviceConnectListener {
    private lateinit var adapter: AutoRecycleViewAdapter
    private var isPause  = true
    private var videoPlayFragment: VideoPlayFragment ?= null
    private val player  by lazy {
        DLNAPlayer(this)
    }

    private val listListener =  object :DLNARegistryListener(){
        override fun onDeviceChanged(deviceInfoList: List<DeviceInfo>?) {
            deviceInfoList?.let {
                adapter?.setNewData(deviceInfoList.toMutableList())
            }
        }

        override fun onDeviceAdded(registry: Registry?, device: Device<*, *, *>?) {
            super.onDeviceAdded(registry, device)
        }

        override fun onDeviceRemoved(registry: Registry?, device: Device<*, *, *>?) {
            super.onDeviceRemoved(registry, device)
            adapter.removeDevice(device)
        }
    }

    override fun onActivityCreate() {
        super.onActivityCreate()
//        DLNAManager.instance.registerListener(listListener)

    }

    override fun onActivityDestroy() {
        super.onActivityDestroy()
//        DLNAManager.instance.unregisterListener(listListener)
    }


    override fun initData() {

    }

    override fun initView() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paly_video)
        DLNAManager.instance.init(this,object : DLNAStateCallback {
            override fun onConnected() {
                Timber.i("onConnected")
                DLNAManager.instance.registerListener(listListener)
                DLNAManager.instance.startBrowser()
                "链接成功".showToast()
            }

            override fun onDisconnected() {
                Timber.i("onDisconnected")
            }
        })
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        start_screen.setOnClickListener {
            player.setDataSource(MediaInfo(mediaName = "测试",
                    mediaType =TYPE_VIDEO,
                    uri = "https://vod.lycheer.net/e22cd48bvodtransgzp1253442168/d6b59e205285890789389180692/v.f20.mp4",
                    duration = 10))

            player.start(object :DLNAControlCallback{
                override fun onSuccess(invocation: ActionInvocation<*>?) {
                }

                override fun onReceived(invocation: ActionInvocation<*>?, vararg extra: Any?) {
                    Timber.i(extra.toJson())
                }

                override fun onFailure(invocation: ActionInvocation<*>?, errorCode: Int, errorMsg: String?) {

                }

            })
        }

        stop_screen.setOnClickListener {
            player.stop(object :DLNAControlCallback{
                override fun onSuccess(invocation: ActionInvocation<*>?) {
                }

                override fun onReceived(invocation: ActionInvocation<*>?, vararg extra: Any?) {
                    Timber.i(extra.toJson())
                }

                override fun onFailure(invocation: ActionInvocation<*>?, errorCode: Int, errorMsg: String?) {
                }
            })
        }

        showRecycleView()
        initFragment()
    }

    private fun initFragment() {
        videoPlayFragment = VideoPlayFragment()
        supportFragmentManager.addFragmentToActivity(videoPlayFragment!!,R.id.fl_content)
    }

    override fun onBackPressed() {
        if (videoPlayFragment?.onBackPressed()!!){
            super.onBackPressed()
        }
    }


    private fun showRecycleView() {
        adapter = AutoRecycleViewAdapter(ArrayList()).apply {
            setOnItemClickListener { _, _, posotion ->
                setPlay(posotion)
                getItem(posotion)?.apply {
                    player.connect(this)

                }

            }
        }
        val linearLayoutManager = LinearLayoutManager(this)
        iRecyclerView?.apply {
            iRecyclerView.layoutManager = linearLayoutManager
            iRecyclerView.adapter =adapter
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
//        //如果旋转了就全屏
        if (!isPause) {
            videoPlayFragment?.onConfigurationChanged(newConfig)
        }
    }

    override fun onConnect(deviceInfo: DeviceInfo?, errorCode: Int) {
        deviceInfo?.name?.showToast()
    }

    override fun onDisconnect(deviceInfo: DeviceInfo?, type: Int, errorCode: Int) {

    }

    override fun onDestroy() {
        super.onDestroy()
        DLNAManager.instance.unregisterListener(listListener)
    }
//
//    override fun onPause() {
//        super.onPause()
//        isPause = true
//    }
//
//    override fun onResume() {
//        super.onResume()
//        isPause = false
//    }

}
