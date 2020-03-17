package com.shetj.diyalbume.utils

import android.app.Activity
import android.content.Context
import android.os.CountDownTimer
import android.support.v4.media.session.PlaybackStateCompat
import android.widget.Toast
import androidx.annotation.IntDef
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import me.shetj.base.kt.showToast
import java.util.*


/**
 * 定时器管理
 * 1.课程定时（获取课程时间）
 * 2.时间定时 {1.自定义  2.固定时间 }
 * 3.课程播放模式 （循环 和 列表）
 */
class TimerConfigure private constructor(){

    @IntDef(
            PlaybackStateCompat.REPEAT_MODE_ONE,// 单课循环
            PlaybackStateCompat.REPEAT_MODE_ALL// 列表播放
    )
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class RepeatMode

    private var timeListenerList: MutableList<CallBack> ?= ArrayList() //回调
    private var countDownTimer: CountDownTimer ?=null //时间计时

    private var currentState = STATE_CLOSE //默认关闭

    private var repeatMode = PlaybackStateCompat.REPEAT_MODE_ALL //默认列表播放
    private var isCourseTimer :Boolean = false //是否是课程计时
    private var duration = 0L //计时时长

    private var position = 0

    //选择时间部分
    private var hour  = 0
    private var minute = 0


    private object Holder {
        val INSTANCE = TimerConfigure()
    }

    companion object{

        const  val STATE_START = 0  //开启定时
        const  val STATE_CLOSE = 1 // 关闭/取消定时
        const  val STATE_COMPLETE = 2    //完成定时complete
        const  val STATE_COURSE = 3 //切换到课程计时

        @JvmStatic
        val instance :TimerConfigure
            get() = Holder.INSTANCE
    }

    /**
     * 开始计时
     */
    fun startTime(isCourseTimer: Boolean = false,duration:Long = 0L){
        cancel(false)
        this.isCourseTimer = isCourseTimer
        this.duration = duration
        start(duration)
        stateChange(STATE_START)
    }

    /**
     * 修改课程模式
     */
    fun setRepeatMode(context: Context,@RepeatMode repeatMode:Int){
        if (this.repeatMode != repeatMode) {
            this.repeatMode = repeatMode
            timeListenerList?.forEach {
                AndroidSchedulers.mainThread().scheduleDirect {
                    it.onChangeModel(repeatMode)
                     when(repeatMode){
                        PlaybackStateCompat.REPEAT_MODE_ONE ->{
                            "已切换到单课循环"
                        }
                        PlaybackStateCompat.REPEAT_MODE_ALL ->{
                            "已切换到顺序播放"
                        }
                        else -> "已切换到顺序播放"
                    }.showToast()
                }
            }
        }
    }

    /**
     * 切换播放模式
     */
    fun changePlayMode(context: Context) {
        if (repeatMode == PlaybackStateCompat.REPEAT_MODE_ONE){
            setRepeatMode(context,PlaybackStateCompat.REPEAT_MODE_ALL)
        }else{
            setRepeatMode(context,PlaybackStateCompat.REPEAT_MODE_ONE)
        }
    }

    /**
     *  展示是定时类型
     */
    fun showTimePick(context: Context){
        val  dialog = TimeTypeListBottomSheetDialog(context as Activity?,position)
        dialog.setOnItemClickListener { adapter, _, position ->
            this.position = position
            val item = adapter.getItem(position) as TimeType
            setTimeType(item, context)
            dialog.dismissBottomSheet()
        }
        dialog.showBottomSheet()
    }

    fun setTimeType(item: TimeType, context: Context) {
        when (item.name) {
            "不开启" -> {
                cancel(true)
            }
            "自定义" -> {
                showTypePick(context)
            }
            "播放完当前课程" ,"播完当前"-> {
                finishByLecture()
            }
            else -> {
                startTime(duration = item.duration)
            }
        }
    }

    private fun finishByLecture() {
        isCourseTimer = true
        countDownTimer?.cancel()
        stateChange(STATE_COURSE)
    }

    /**
     * 展示时间选择器
     */
    private fun showTypePick(context: Context){
//        val dialogFragment = TimePickerDialogFragment.newInstance("自定义定时关闭")
//        dialogFragment.setSelectedDate(hour,minute)
//        dialogFragment.setOnTimeSelectListener { hour, minute ->
//            run {
//                val time = getDurationByTime(hour, minute)
//                startTime(duration = time)
//            }
//        }
//        dialogFragment.show((context as AppCompatActivity).supportFragmentManager!!, "TimePickerDialogFragment")
    }

    /**
     * 取消计时
     */
    fun cancel(isUpdate: Boolean = true) {
        //如果时开启了定时
        if (currentState == STATE_START){
            countDownTimer?.cancel()
        }
        //如果时开启了课程定时
        if (currentState == STATE_COURSE){
            isCourseTimer = false
        }
        if (isUpdate) {
            stateChange(STATE_CLOSE)
        }
    }

    fun isCourseTime(): Boolean = isCourseTimer

    //不是课程结束控制，让播放模式是单曲循环
    fun isRepeatOne(): Boolean =!isCourseTimer && repeatMode == PlaybackStateCompat.REPEAT_MODE_ONE

    fun addCallBack(callback: CallBack){
        timeListenerList?.add(callback)
        //设置默认情况
        callback.onChangeModel(repeatMode)
        callback.onStateChange(currentState)
    }

    fun removeCallBack(callback: CallBack){
        timeListenerList?.remove(callback)
    }

    /**
     * 状态变更
     */
    fun stateChange(state: Int){
        this.currentState = state
        when(state){
            STATE_CLOSE ->{
                resetDate()
            }
            STATE_COMPLETE ->{
                resetDate()
                position = 0
            }
        }
        timeListenerList?.forEach {
            it.onStateChange(state)
        }
    }

    fun getTimeTypePosition():Int{
        return position
    }

    private fun resetDate(){
        hour = 0
        minute = 0
        duration = 0
        isCourseTimer = false
    }

    private fun getDurationByTime(hour: Int, minute: Int): Long {
        this.hour = hour
        this.minute = minute
        var time = 0L
        if(hour > 0){
            time += hour * 3600 * 1000
        }
        if (minute > 0){
            time += minute * 60 *1000
        }
        return time
    }

    private fun start(duration: Long = 0L){
        countDownTimer = object :CountDownTimer(duration, 1000){
            override fun onFinish() {
                stateChange(STATE_COMPLETE)
            }
            override fun onTick(progress: Long) {
                progress(progress)
            }
        }
        countDownTimer?.start()
    }


    private fun progress(progress: Long){
        timeListenerList?.forEach {
            it.onTick(progress)
        }
    }


    interface CallBack{
        /**
         * 进度回调
         */
        fun onTick(progress: Long)
        /**
         * 状态改变
         * 开始/关闭/完成
         */
        fun onStateChange(state :Int)

        /**
         * 2020年2月13日11:02:56
         * 新增切换循环模式
         */
        fun onChangeModel(@RepeatMode repeatMode:Int)
    }
}