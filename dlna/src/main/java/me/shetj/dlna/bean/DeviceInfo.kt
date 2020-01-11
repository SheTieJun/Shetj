package me.shetj.dlna.bean

import me.shetj.dlna.DLNAPlayer
import org.fourthline.cling.model.meta.Device
import java.io.Serializable

/**
 * Description：设备信息
 * <BR></BR>
 * Creator：yankebin
 * <BR></BR>
 * CreatedAt：2019-07-09
 */
class DeviceInfo : Serializable {
    var device: Device<*, *, *>? = null
    var name: String? = null
    var mediaID: String? = null
    var oldMediaID: String? = null
    var state = DLNAPlayer.UNKNOWN
    var isConnected = false

    constructor(device: Device<*, *, *>?, name: String?) {
        this.device = device
        this.name = name
    }

    constructor() {}

}