package com.shetj.diyalbume.utils

import androidx.annotation.Keep
import java.text.SimpleDateFormat
import java.util.*

/**
 * 类名称：TimeUtil.java <br></br>
 * @author shetj<br></br>
 */
@Keep
object TimeUtil {

    /**
     * 得到时间戳
     * @return
     */
    val time: Long
        get() = System.currentTimeMillis()

    val ymdhmsTime: String
        get() {
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            return format.format(Date())
        }

    val hmsTime: String
        get() {
            val format = SimpleDateFormat("HH:mm:ss")
            return format.format(Date())

        }


    val ymDime: String
        get() {
            val format = SimpleDateFormat("yyyy-MM-dd")
            return format.format(Date())

        }

    /**
     * 方法名：  formatFromNoformt	<br></br>
     * 方法描述：返回时间差时间<br></br>
     * 修改备注：<br></br>
     * 创建时间： 2016-4-18上午10:54:52<br></br>
     * @param time
     * @return
     */
    fun getTimeDelay(time: String): String {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        try {
            val str = format.format(Date())
            val d1 = format.parse(str)
            val d2 = format.parse(time)
            val diff = d1.time - d2.time//这样得到的差值是微秒级别
            val days = diff / (1000 * 60 * 60 * 24)
            val hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)
            val minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60)
            if (days.toDouble() == 0.0 && hours.toDouble() == 0.0 && minutes.toDouble() == 0.0) {
                return "刚刚 "
            }
            if (days.toDouble() == 0.0 && hours.toDouble() == 0.0 && minutes > 0.0) {
                return minutes.toString() + "分钟前 "
            }
            if (days.toDouble() == 0.0 && hours > 0.0) {
                return hours.toString() + "小时前 "
            }
            if (1.0 <= days && days < 365) {
                return days.toString() + "天前 "
            }
            if (days > 365) {
                return days.toString() + "年前 "
            }
        } catch (e: Exception) {
            println(e.message)
        }

        return time

    }

    /**
     * 返回日差
     * @param time
     * @return
     */
    fun getSentDays(time: String): Long {
        val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        try {
            val d1 = df.parse(TimeUtil.ymdhmsTime)
            val d2 = df.parse(time)
            val diff = d1.time - d2.time//这样得到的差值是微秒级别
            return diff / (1000 * 60 * 60 * 24)
        } catch (e: Exception) {
            println(e.message)
        }

        return 0
    }


    /**
     * 返回日差
     *
     * @param time the time
     * @return long long
     */
    @JvmStatic
    fun getTimeDiff(time: String): Long {
        val df = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
        try {
            val d1 = Date()
            val d2 = df.parse(time)
            val diff = d2.time - d1.time//这样得到的差值是微秒级别
            if (diff > 0) {
                return diff
            }
        } catch (e: Exception) {
            println(e.message)
        }

        return 0
    }

}
