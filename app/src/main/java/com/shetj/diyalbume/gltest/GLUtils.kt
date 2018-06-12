package com.shetj.diyalbume.gltest

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.os.Build

/**
 *
 * <b>@packageName：</b> com.shetj.diyalbume.gltest<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2018/6/12 0012<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */
object GLUtils{
    fun checkSupported(activity: Activity) : Boolean {

        val activityManager = activity.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val configurationInfo = activityManager .deviceConfigurationInfo
        val  supportsEs2 =
                configurationInfo.reqGlEsVersion >= 0x20000
                        || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
                        && (Build.FINGERPRINT.startsWith("generic")
                        || Build.FINGERPRINT.startsWith("unknown")
                        || Build.MODEL.contains("google_sdk")
                        || Build.MODEL.contains("Emulator")
                        || Build.MODEL.contains("Android SDK built for x86")))
        return supportsEs2
    }
}