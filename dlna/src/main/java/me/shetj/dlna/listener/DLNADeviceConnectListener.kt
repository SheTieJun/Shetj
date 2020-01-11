package me.shetj.dlna.listener

import me.shetj.dlna.bean.DeviceInfo

interface DLNADeviceConnectListener {
    fun onConnect(deviceInfo: DeviceInfo?, errorCode: Int)
    fun onDisconnect(deviceInfo: DeviceInfo?, type: Int, errorCode: Int)

    companion object {
        const val TYPE_DLNA = 1
        const val TYPE_IM = 2
        const val TYPE_NEW_LELINK = 3
        const val CONNECT_INFO_CONNECT_SUCCESS = 100000
        const val CONNECT_INFO_CONNECT_FAILURE = 100001
        const val CONNECT_INFO_DISCONNECT = 212000
        const val CONNECT_INFO_DISCONNECT_SUCCESS = 212001
        const val CONNECT_ERROR_FAILED = 212010
        const val CONNECT_ERROR_IO = 212011
        const val CONNECT_ERROR_IM_WAITTING = 212012
        const val CONNECT_ERROR_IM_REJECT = 212013
        const val CONNECT_ERROR_IM_TIMEOUT = 212014
        const val CONNECT_ERROR_IM_BLACKLIST = 212015
    }
}