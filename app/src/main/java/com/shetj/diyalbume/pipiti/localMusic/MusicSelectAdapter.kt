package com.shetj.diyalbume.pipiti.localMusic

import android.annotation.SuppressLint
import android.media.MediaPlayer
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.shetj.diyalbume.R
import java.util.*

class MusicSelectAdapter(data: MutableList<Music>) : BaseQuickAdapter<Music, BaseViewHolder>(R.layout.item_select_music2, data) {

    private var select = -1
    private val MusicList: ArrayList<Music>? = null
    private val player: MediaPlayer? = null

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


    override fun convert(helper: BaseViewHolder, item: Music) {
        item.let {
            helper.setText(R.id.tv_music_name, item.name)
                    .setText(R.id.tv_music_size, byte2FitMemorySize(item.size))
                    .setText(R.id.tv_music_time, formatTime(item.duration))
            addChildClickViewIds(R.id.tv_play)
        }


    }

    fun setSelect(position: Int) {
        select = position
    }

    fun getSelect(): String? {
        return if (select != -1) {
            MusicList!![select].url
        } else {
            null
        }
    }

    @SuppressLint("DefaultLocale")
    fun byte2FitMemorySize(byteNum: Long): String {
        return if (byteNum < 0) {
            "shouldn't be less than zero!"
        } else if (byteNum < KB) {
            String.format("%.2fB", byteNum + 0.005)
        } else if (byteNum < MB) {
            String.format("%.2fKB", byteNum / KB + 0.005)
        } else if (byteNum < GB) {
            String.format("%.2fMB", byteNum / MB + 0.005)
        } else {
            String.format("%.2fGB", byteNum / GB + 0.005)
        }
    }

    fun formatTime(ms: Long): String {
        val ss = 1000
        val mi = ss * 60
        val hh = mi * 60
        val dd = hh * 24
        //
        val day = ms / dd
        val hour = (ms - day * dd) / hh
        val minute = (ms - day * dd - hour * hh) / mi
        val second = (ms - day * dd - hour * hh - minute * mi) / ss
        val milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss
        //
        val strDay = if (day < 10) "0$day" else "" + day
        //        //天
        val strHour = if (hour < 10) "0$hour" else "" + hour
        //        //小时
        val strMinute = if (minute < 10) "0$minute" else "" + minute
        //        //分钟
        val strSecond = if (second < 10) "0$second" else "" + second
        //        //秒
        var strMilliSecond = if (milliSecond < 10) "0$milliSecond" else "" + milliSecond
        //
        return "$strMinute：$strSecond"
    }


}