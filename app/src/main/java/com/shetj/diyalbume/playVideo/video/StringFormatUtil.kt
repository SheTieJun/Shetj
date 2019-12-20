package com.shetj.diyalbume.playVideo.video

import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.text.style.CharacterStyle
import android.text.style.ForegroundColorSpan
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.shetj.diyalbume.R
/**
 * @param context
 * @param wholeStr     全部文字
 * @param highlightStr 改变颜色的文字
 * @param color        颜色
 */
class StringFormatUtil(private val mContext: Context,
                       private val wholeStr: String,
                       private val highlightStr: String,
                       @ColorRes private var color: Int) {
    private var spBuilder: SpannableStringBuilder? = null
    private var start = 0
    private var end = 0
    fun fillColor(): StringFormatUtil {
        if (!TextUtils.isEmpty(wholeStr) && !TextUtils.isEmpty(highlightStr) && wholeStr.contains(highlightStr)) { /*
             *  返回highlightStr字符串wholeStr字符串中第一次出现处的索引。
             */
            start = wholeStr.indexOf(highlightStr)
            end = start + highlightStr.length
            spBuilder = SpannableStringBuilder(wholeStr)
            val charaStyle: CharacterStyle = ForegroundColorSpan(ContextCompat.getColor(mContext,color))
            spBuilder!!.setSpan(charaStyle, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        return this
    }

    val result: SpannableStringBuilder
        get() = if (spBuilder != null) {
            spBuilder!!
        } else SpannableStringBuilder(wholeStr)

    companion object {

        @JvmOverloads
        fun getSpannableStringBuilder(context: Context,
                                      content: String,
                                      highlightContent : String,
                                      @ColorRes color: Int = R.color.mdtp_accent_color): SpannableStringBuilder {
            val formatUtil = StringFormatUtil(context, content, highlightContent, color)
            return formatUtil.fillColor().result
        }
    }

}