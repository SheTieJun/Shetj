package com.shetj.diyalbume.playVideo.video

import android.content.*
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.widget.Toast
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
import me.shetj.cling.control.callback.ControlCallback
import me.shetj.cling.entity.*
import me.shetj.cling.listener.BrowseRegistryListener
import me.shetj.cling.listener.DeviceListChangedListener
import me.shetj.cling.playUrl
import me.shetj.cling.service.ClingUpnpService
import me.shetj.cling.service.manager.ClingManager
import me.shetj.cling.service.manager.DeviceManager
import me.shetj.cling.util.ClingUtils
import me.shetj.cling.util.Utils
import timber.log.Timber
import java.util.*

@Route(path = "/shetj/PlayVideoActivity")
class PlayVideoActivity : BaseActivity<BasePresenter<*>>()   {
    /** 连接设备状态: 播放状态  */
    val PLAY_ACTION = 0xa1
    /** 连接设备状态: 暂停状态  */
    val PAUSE_ACTION = 0xa2
    /** 连接设备状态: 停止状态  */
    val STOP_ACTION = 0xa3
    /** 连接设备状态: 转菊花状态  */
    val TRANSITIONING_ACTION = 0xa4
    /** 获取进度  */
    val GET_POSITION_INFO_ACTION = 0xa5
    /** 投放失败  */
    val ERROR_ACTION = 0xa5
    private lateinit var mTransportStateBroadcastReceiver: TransportStateBroadcastReceiver
    private lateinit var mAdapter: AutoRecycleViewAdapter
    private var isPause  = true
    private var videoPlayFragment: VideoPlayFragment ?= null
    private val mClingPlayControl by lazy {
        ClingUtils.getClingPlayControl()
    }
    private val mBrowseRegistryListener by lazy {
        BrowseRegistryListener()
    }

    private val mHandler: Handler = InnerHandler()

    private val mUpnpServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder: ClingUpnpService.LocalBinder = service as ClingUpnpService.LocalBinder
            val beyondUpnpService: ClingUpnpService = binder.getService()
            val clingUpnpServiceManager = ClingManager.getInstance()
            clingUpnpServiceManager.setUpnpService(beyondUpnpService)
            clingUpnpServiceManager.setDeviceManager(DeviceManager())
            clingUpnpServiceManager.registry.addListener(mBrowseRegistryListener)
            //Search on service created.
            clingUpnpServiceManager.searchDevices()
        }

        override fun onServiceDisconnected(className: ComponentName) {
            ClingManager.getInstance().setUpnpService(null)
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

    private fun registerReceivers() { //Register play status broadcast
        mTransportStateBroadcastReceiver = TransportStateBroadcastReceiver()
        val filter = IntentFilter()
        filter.addAction(Intents.ACTION_PLAYING)
        filter.addAction(Intents.ACTION_PAUSED_PLAYBACK)
        filter.addAction(Intents.ACTION_STOPPED)
        filter.addAction(Intents.ACTION_TRANSITIONING)
        registerReceiver(mTransportStateBroadcastReceiver, filter)
    }


    private fun bindServices() { // Bind UPnP service
        val upnpServiceIntent = Intent(this, ClingUpnpService::class.java)
        bindService(upnpServiceIntent, mUpnpServiceConnection, Context.BIND_AUTO_CREATE)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paly_video)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        start_screen.setOnClickListener {
            val url = "https://vod.lycheer.net/e22cd48bvodtransgzp1253442168/d6b59e205285890789389180692/v.f20.mp4"
            mClingPlayControl.playUrl(url,object : ControlCallback<Any>{
                override fun success(response: IResponse<Any>) {
                    "投放成功".showToast()
                    //                    ClingUpnpServiceManager.getInstance().subscribeMediaRender();
                    ClingManager.getInstance().registerAVTransport(this@PlayVideoActivity)
                    ClingManager.getInstance().registerRenderingControl(this@PlayVideoActivity)
                }

                override fun fail(response: IResponse<Any>) {
                    "投放失败".showToast()
                    mHandler.sendEmptyMessage(ERROR_ACTION)
                }
            })

        }

        stop_screen.setOnClickListener {
            mClingPlayControl.stop(object :ControlCallback<Any>{
                override fun success(response: IResponse<Any>) {
                    "停止成功".showToast()
                }

                override fun fail(response: IResponse<Any>) {
                    "停止失败".showToast()
                    mHandler.sendEmptyMessage( ERROR_ACTION)
                }
            })
        }
        showRecycleView()
        initFragment()

        bindServices()
        registerReceivers()
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
        mAdapter = AutoRecycleViewAdapter(ArrayList()).apply {
            setOnItemClickListener { _, _, posotion ->
                getItem(posotion)?.apply {
                    // 选择连接设备
                    if (Utils.isNull(this)) {
                        return@setOnItemClickListener
                    }
                    ClingManager.getInstance().selectedDevice = this
                    val device  = this.device
                    if (Utils.isNull(device)) {
                        return@setOnItemClickListener
                    }
                    setPlay(posotion)
                }

            }
        }

        val linearLayoutManager = LinearLayoutManager(this)
        iRecyclerView?.apply {
            iRecyclerView.layoutManager = linearLayoutManager
            iRecyclerView.adapter =mAdapter
        }

        // 设置发现设备监听
        mBrowseRegistryListener.setOnDeviceListChangedListener(object : DeviceListChangedListener {
            override fun onDeviceRemoved(device: IDevice<*>?) {
                runOnUiThread { mAdapter.remove(device as ClingDevice) }
            }

            override fun onDeviceAdded(device: IDevice<*>?) {
                runOnUiThread { mAdapter.addData(device as ClingDevice)}
            }

        })
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
//        //如果旋转了就全屏
        if (!isPause) {
            videoPlayFragment?.onConfigurationChanged(newConfig)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacksAndMessages(null)
        unbindService(mUpnpServiceConnection)
        unregisterReceiver(mTransportStateBroadcastReceiver)
        ClingManager.getInstance().registry?.removeListener(mBrowseRegistryListener)
        ClingManager.getInstance().destroy()
        ClingDeviceList.getInstance().destroy()
    }


    /******************* end progress changed listener  */
     inner class InnerHandler : Handler() {

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                PLAY_ACTION -> {
                    Toast.makeText(this@PlayVideoActivity, "正在投放", Toast.LENGTH_SHORT).show()
                    mClingPlayControl.currentState = DLANPlayState.PLAY
                }
                PAUSE_ACTION -> {
                    Timber.i(  "Execute PAUSE_ACTION")
                    mClingPlayControl.currentState = DLANPlayState.PAUSE
                }
                STOP_ACTION -> {
                    Timber.i(  "Execute STOP_ACTION")
                    mClingPlayControl.currentState = DLANPlayState.STOP
                }
                TRANSITIONING_ACTION -> {
                    Timber.i( "Execute TRANSITIONING_ACTION")
                    "正在连接".showToast()
                }
                ERROR_ACTION -> {
                    Timber.i("Execute ERROR_ACTION")
                    "投放失败" .showToast()
                }
            }
        }
    }

    /**
     * 接收状态改变信息
     */
    inner class TransportStateBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            Timber.i( "Receive playback intent:$action")
            when {
                Intents.ACTION_PLAYING == action -> {
                    mHandler.sendEmptyMessage(  PLAY_ACTION)
                }
                Intents.ACTION_PAUSED_PLAYBACK == action -> {
                    mHandler.sendEmptyMessage( PAUSE_ACTION)
                }
                Intents.ACTION_STOPPED == action -> {
                    mHandler.sendEmptyMessage( STOP_ACTION)
                }
                Intents.ACTION_TRANSITIONING == action -> {
                    mHandler.sendEmptyMessage(TRANSITIONING_ACTION)
                }
            }
        }
    }
}
