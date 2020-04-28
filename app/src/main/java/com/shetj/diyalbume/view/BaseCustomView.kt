package com.shetj.diyalbume.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import me.shetj.base.tools.app.ArmsUtils
import me.shetj.base.tools.app.TimberUtil
import me.shetj.base.tools.file.ConstUtils
import me.shetj.base.tools.time.DateUtils
import timber.log.Timber


open class BaseCustomView
@JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
        View(context, attrs, defStyle){
    protected val defaultSize = ArmsUtils.dip2px(10f)

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        Timber.i("onLayout($changed,$left,$top,$right,$bottom)")
        super.onLayout(changed, left, top, right, bottom)
    }

    //测量
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        Timber.i("onMeasure($widthMeasureSpec,$heightMeasureSpec)")
        val width = measureWidth(widthMeasureSpec,defaultSize)
        val height = measureHeight(heightMeasureSpec,defaultSize)
        setMeasuredDimension(width, height)
    }

    override fun onDetachedFromWindow() {
        Timber.i("onDetachedFromWindow ${DateUtils.timeString}")
        super.onDetachedFromWindow()
    }

    override fun onAttachedToWindow() {
        Timber.i("onAttachedToWindow ${DateUtils.timeString}")
        super.onAttachedToWindow()
    }

    override fun onAnimationStart() {
        Timber.i("onAnimationStart")
        super.onAnimationStart()
    }

    override fun onAnimationEnd() {
        Timber.i("onAnimationEnd")
        super.onAnimationEnd()
    }

    override fun setZ(z: Float) {
        super.setZ(z)
    }

    fun measureHeight(measureSpec: Int, defaultSize: Int): Int {
        var result = 0
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize
        } else {
            result = defaultSize +paddingTop +paddingBottom
            if (specMode == MeasureSpec.AT_MOST){
                result = result.coerceAtMost(specSize)
            }
        }
        result = result.coerceAtLeast(suggestedMinimumHeight)
        return result
    }

    fun measureWidth(measureSpec: Int, defaultSize: Int): Int {
        var result = 0
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize
        } else {
            result = defaultSize + paddingLeft + paddingRight
            if (specMode == MeasureSpec.AT_MOST) {
                result = result.coerceAtMost(specSize)
            }
        }
        result = result.coerceAtLeast(suggestedMinimumWidth)
        return result
    }

    /**
     * 绘制文字在中心
     */
    fun drawnTextCenter(canvas: Canvas,text:String,paint: Paint){
        val measureText = paint.measureText(text)
        val x = width/2 - measureText / 2
        val y = height/2 - getPaintTextYCenter(paint)
        canvas.drawText(text,x,y,paint)
    }

    fun getPaintTextYCenter(paint: Paint):Float{
      return  (paint.ascent() + paint.descent()) / 2
    }
}