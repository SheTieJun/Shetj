package me.shetj.cling.control.callback

import me.shetj.cling.entity.IResponse

interface ControlReceiveCallback<T> : ControlCallback<T> {
    fun receive(response: IResponse<T>?)
}