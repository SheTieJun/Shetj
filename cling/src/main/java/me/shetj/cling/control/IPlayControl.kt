package me.shetj.cling.control

import me.shetj.cling.control.callback.ControlCallback
import me.shetj.cling.control.callback.ControlReceiveCallback

/**
 * 说明：对视频的控制操作定义
 * 作者：zhouzhan
 * 日期：17/6/27 17:13
 */
interface IPlayControl {
    /**
     * 播放一个新片源
     *
     * @param url   片源地址
     */
    fun playNew(url: String?, ItemType: Int, callback: ControlCallback<*>?)

    /**
     * 播放
     */
    fun play(callback: ControlCallback<*>?)

    /**
     * 暂停
     */
    fun pause(callback: ControlCallback<*>?)

    /**
     * 停止
     */
    fun stop(callback: ControlCallback<*>?)

    /**
     * 视频 seek
     *
     * @param pos   seek到的位置(单位:毫秒)
     */
    fun seek(pos: Int, callback: ControlCallback<*>?)

    /**
     * 设置音量
     *
     * @param pos   音量值，最大为 100，最小为 0
     */
    fun setVolume(pos: Int, callback: ControlCallback<*>?)

    /**
     * 设置静音
     *
     * @param desiredMute   是否静音
     */
    fun setMute(desiredMute: Boolean, callback: ControlCallback<*>?)

    /**
     * 获取tv进度
     */
    fun getPositionInfo(callback: ControlReceiveCallback<*>?)

    /**
     * 获取音量
     */
    fun getVolume(callback: ControlReceiveCallback<*>?)
}