package me.shetj.cling

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import me.shetj.cling.control.ClingPlayControl
import me.shetj.cling.control.callback.ControlCallback
import me.shetj.cling.entity.ClingDevice
import me.shetj.cling.entity.ClingDeviceList
import me.shetj.cling.entity.DLANPlayState
import me.shetj.cling.service.ClingUpnpService
import me.shetj.cling.service.ClingUpnpService.LocalBinder
import me.shetj.cling.service.manager.ClingManager
import me.shetj.cling.service.manager.DeviceManager


fun Context.initCling(onSuccess: (ClingManager.() -> Unit)? = {}): ServiceConnection {
    val clingUpnpServiceManager = ClingManager.getInstance()
    val mUpnpServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            Log.e("ClingUtils", "mUpnpServiceConnection onServiceConnected")
            val binder = service as LocalBinder
            val beyondUpnpService = binder.service
            clingUpnpServiceManager.setUpnpService(beyondUpnpService)
            clingUpnpServiceManager.setDeviceManager(DeviceManager())
            onSuccess?.let {
                onSuccess(clingUpnpServiceManager)
            }
            //Search on service created.
            clingUpnpServiceManager.searchDevices()
        }

        override fun onServiceDisconnected(className: ComponentName) {
            Log.e("ClingUtils", "mUpnpServiceConnection onServiceDisconnected")
            ClingManager.getInstance().setUpnpService(null)
        }
    }

    val upnpServiceIntent = Intent(this, ClingUpnpService::class.java)
    applicationContext.bindService(upnpServiceIntent, mUpnpServiceConnection, Context.BIND_AUTO_CREATE)

    return mUpnpServiceConnection
}



fun ClingPlayControl.playUrl(url:String, callback: ControlCallback<*>){
    @DLANPlayState.DLANPlayStates val currentState: Int = currentState
    /**
     * 通过判断状态 来决定 是继续播放 还是重新播放
     */
    if (currentState == DLANPlayState.STOP) {
        playNew(url, callback)
    } else {
        play(callback)
    }
}

/**
 * 刷新设备
 */
fun refreshDeviceList(onSuccess: (Collection<ClingDevice>.() -> Unit)? = {}) {
    val devices: Collection<ClingDevice>? = ClingManager.getInstance().dmrDevices
    ClingDeviceList.getInstance().clingDeviceList = devices
    if (devices != null) {
        onSuccess?.let {
            onSuccess(devices)
        }
    }
}


