package com.shetj.diyalbume.utils

import android.app.Activity
import android.view.View

//调暗状态栏和通知栏
fun Activity.set1(){

    window?.decorView?.apply {
        systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE
    }

}

fun Activity.clean(){
     window?.decorView?.apply {
        // Calling setSystemUiVisibility() with a value of 0 clears
        systemUiVisibility = 0
    }
}

//隐藏导航栏和状态栏：
fun Activity.full(){
    
//    // Hide the status bar.
//    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
//    // Remember that you should never show the action bar if the
//    // status bar is hidden, so hide that too if necessary.
//    actionBar?.hide()

    //隐藏导航栏
    window.decorView.apply {
        // Hide both the navigation bar and the status bar.
        // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
        // a general rule, you should design your app to hide the status bar whenever you
        // hide the navigation bar.
        systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
    }

}


fun Activity.change(visibile:((Boolean) ->Unit ) ?= null){
    window.decorView.setOnSystemUiVisibilityChangeListener { visibility ->
        // Note that system bars will only be "visible" if none of the
        // LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
        if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
            // The system bars are visible. Make any desired
            // adjustments to your UI, such as showing the action bar or
            // other navigational controls.
            visibile?.invoke(true)
        } else {
            // The system bars are NOT visible. Make any desired
            // adjustments to your UI, such as hiding the action bar or
            // other navigational controls.
            visibile?.invoke(false)
        }
    }

}