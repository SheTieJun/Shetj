package com.shetj.diyalbume.bluetooth

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.widget.TextView
import com.clj.fastble.BleManager
import com.clj.fastble.data.BleDevice
import com.clj.fastble.scan.BleScanRuleConfig
import com.jakewharton.rxbinding2.view.RxView
import com.shetj.diyalbume.R
import kotlinx.android.synthetic.main.activity_blue_tooth.*
import me.shetj.base.base.BaseActivity
import me.shetj.base.base.BaseMessage
import me.shetj.base.decoration.Decoration
import me.shetj.base.tools.app.ArmsUtils


/**
 * 学习蓝牙相关
 */
class BluetoothActivity : BaseActivity<BluetoothPresenter>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blue_tooth)
        initView()
        initData()
    }

    override fun initData() {

        //初始化

        BleManager.getInstance().init(application)

        if (!BleManager.getInstance().isBlueEnable){
            val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(intent, 0x01)
        }else{
            val scanRuleConfig =  BleScanRuleConfig.Builder()
//                    .setServiceUuids(serviceUuids)      // 只扫描指定的服务的设备，可选
//                    .setDeviceName(true, names)         // 只扫描指定广播名的设备，可选
//                    .setDeviceMac(mac)                  // 只扫描指定mac的设备，可选
                    .setAutoConnect(true)      // 连接时的autoConnect参数，可选，默认false
                    .setScanTimeOut(10000)              // 扫描超时时间，可选，默认10秒
                    .build();
            BleManager.getInstance().initScanRule(scanRuleConfig);
            /**
             * 配置日志
            默认打开库中的运行日志，如果不喜欢可以关闭
            BleManager enableLog(boolean enable)
            配置重连
            设置连接时重连次数和重连间隔（毫秒），默认为0次不重连
            BleManager setReConnectCount(int count, long interval)
            配置分包发送
            设置分包发送的时候，每一包的数据长度，默认20个字节
            BleManager setSplitWriteNum(int num)
            配置连接超时
            设置连接超时时间（毫秒），默认10秒
            BleManager setConnectOverTime(long time)
            配置操作超时
            设置readRssi、setMtu、write、read、notify、indicate的超时时间（毫秒），默认5秒
            BleManager setConnectOverTime(long time)
             */
            BleManager.getInstance()
                    .enableLog(true)
                    .setReConnectCount(1, 5000)
                    .setSplitWriteNum(20)
                    .setConnectOverTime(10000).operateTimeout = 5000

        }
        if (!BleManager.getInstance().isSupportBle){
            ArmsUtils.makeText("不支持BLE")
        }



    }

    private lateinit var adapter: BluetoothListAdapter

    override fun initView() {
        mPresenter = BluetoothPresenter(this)
        mPresenter.getPer(rxPermissions)
        adapter = BluetoothListAdapter(ArrayList())
        ArmsUtils.configRecycleView(iRecyclerView,LinearLayoutManager(rxContext))

        iRecyclerView.addItemDecoration(Decoration.builder().color(R.color.line_color).headerCount(1).build())
        val textView = TextView(rxContext)
        textView.text = "搜索"
        textView.gravity = Gravity.CENTER
        val dp10 = ArmsUtils.dip2px(10f)
        textView.setPadding(dp10, dp10,
                dp10, dp10)

        RxView.clicks(textView)
                .subscribe {
                    mPresenter.startSearch(textView)
                }
        adapter.addHeaderView(textView)

        adapter.setOnItemClickListener { _, _, position ->
            mPresenter.connect(this.adapter.getItem(position))
        }

        iRecyclerView.adapter = adapter
    }


    override fun updateView(message: BaseMessage<*>?) {
        super.updateView(message)
        when(message?.type ){
            1 ->{
                adapter.setNewData(message.obj as MutableList<BleDevice>?)
            }
        }
    }


}
