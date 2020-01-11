package com.shetj.diyalbume.utils

import android.app.Activity
import android.view.ViewGroup
import androidx.annotation.Keep
import com.irozon.sneaker.Sneaker
import com.irozon.sneaker.interfaces.OnSneakerClickListener
import com.irozon.sneaker.interfaces.OnSneakerDismissListener
import com.shetj.diyalbume.R

/**
 * **@packageName：** me.shetj.base.tools.app<br></br>
 * **@author：** shetj<br></br>
 * **@createTime：** 2018/2/28<br></br>
 * **@company：**<br></br>
 * **@email：** 375105540@qq.com<br></br>
 * **@describe** 顶部信息提示<br></br>
 */

@Keep
object SneakerUtils {

    fun sneakError(activity: Activity, title: String, message: String) {
        Sneaker.with(activity)
                .setTitle(title)
                .setMessage(message)
                .sneakError()
    }

    fun sneakSuccess(activity: Activity, title: String, message: String) {
        Sneaker.with(activity)
                .setTitle(title)
                .setMessage(message)
                .sneakSuccess()
    }

    fun sneakWarning(activity: Activity, title: String, message: String) {
        Sneaker.with(activity)
                .setTitle(title)
                .setMessage(message)
                .sneakWarning()
    }

    fun sneakCus(activity: Activity,
                 title: String,
                 message: String,
                 color: Int,
                 bgColor: Int,
                 listener: OnSneakerClickListener,
                 dismissListener: OnSneakerDismissListener) {
        Sneaker.with(activity)
                .setTitle(title, color)
                // Title and title color
                .setMessage(message, color)
                // Message and message color
                .setDuration(4000)
                // Time duration to show
                .autoHide(true)
                // Auto hide Sneaker view
                .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                // Height of the Sneaker layout
                .setIcon(R.drawable.ic_error, R.color.white, false)
                // Icon, icon tint color and circular icon view
                .setOnSneakerClickListener(listener)
                // Click listener for Sneaker
                .setOnSneakerDismissListener(dismissListener)
                // Dismiss listener for Sneaker. - Version 1.0.2
                .sneak(bgColor)
        // Sneak with background color
    }
}
