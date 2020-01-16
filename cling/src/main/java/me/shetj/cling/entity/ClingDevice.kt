package me.shetj.cling.entity

import org.fourthline.cling.model.meta.Device

class ClingDevice(private val mDevice: Device<*, *, *>) : IDevice<Device<*, *, *>?> {
    /** 是否已选中  */
    var isSelected = false


    override val device: Device<*, *, *>
        get() = mDevice

}