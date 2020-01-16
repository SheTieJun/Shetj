package me.shetj.cling.control.callback

import me.shetj.cling.entity.IResponse

/**
 * 说明：设备控制操作 回调
 * 作者：zhouzhan
 * 日期：17/7/4 10:56
 */
interface ControlCallback<T> {
    fun success(response: IResponse<T>)
    fun fail(response: IResponse<T>)
}