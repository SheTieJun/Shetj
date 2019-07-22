package com.shetj.diyalbume.wk_time

import android.content.Context
import android.os.CountDownTimer
import java.util.ArrayList


/**
 * 定时器管理
 * 1.课程定时（获取课程时间）
 * 2.时间定时 {1.自定义  2.固定时间 }
 *
 */
class TimerConfigure private constructor(){

    val STATE_START = 0  //开启定时
    val STATE_CLOSE = 1 // 关闭/取消定时
    val STATE_COMPLETE = 2    //完成定时complete

    private var timeListenerList: MutableList<CallBack> ?= ArrayList() //回调
    private var countDownTimer: CountDownTimer ?=null //时间计时
    private var currentState = 1 //默认关闭
    private var isCourseTimer :Boolean = false //课程计时
    private var duration = 0L //计时时长
    private object Holder {
        val INSTANCE = TimerConfigure()
    }

    companion object{
        val instance :TimerConfigure
            get() = Holder.INSTANCE
    }

    /**
     * 开始计时
     */
    fun startTime(isCourseTimer: Boolean = false,duration:Long = 0L){
        cancel()
        this.isCourseTimer = isCourseTimer
        this.duration = duration
        start(duration)
        stateChange(STATE_START)
    }


    /**
     * 展示时间选择器
     * @param duration 课程时间
     */
    fun showTimePick(context: Context){

    }


    /**
     * 展示是定时类型
     */
    fun showTypePick(context: Context){

    }

    /**
     * 取消计时
     */
    fun cancel(){
        if (getState() != STATE_CLOSE){
            stop()
        }
    }


    fun addCallBack(callback: CallBack){
        timeListenerList?.add(callback)
    }

    fun removeCallBack(callback: CallBack){
        timeListenerList?.remove(callback)
    }


    private fun getState():Int{
        return currentState
    }

    private fun start(duration: Long = 0L){

        countDownTimer = object :CountDownTimer(0,duration){

            override fun onFinish() {
                stateChange(STATE_COMPLETE)
                stateChange(STATE_CLOSE)
            }

            override fun onTick(progress: Long) {
                progress(progress)
            }

        }

    }

    private fun stop(){
        countDownTimer?.cancel()
        stateChange(STATE_CLOSE)
    }


    /**
     * 状态变更
     */
    private fun stateChange(state: Int){
        this.currentState = state
        timeListenerList?.forEach {
            it.onStateChange(state)
        }
    }

    private fun progress(progress: Long){
        timeListenerList?.forEach {
            it.progress(progress)
        }
    }

    interface CallBack{
        /**
         * 进度回调
         */
        fun progress(progress: Long)

        /**
         * 状态改变
         * 开始/关闭/完成
         */
        fun onStateChange(state :Int)
    }
}