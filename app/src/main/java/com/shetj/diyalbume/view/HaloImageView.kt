package com.shetj.diyalbume.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.shetj.diyalbume.R

class HaloImageView : TextView {

    private var valueAnimator: ValueAnimator? =null
    private lateinit var mPaint: Paint
    private lateinit var mBGPaint: Paint
    private var haloColor: Int = Color.RED
    private var haloSize: Float = 0f
    private var haloBackgroundColor:  Int = Color.RED

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
         context.obtainStyledAttributes(
                attrs, R.styleable.HaloImageView, defStyle, 0).apply {
            haloColor = getColor(
                    R.styleable.HaloImageView_haloColor,
                    ContextCompat.getColor(context,R.color.app_color_theme_1))
            haloBackgroundColor = getColor(
                    R.styleable.HaloImageView_haloBackgroundColor,
                    ContextCompat.getColor(context,R.color.app_color_theme_1))
            haloSize = getDimension(
                    R.styleable.HaloImageView_haloSize,
                    10f)
            if (haloSize < 10){
                haloSize = 10f
            }
            recycle()
        }
        mPaint = Paint().apply {
            flags = Paint.ANTI_ALIAS_FLAG
            isAntiAlias = true
            color = haloColor
            style = Paint.Style.FILL
            maskFilter = BlurMaskFilter(haloSize, BlurMaskFilter.Blur.OUTER)
        }
        mBGPaint =Paint().apply {
            flags = Paint.ANTI_ALIAS_FLAG
            isAntiAlias = true
            color = haloBackgroundColor
            style = Paint.Style.FILL
        }
        setLayerType(View.LAYER_TYPE_SOFTWARE,null)
    }


    fun startAnimo(){
        valueAnimator = ValueAnimator.ofFloat(8f, haloSize).apply {
            duration = 600
            repeatMode =ValueAnimator.REVERSE
            repeatCount = -1
             addUpdateListener {
                val currentValue = it?.animatedValue
                setHaloSize(currentValue as Float)
            }
            start()
        }

    }


    private fun setHaloSize(size:Float){
//        haloSize = size + 5f
        mPaint.maskFilter =  BlurMaskFilter(size, BlurMaskFilter.Blur.OUTER)
        invalidate()
    }

    fun stopAnim(){
        valueAnimator?.cancel()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawCircle((width/2).toFloat(), (height/2).toFloat(), (width/2).toFloat()-haloSize, mPaint)
        canvas.drawCircle((width/2).toFloat(), (height/2).toFloat(), (width/2).toFloat()-haloSize, mBGPaint)
        super.onDraw(canvas)
    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startAnimo()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopAnim()
    }
}