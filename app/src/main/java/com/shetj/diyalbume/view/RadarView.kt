package com.shetj.diyalbume.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.SweepGradient
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View

import com.shetj.diyalbume.R

import java.util.ArrayList

class RadarView : View {

    //默认的主题颜色
    private val DEFAULT_COLOR = Color.parseColor("#91D7F4")

    // 圆圈和交叉线的颜色
    private var mCircleColor = DEFAULT_COLOR
    //圆圈的数量 不能小于1
    private var mCircleNum = 3
    //扫描的颜色 RadarView会对这个颜色做渐变透明处理
    private var mSweepColor = DEFAULT_COLOR
    //水滴的颜色
    private var mRaindropColor = DEFAULT_COLOR
    //水滴的数量 这里表示的是水滴最多能同时出现的数量。因为水滴是随机产生的，数量是不确定的
    private var mRaindropNum = 4
    //是否显示交叉线
    private var isShowCross = true
    //是否显示水滴
    private var isShowRaindrop = true
    //扫描的转速，表示几秒转一圈
    private var mSpeed = 3.0f
    //水滴显示和消失的速度
    private var mFlicker = 3.0f

    private var mCirclePaint: Paint? = null// 圆的画笔
    private var mSweepPaint: Paint? = null //扫描效果的画笔
    private var mRaindropPaint: Paint? = null// 水滴的画笔

    private var mDegrees: Float = 0.toFloat() //扫描时的扫描旋转角度。
    private var isScanning = false//是否扫描

    //保存水滴数据
    private val mRaindrops = ArrayList<Raindrop>()

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        getAttrs(context, attrs)
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        getAttrs(context, attrs)
        init()
    }

    /**
     * 获取自定义属性值
     *
     * @param context
     * @param attrs
     */
    private fun getAttrs(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.RadarView)
            mCircleColor = mTypedArray.getColor(R.styleable.RadarView_circleColor, DEFAULT_COLOR)
            mCircleNum = mTypedArray.getInt(R.styleable.RadarView_circleNum, mCircleNum)
            if (mCircleNum < 1) {
                mCircleNum = 3
            }
            mSweepColor = mTypedArray.getColor(R.styleable.RadarView_sweepColor, DEFAULT_COLOR)
            mRaindropColor = mTypedArray.getColor(R.styleable.RadarView_raindropColor, DEFAULT_COLOR)
            mRaindropNum = mTypedArray.getInt(R.styleable.RadarView_raindropNum, mRaindropNum)
            isShowCross = mTypedArray.getBoolean(R.styleable.RadarView_showCross, true)
            isShowRaindrop = mTypedArray.getBoolean(R.styleable.RadarView_showRaindrop, true)
            mSpeed = mTypedArray.getFloat(R.styleable.RadarView_speed, mSpeed)
            if (mSpeed <= 0) {
                mSpeed = 3f
            }
            mFlicker = mTypedArray.getFloat(R.styleable.RadarView_flicker, mFlicker)
            if (mFlicker <= 0) {
                mFlicker = 3f
            }
            mTypedArray.recycle()
        }
    }

    /**
     * 初始化
     */
    private fun init() {
        // 初始化画笔
        mCirclePaint = Paint()
        mCirclePaint!!.color = mCircleColor
        mCirclePaint!!.strokeWidth = 1f
        mCirclePaint!!.style = Paint.Style.STROKE
        mCirclePaint!!.isAntiAlias = true

        mRaindropPaint = Paint()
        mRaindropPaint!!.style = Paint.Style.FILL
        mRaindropPaint!!.isAntiAlias = true

        mSweepPaint = Paint()
        mSweepPaint!!.isAntiAlias = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //设置宽高,默认200dp
        val defaultSize = dp2px(context, 200f)
        setMeasuredDimension(measureWidth(widthMeasureSpec, defaultSize),
                measureHeight(heightMeasureSpec, defaultSize))
    }

    /**
     * 测量宽
     *
     * @param measureSpec
     * @param defaultSize
     * @return
     */
    private fun measureWidth(measureSpec: Int, defaultSize: Int): Int {
        var result = 0
        val specMode = View.MeasureSpec.getMode(measureSpec)
        val specSize = View.MeasureSpec.getSize(measureSpec)

        if (specMode == View.MeasureSpec.EXACTLY) {
            result = specSize
        } else {
            result = defaultSize + paddingLeft + paddingRight
            if (specMode == View.MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize)
            }
        }
        result = Math.max(result, suggestedMinimumWidth)
        return result
    }

    /**
     * 测量高
     *
     * @param measureSpec
     * @param defaultSize
     * @return
     */
    private fun measureHeight(measureSpec: Int, defaultSize: Int): Int {
        var result = 0
        val specMode = View.MeasureSpec.getMode(measureSpec)
        val specSize = View.MeasureSpec.getSize(measureSpec)

        if (specMode == View.MeasureSpec.EXACTLY) {
            result = specSize
        } else {
            result = defaultSize + paddingTop + paddingBottom
            if (specMode == View.MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize)
            }
        }
        result = Math.max(result, suggestedMinimumHeight)
        return result
    }

    override fun onDraw(canvas: Canvas) {

        //计算圆的半径
        val width = width - paddingLeft - paddingRight
        val height = height - paddingTop - paddingBottom
        val radius = Math.min(width, height) / 2

        //计算圆的圆心
        val cx = paddingLeft + (getWidth() - paddingLeft - paddingRight) / 2
        val cy = paddingTop + (getHeight() - paddingTop - paddingBottom) / 2

        drawCircle(canvas, cx, cy, radius)

        if (isShowCross) {
            drawCross(canvas, cx, cy, radius)
        }

        //正在扫描
        if (isScanning) {
            if (isShowRaindrop) {
                drawRaindrop(canvas, cx, cy, radius)
            }
            drawSweep(canvas, cx, cy, radius)
            //计算雷达扫描的旋转角度
            mDegrees = (mDegrees + 360f / mSpeed / 60f) % 360

            //触发View重新绘制，通过不断的绘制实现View的扫描动画效果
            invalidate()
        }
    }

    /**
     * 画圆
     */
    private fun drawCircle(canvas: Canvas, cx: Int, cy: Int, radius: Int) {
        //画mCircleNum个半径不等的圆圈。
        for (i in 0 until mCircleNum) {
            canvas.drawCircle(cx.toFloat(), cy.toFloat(), (radius - radius / mCircleNum * i).toFloat(), mCirclePaint!!)
        }
    }

    /**
     * 画交叉线
     */
    private fun drawCross(canvas: Canvas, cx: Int, cy: Int, radius: Int) {
        //水平线
        canvas.drawLine((cx - radius).toFloat(), cy.toFloat(), (cx + radius).toFloat(), cy.toFloat(), mCirclePaint!!)

        //垂直线
        canvas.drawLine(cx.toFloat(), (cy - radius).toFloat(), cx.toFloat(), (cy + radius).toFloat(), mCirclePaint!!)
    }

    /**
     * 生成水滴。水滴的生成是随机的，并不是每次调用都会生成一个水滴。
     */
    private fun generateRaindrop(cx: Int, cy: Int, radius: Int) {

        // 最多只能同时存在mRaindropNum个水滴。
        if (mRaindrops.size < mRaindropNum) {
            // 随机一个20以内的数字，如果这个数字刚好是0，就生成一个水滴。
            // 用于控制水滴生成的概率。
            val probability = (Math.random() * 20).toInt() == 0
            if (probability) {
                var x = 0
                var y = 0
                val xOffset = (Math.random() * (radius - 20)).toInt()
                val yOffset = (Math.random() * Math.sqrt(1.0 * (radius - 20).toDouble() * (radius - 20).toDouble() - xOffset * xOffset).toInt()).toInt()

                if ((Math.random() * 2).toInt() == 0) {
                    x = cx - xOffset
                } else {
                    x = cx + xOffset
                }

                if ((Math.random() * 2).toInt() == 0) {
                    y = cy - yOffset
                } else {
                    y = cy + yOffset
                }

                mRaindrops.add(Raindrop(x, y, 0f, mRaindropColor))
            }
        }
    }

    /**
     * 删除水滴
     */
    private fun removeRaindrop() {
        val iterator = mRaindrops.iterator()

        while (iterator.hasNext()) {
            val raindrop = iterator.next()
            if (raindrop.radius > 20 || raindrop.alpha < 0) {
                iterator.remove()
            }
        }
    }

    /**
     * 画雨点(就是在扫描的过程中随机出现的点)。
     */
    private fun drawRaindrop(canvas: Canvas, cx: Int, cy: Int, radius: Int) {
        generateRaindrop(cx, cy, radius)
        for (raindrop in mRaindrops) {
            mRaindropPaint!!.color = raindrop.changeAlpha()
            canvas.drawCircle(raindrop.x.toFloat(), raindrop.y.toFloat(), raindrop.radius, mRaindropPaint!!)
            //水滴的扩散和透明的渐变效果
            raindrop.radius += 1.0f * 20 / 60f / mFlicker
            raindrop.alpha -= 1.0f * 255 / 60f / mFlicker
        }
        removeRaindrop()
    }

    /**
     * 画扫描效果
     */
    private fun drawSweep(canvas: Canvas, cx: Int, cy: Int, radius: Int) {
        //扇形的透明的渐变效果
        val sweepGradient = SweepGradient(cx.toFloat(), cy.toFloat(),
                intArrayOf(Color.TRANSPARENT, changeAlpha(mSweepColor, 0), changeAlpha(mSweepColor, 168), changeAlpha(mSweepColor, 255), changeAlpha(mSweepColor, 255)), floatArrayOf(0.0f, 0.6f, 0.99f, 0.998f, 1f))
        mSweepPaint!!.shader = sweepGradient
        //先旋转画布，再绘制扫描的颜色渲染，实现扫描时的旋转效果。
        canvas.rotate(-90 + mDegrees, cx.toFloat(), cy.toFloat())
        canvas.drawCircle(cx.toFloat(), cy.toFloat(), radius.toFloat(), mSweepPaint!!)
    }

    /**
     * 开始扫描
     */
    fun start() {
        if (!isScanning) {
            isScanning = true
            invalidate()
        }
    }

    /**
     * 停止扫描
     */
    fun stop() {
        if (isScanning) {
            isScanning = false
            mRaindrops.clear()
            mDegrees = 0.0f
        }
    }

    /**
     * 水滴数据类
     */
    private class Raindrop(internal var x: Int, internal var y: Int, internal var radius: Float, internal var color: Int) {
        internal var alpha = 255f

        /**
         * 获取改变透明度后的颜色值
         *
         * @return
         */
        fun changeAlpha(): Int {
            return RadarView.changeAlpha(color, alpha.toInt())
        }

    }

    companion object {

        /**
         * dp转px
         */
        private fun dp2px(context: Context, dpVal: Float): Int {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    dpVal, context.resources.displayMetrics).toInt()
        }

        /**
         * 改变颜色的透明度
         *
         * @param color
         * @param alpha
         * @return
         */
        private fun changeAlpha(color: Int, alpha: Int): Int {
            val red = Color.red(color)
            val green = Color.green(color)
            val blue = Color.blue(color)
            return Color.argb(alpha, red, green, blue)
        }
    }
}