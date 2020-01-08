package com.shetj.diyalbume.worker.wk_time

import android.content.Context
import android.os.CountDownTimer
import java.util.*


/**
 * 定时器管理
 * 1.课程定时（获取课程时间）
 * 2.时间定时 {1.自定义  2.固定时间 }
 *
 */
class TimerConfigure private constructor(){
    private var timeListenerList: MutableList<CallBack> ?= ArrayList() //回调
    private var countDownTimer: CountDownTimer ?=null //时间计时

    private var currentState = STATE_CLOSE //默认关闭

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
        val instance : TimerConfigure
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
     *  展示是定时类型
     */
    fun showTimePick(context: Context){
//        val  dialog = TimeTypeListBottomSheetDialog(context as Activity?,position)
//        dialog.setOnItemClickListener { adapter, _, position ->
//            this.position = position
//            val item = adapter.getItem(position) as TimeType
//            when(item.name){
//                "不开启" ->{
//                    cancel()
//                }
//                "自定义" ->{
//                    showTypePick(context)
//                }
//                "播放完当前课程" ->{
//                    finishByLecture()
//                }
//                else ->{
//                    startTime(duration = item.duration)
//                }
//            }
//            dialog.dismissBottomSheet()
//        }
//        dialog.showBottomSheet()
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
    fun cancel(){
        //如果时开启了定时
        if (currentState == STATE_START){
            countDownTimer?.cancel()
        }
        //如果时开启了课程定时
        if (currentState == STATE_COURSE){
            isCourseTimer = false
        }
        stateChange(STATE_CLOSE)
    }

    fun isCourseTime():Boolean = isCourseTimer

    fun addCallBack(callback: CallBack){
        timeListenerList?.add(callback)
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
    }
}