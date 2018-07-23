package com.shetj.diyalbume.bluetooth

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import me.shetj.base.tools.app.ArmsUtils


/**
 *
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2018/7/23<br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b> 蓝牙操作相关的记录 <br>
 *  <b>  <uses-permission android:name="android.permission.BLUETOOTH"/><br>
 *  <b>  <uses-permission android:name="android.permission.BLUETOOTH_ADMIN"/><br>
 *      6.0
 *      <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />

 *      <b> BLE 需要<br>
 *<b> <uses-feature android:name="android.hardware.bluetooth_le" android:required="true"/><b>
 */
object BluetoothUtils {
     const val REQUEST_ENABLE_BT: Int  = 0x0099
    /**
     * 获取Adapter
     */
    fun getBluetoothAdapter(): BluetoothAdapter {
        val defaultAdapter = BluetoothAdapter.getDefaultAdapter()

        if (null == defaultAdapter){
            ArmsUtils.makeText("本机未找到蓝牙功能")
        }
        return defaultAdapter
    }

    /**
     *   发现蓝牙
     *   defaultAdapter.startDiscovery();
     */



    /**
     *  if (mBluetooth.isDiscovering() == true) {
     *     mBluetooth.cancelDiscovery();
     *  }
     */

    /**
     * 打开蓝牙
     */
    fun openBluetooth(context: Activity, mBluetoothAdapter:BluetoothAdapter){
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            context.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        }
    }

    /***********
     * 扫描设备
     */
     fun scanLeDevice(enable: Boolean,mBluetoothAdapter:BluetoothAdapter) {
        mBluetoothAdapter.startDiscovery()
    }


}