package me.shetj.cling

import me.shetj.cling.control.ClingPlayControl
import me.shetj.cling.control.callback.ControlCallback
import me.shetj.cling.entity.ClingDevice
import me.shetj.cling.entity.ClingDeviceList
import me.shetj.cling.entity.DLANPlayState
import me.shetj.cling.service.manager.ClingManager


fun ClingPlayControl.playUrl(url:String, callback: ControlCallback<*>){
    val currentState: Int = currentState
    /**
     * 通过判断状态 来决定 是继续播放 还是重新播放
     */
    if (currentState == DLANPlayState.STOP) {
        playNew(url, ClingPlayControl.TYPE_VIDEO,callback)
    } else {
        play(callback)
    }
}

/**
 * 刷新设备
 */
fun refreshDeviceList(onSuccess: (Collection<ClingDevice>.() -> Unit)? = {}) {
    val devices: Collection<ClingDevice>? = ClingManager.instance.dmrDevices
    ClingDeviceList.getInstance().clingDeviceList = devices
    if (devices != null) {
        onSuccess?.let {
            onSuccess(devices)
        }
    }
}


