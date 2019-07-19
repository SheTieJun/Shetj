package com.shetj.diyalbume.utils

import android.widget.TextView


/**
 * 文字加粗
 */
internal fun TextView.testBold(isBold: Boolean){
    paint.isFakeBoldText = isBold
}