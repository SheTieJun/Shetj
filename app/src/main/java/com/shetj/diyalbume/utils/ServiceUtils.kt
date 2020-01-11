package com.shetj.diyalbume.utils

import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.text.TextUtils
import androidx.annotation.RequiresApi
import java.util.*

object ServiceUtils {

    /**
     * 判断服务是否已经正在运行
     *
     * @param mContext  上下文对象
     * @param className Service类的全路径类名 "包名+类名" 如com.demo.test.MyService
     * @return
     */
    fun isServiceRunning(mContext: Context, className: String): Boolean {
        val myManager = mContext
                .applicationContext.getSystemService(
                Context.ACTIVITY_SERVICE) as ActivityManager
        val runningService = myManager
                .getRunningServices(30) as ArrayList<ActivityManager.RunningServiceInfo>
        for (i in runningService.indices) {
            if (runningService[i].service.className.toString() == className) {
                return true
            }
        }
        return false
    }


    /**
     * 判断某个界面是否在前台
     *
     * @param context
     * @param className 某个界面名称
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    fun isActivityForeground(context: Context?, className: String): Boolean {
        if (context == null || TextUtils.isEmpty(className)) {
            return false
        }

        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val list = am.getRunningTasks(1)
        if (list != null && list.size > 0) {
            val cpn = list[0].topActivity
            if (className == cpn?.className) {
                return true
            }
        }

        return false
    }

}