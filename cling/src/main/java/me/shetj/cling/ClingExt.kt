package me.shetj.cling

import me.shetj.cling.callback.ControlCallback
import me.shetj.cling.control.ClingPlayControl
import me.shetj.cling.entity.ClingDevice
import me.shetj.cling.entity.ClingDeviceList
import me.shetj.cling.entity.ClingPlayState
import me.shetj.cling.entity.ClingPlayType
import me.shetj.cling.manager.ClingManager


/**
 * 播放视频
 */
@JvmOverloads
fun ClingPlayControl.playUrl(url:String,ItemType :ClingPlayType = ClingPlayType.TYPE_VIDEO ,callback: ControlCallback<*>?){
    val currentState: ClingPlayState = currentState
    if (currentState == ClingPlayState.STOP) {
        playNew(url,ItemType,callback)
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


