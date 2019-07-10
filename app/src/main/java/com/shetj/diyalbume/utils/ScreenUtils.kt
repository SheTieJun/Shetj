package com.shetj.diyalbume.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.DisplayCutout
import android.view.View
import android.view.WindowInsets

import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

import timber.log.Timber

object ScreenUtils {

    val VIVO_NOTCH = 0x00000020//是否有刘海
    val VIVO_FILLET = 0x00000008//是否有圆角

    // 是否是小米手机
    val isXiaomi: Boolean
        get() = "Xiaomi" == Build.MANUFACTURER

    /**
     * 判断是否是刘海屏
     *
     * @return
     */
    fun hasNotchScreen(activity: Activity): Boolean {
        return if (getInt("ro.miui.notch", activity) == 1 || hasNotchAtHuawei(activity) || hasNotchAtOPPO(activity)
                || hasNotchAtVivo(activity) || isAndroidP(activity) != null) { //TODO 各种品牌
            true
        } else false
    }

    /**
     * Android P 刘海屏判断
     *
     * @param activity
     * @return
     */
    fun isAndroidP(activity: Activity): DisplayCutout? {
        val decorView = activity.window.decorView
        if (decorView != null && android.os.Build.VERSION.SDK_INT >= 28) {
            val windowInsets = decorView.rootWindowInsets
            if (windowInsets != null)
                return windowInsets.displayCutout
        }
        return null
    }

    /**
     * 小米刘海屏判断.
     *
     * @return 0 if it is not notch ; return 1 means notch
     * @throws IllegalArgumentException if the key exceeds 32 characters
     */
    fun getInt(key: String, activity: Activity): Int {
        var result = 0
        if (isXiaomi) {
            try {
                val classLoader = activity.classLoader
                val SystemProperties = classLoader.loadClass("android.os.SystemProperties")
                //参数类型
                val paramTypes = arrayOfNulls<Class<*>>(2)
                paramTypes[0] = String::class.java
                paramTypes[1] = Int::class.javaPrimitiveType
                val getInt = SystemProperties.getMethod("getInt", *paramTypes)
                //参数
                val params = arrayOfNulls<Any>(2)
                params[0] = key
                params[1] = 0
                result = getInt.invoke(SystemProperties, *params) as Int

            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            } catch (e: NoSuchMethodException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            } catch (e: InvocationTargetException) {
                e.printStackTrace()
            }

        }
        return result
    }

    /**
     * 华为刘海屏判断
     *
     * @return
     */
    fun hasNotchAtHuawei(context: Context): Boolean {
        var ret = false
        try {
            val classLoader = context.classLoader
            val HwNotchSizeUtil = classLoader.loadClass("com.huawei.android.util.HwNotchSizeUtil")
            val get = HwNotchSizeUtil.getMethod("hasNotchInScreen")
            ret = get.invoke(HwNotchSizeUtil) as Boolean
        } catch (e: ClassNotFoundException) {
            Timber.e("hasNotchAtHuawei ClassNotFoundException")
        } catch (e: NoSuchMethodException) {
            Timber.e("hasNotchAtHuawei NoSuchMethodException")
        } catch (e: Exception) {
            Timber.e("hasNotchAtHuawei Exception")
        } finally {
            return ret
        }
    }

    /**
     * VIVO刘海屏判断
     *
     * @return
     */
    fun hasNotchAtVivo(context: Context): Boolean {
        var ret = false
        try {
            val classLoader = context.classLoader
            val FtFeature = classLoader.loadClass("android.util.FtFeature")
            val method = FtFeature.getMethod("isFeatureSupport", Int::class.javaPrimitiveType!!)
            ret = method.invoke(FtFeature, VIVO_NOTCH) as Boolean
        } catch (e: ClassNotFoundException) {
            Timber.e("hasNotchAtVivo ClassNotFoundException")
        } catch (e: NoSuchMethodException) {
            Timber.e("hasNotchAtVivo NoSuchMethodException")
        } catch (e: Exception) {
            Timber.e("hasNotchAtVivo Exception")
        } finally {
            return ret
        }
    }

    /**
     * OPPO刘海屏判断
     *
     * @return
     */
    fun hasNotchAtOPPO(context: Context): Boolean {
        return context.packageManager.hasSystemFeature("com.oppo.feature.screen.heteromorphism")
    }
}
