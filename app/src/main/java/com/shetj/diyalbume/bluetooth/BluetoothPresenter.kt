package com.shetj.diyalbume.bluetooth

import android.Manifest
import android.bluetooth.BluetoothGatt
import android.widget.TextView
import com.clj.fastble.BleManager
import com.clj.fastble.callback.BleGattCallback
import com.clj.fastble.callback.BleMtuChangedCallback
import com.clj.fastble.callback.BleScanCallback
import com.clj.fastble.data.BleDevice
import com.clj.fastble.exception.BleException
import com.tbruyelle.rxpermissions2.RxPermissions
import me.shetj.base.base.BaseModel
import me.shetj.base.base.BasePresenter
import me.shetj.base.base.IView
import me.shetj.base.tools.app.ArmsUtils


class BluetoothPresenter(view:IView) : BasePresenter<BaseModel>(view) {


    fun setting(bleDevice: BleDevice?,mtu :Int){
        BleManager.getInstance().setMtu(bleDevice, mtu, object : BleMtuChangedCallback() {
            override fun onSetMTUFailure(exception: BleException) {
                // 设置MTU失败
            }

            override fun onMtuChanged(mtu: Int) {
                // 设置MTU成功，并获得当前设备传输支持的MTU值
            }
        })
    }

    fun startSearch(textView: TextView) {
        BleManager.getInstance().scan(object : BleScanCallback(){
            override fun onScanFinished(scanResultList: MutableList<BleDevice>?) {
               view.updateView(getMessage(1,scanResultList))
            }

            override fun onScanStarted(success: Boolean) {
                ArmsUtils.makeText("开始搜索")
            }

            override fun onScanning(bleDevice: BleDevice?) {
            }

        })


    }

    fun connect(item: BleDevice?) {

        BleManager.getInstance().connect(item,object :BleGattCallback(){
            override fun onStartConnect() {
                ArmsUtils.makeText("开始连接")
            }

            override fun onDisConnected(isActiveDisConnected: Boolean, device: BleDevice?, gatt: BluetoothGatt?, status: Int) {
                ArmsUtils.makeText(" 连接中断 =isActiveDisConnected = ${isActiveDisConnected}表示是否是主动调用了断开连接方法")
            }

            override fun onConnectSuccess(bleDevice: BleDevice?, gatt: BluetoothGatt?, status: Int) {
                ArmsUtils.makeText("连接成功 = ${bleDevice?.name}")
            }

            override fun onConnectFail(bleDevice: BleDevice?, exception: BleException?) {
                ArmsUtils.makeText("连接失败 = ${bleDevice?.name}")
            }

        })
    }


    /**
     * 通过物理地址连接
     */
    fun connect(mac: String?) {

        BleManager.getInstance().connect(mac,object :BleGattCallback(){
            override fun onStartConnect() {
                ArmsUtils.makeText("开始连接")
            }

            override fun onDisConnected(isActiveDisConnected: Boolean, device: BleDevice?, gatt: BluetoothGatt?, status: Int) {
                ArmsUtils.makeText(" 连接中断 =isActiveDisConnected = ${isActiveDisConnected}表示是否是主动调用了断开连接方法")
            }

            override fun onConnectSuccess(bleDevice: BleDevice?, gatt: BluetoothGatt?, status: Int) {
                ArmsUtils.makeText("连接成功 = ${bleDevice?.name}")
            }

            override fun onConnectFail(bleDevice: BleDevice?, exception: BleException?) {
                ArmsUtils.makeText("连接失败 = ${bleDevice?.name}")
            }

        })
    }


    override fun onDestroy() {
        super.onDestroy()
    }

    fun stop()  {
        /**
         * 停止扫描
         */
        BleManager.getInstance().cancelScan();

        /**
         * 清空
         */
        BleManager.getInstance().destroy();
    }

    /**
     * 断开一个设备
     */
    fun breakBLe(bleDevice: BleDevice?){
        BleManager.getInstance().disconnect(bleDevice)
    }

    fun getPer(rxPermissions: RxPermissions) {
        rxPermissions.requestEach(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
              )
                .subscribe { permission ->
                    if (permission.granted) {
                        // 用户已经同意该权限
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                    } else {
                        // 用户拒绝了该权限，并且选中『不再询问』
                    }
                }
    }

    init {

    }


}
