package com.shetj.diyalbume.utils

class TimeType {
    var name :String ?=null
    var duration :Long = 0L
    var isCourseTimer :Boolean = false

    constructor(name: String?, duration: Long, isCourseTimer: Boolean) {
        this.name = name
        this.duration = duration
        this.isCourseTimer = isCourseTimer
    }

    constructor(name: String?, duration: Long) {
        this.name = name
        this.duration = duration
    }


    companion object{
        @JvmStatic
        fun getTimeTypeList(): MutableList<TimeType>{
           val timeTypes =  ArrayList<TimeType>(7)
            timeTypes.add(TimeType("不开启",0))
            timeTypes.add(TimeType("播放完当前课程",0,true))
            timeTypes.add(TimeType("10分钟",10*60*1000))
            timeTypes.add(TimeType("20分钟",20*60*1000))
            timeTypes.add(TimeType("30分钟",30*60*1000))
            timeTypes.add(TimeType("60分钟",60*60*1000))
            timeTypes.add(TimeType("90分钟",90*60*1000))
//            timeTypes.add(TimeType("自定义",0))
            return timeTypes
        }

        @JvmStatic
        fun getTimeTypeList2(): MutableList<TimeType>{
            val timeTypes =  ArrayList<TimeType>(7)
            timeTypes.add(TimeType("不开启",0))
            timeTypes.add(TimeType("播完当前",0,true))
            timeTypes.add(TimeType("10分钟",10*60*1000))
            timeTypes.add(TimeType("20分钟",20*60*1000))
            timeTypes.add(TimeType("30分钟",30*60*1000))
            timeTypes.add(TimeType("60分钟",60*60*1000))
            timeTypes.add(TimeType("90分钟",90*60*1000))
//            timeTypes.add(TimeType("自定义",0))
            return timeTypes
        }

        fun getPlayModeList(): MutableList<String>{
            val modelList =  ArrayList<String>(2)
            modelList.add("单课循环")
            modelList.add("顺序播放")
            return modelList
        }
    }
}