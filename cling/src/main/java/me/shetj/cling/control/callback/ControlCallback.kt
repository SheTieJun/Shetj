package me.shetj.cling.control.callback

import me.shetj.cling.entity.IResponse

interface ControlCallback<T> {
    fun success(response: IResponse<T>)
    fun fail(response: IResponse<T>)
}