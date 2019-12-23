package com.shetj.diyalbume.base

import android.support.v4.media.MediaBrowserCompat

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.shetj.diyalbume.R

abstract class BaseAdapter(data: List<MediaBrowserCompat.MediaItem>?) : BaseQuickAdapter<MediaBrowserCompat.MediaItem, BaseViewHolder>(R.layout.item_select_music2, data) {

    private var position :Int = -1

    /******************** 存储相关常量  */
    /**
     * KB与Byte的倍数
     */
    val KB = 1024
    /**
     * MB与Byte的倍数
     */
    val MB = 1048576
    /**
     * GB与Byte的倍数
     */
    val GB = 1073741824

    /**
     * 设置选中的位置
     */
    fun setSelectPosition(targetPos: Int) {
        //如果不相等，说明有变化
        if (position != targetPos) {
            val old: Int = position
            this.position = targetPos
            if (old != -1) {
                notifyItemChanged(old + headerLayoutCount)
            }
            if (targetPos != -1) {
                notifyItemChanged(targetPos + headerLayoutCount)
            }
        }
    }

    fun byte2FitMemorySize(byteNum: Long): String {
        return when {
            byteNum < 0 -> {
                "shouldn't be less than zero!"
            }
            byteNum < KB -> {
                String.format("%.2fB", byteNum + 0.005)
            }
            byteNum < MB -> {
                String.format("%.2fKB", byteNum / KB + 0.005)
            }
            byteNum < GB -> {
                String.format("%.2fMB", byteNum / MB + 0.005)
            }
            else -> {
                String.format("%.2fGB", byteNum / GB + 0.005)
            }
        }
    }

    fun formatTime(ms: Long): String {
        val ss = 1000
        val mi = ss * 60
        val hh = mi * 60
        val dd = hh * 24
        val day = ms / dd
        val hour = (ms - day * dd) / hh
        val minute = (ms - day * dd - hour * hh) / mi
        val second = (ms - day * dd - hour * hh - minute * mi) / ss
        val milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss
        val strDay = if (day < 10) "0$day" else "" + day
        val strHour = if (hour < 10) "0$hour" else "" + hour
        val strMinute = if (minute < 10) "0$minute" else "" + minute
        val strSecond = if (second < 10) "0$second" else "" + second
        var strMilliSecond = if (milliSecond < 10) "0$milliSecond" else "" + milliSecond
        return "$strMinute：$strSecond"
    }


}