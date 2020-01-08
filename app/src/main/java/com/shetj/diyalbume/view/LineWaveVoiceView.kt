package com.shetj.diyalbume.view

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.animation.ValueAnimator.REVERSE
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.shetj.diyalbume.R
import me.shetj.base.tools.app.ArmsUtils
import timber.log.Timber
import java.util.*
import java.util.concurrent.Executors

class LineWaveVoiceView :View {

    private var evenNumberAnimator: ValueAnimator? =null
    private val DEFAULT_TEXT :String = "请录音"
    private val LINE_WIDTH = 5f
    private val UPDATE_INTERVAL_TIME = 500L//500ms一次动画

    private var minLineHeight = 4f
    private var maxLineHeight = 12f

    private var oddNumber = 0f //基数
    private var oddNumber2 = 0f //基数
    private var evenNumber = 0f //偶数
    private var evenNumber2 = 0f //偶数

    private var text = DEFAULT_TEXT
    private var updateSpeed :Long = UPDATE_INTERVAL_TIME
    private var lineColor :Int = Color.GREEN
    private var textColor :Int = Color.BLACK
    private var lineWidth :Float = 15f
    private var textSize :Float = ArmsUtils.dip2px(15f).toFloat()

    private val paint = Paint()
    private val rectRight = RectF()//右边波纹矩形的数据，10个矩形复用一个rectF
    private val rectLeft = RectF()//左边波纹矩形的数据


    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs){
        attrs?.let {
            initView(attrs,context)
        }
    }
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        initView(attrs,context)
    }

    private fun initView(attrs: AttributeSet, context: Context) {
        context.obtainStyledAttributes(attrs, R.styleable.LineWaveVoiceView)
                .apply {
                    lineColor = getColor(R.styleable.LineWaveVoiceView_voiceLineColor, ContextCompat.getColor(context, R.color.colorAccent))
                    textColor = getColor(R.styleable.LineWaveVoiceView_voiceTextColor, ContextCompat.getColor(context, R.color.colorAccent))
                    lineWidth = getDimension(R.styleable.LineWaveVoiceView_voiceLineWith, LINE_WIDTH)
                    textSize = getDimension(R.styleable.LineWaveVoiceView_voiceTextSize, 42f)
                    updateSpeed = getInt(R.styleable.LineWaveVoiceView_updateSpeed, 500).toLong()

                    recycle()

                }
        evenNumberAnimator = ObjectAnimator.ofFloat(minLineHeight, maxLineHeight)
                .apply {
                    addUpdateListener {
                        val changeSize = it.animatedValue as Float
                        oddNumber = changeSize*0.9f
                        oddNumber2 = changeSize*0.65f
                        evenNumber = maxLineHeight - changeSize + 2f
                        evenNumber2 = maxLineHeight - changeSize + 4f
                        postInvalidate()
                    }
                    repeatMode = REVERSE
                    repeatCount = -1
                    duration = updateSpeed
                }
    }

    @Synchronized
    fun startRecord() {
        evenNumberAnimator?.takeIf {
            !it.isRunning
        }?.apply {
            oddNumber = minLineHeight //基数
            evenNumber =  maxLineHeight -2f  //偶数
            oddNumber2 = minLineHeight
            evenNumber2 =  maxLineHeight  //偶数
            evenNumberAnimator?.start()
        }
    }

    @Synchronized
    fun stopRecord() {
        evenNumberAnimator?.takeIf {
            it.isRunning
        }?.apply {
            oddNumber = 0f //基数
            evenNumber = 0f //偶数
            oddNumber2 = 0f
            evenNumber2 = 0f
            evenNumberAnimator?.cancel()
        }
    }




    fun setText(text: String) {
        this.text = text
        postInvalidate()
    }

    fun setTextColor(@ColorInt color: Int){
        this.textColor = color
        postInvalidate()
    }

    fun setTextSize(dp :Float){
        this.textSize =   ArmsUtils.dip2px(dp).toFloat()
        postInvalidate()
    }


    fun setLineColor(@ColorInt color: Int){
        this.lineColor = color
        postInvalidate()
    }


    fun setLineWidth(dp: Float){
        this.lineWidth = ArmsUtils.dip2px(dp).toFloat()
        postInvalidate()
    }

    fun updateSpeed(updateSpeed : Long){
        this.updateSpeed = updateSpeed
        evenNumberAnimator?.duration = updateSpeed
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            //获取实际宽高的一半
            val widthCentre = width / 2
            val heightCentre = height / 2
            paint.strokeWidth = 0f
            paint.color = textColor
            paint.textSize = textSize
            val textWidth = paint.measureText(text)
            //paint.ascent() 上边
            //paint.descent() 下边
            canvas.drawText(text, widthCentre - textWidth / 2, heightCentre - (paint.ascent() + paint.descent()) / 2, paint)
            //设置颜色
            paint.color = lineColor
            //填充内部
            paint.style = Paint.Style.FILL
            //设置抗锯齿
            paint.isAntiAlias = true
            for (i in 0 until 7) {
                val number  = when(i % 7 ){
                    0 -> oddNumber2
                    1 -> evenNumber
                    2 -> oddNumber
                    3 -> evenNumber2
                    4 -> oddNumber
                    5 -> evenNumber
                    6 -> oddNumber2
                    else -> evenNumber2
                }

                rectRight.left = widthCentre.toFloat() + textWidth / 2 + (1 + 3 * i) * lineWidth
                rectRight.top = heightCentre - lineWidth * number / 2
                rectRight.right = widthCentre.toFloat() + textWidth / 2 + (2 + 3 * i) * lineWidth
                rectRight.bottom = heightCentre + lineWidth * number/ 2

                //左边矩形
                rectLeft.left = widthCentre.toFloat() - textWidth / 2 - (2 + 3 * i) * lineWidth
                rectLeft.top = heightCentre - number * lineWidth / 2
                rectLeft.right = widthCentre.toFloat() - textWidth / 2 - (1 + 3 * i) * lineWidth
                rectLeft.bottom = heightCentre + number * lineWidth / 2

                canvas.drawRoundRect(rectRight, 6f, 6f, paint)
                canvas.drawRoundRect(rectLeft, 6f, 6f, paint)
            }
        }

    }
}