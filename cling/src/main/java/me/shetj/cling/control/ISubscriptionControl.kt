package me.shetj.cling.control

import android.content.Context
import me.shetj.cling.entity.IDevice

/**
 * 说明：tv端回调
 * 作者：zhouzhan
 * 日期：17/7/21 16:38
 */
interface ISubscriptionControl<T> {
    /**
     * 监听投屏端 AVTransport 回调
     */
    fun registerAVTransport(device: IDevice<T>, context: Context)

    /**
     * 监听投屏端 RenderingControl 回调
     */
    fun registerRenderingControl(device: IDevice<T>, context: Context)

    /**
     * 销毁: 释放资源
     */
    fun destroy()
}