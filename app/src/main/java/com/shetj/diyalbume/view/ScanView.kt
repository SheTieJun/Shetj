package com.shetj.diyalbume.view

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.shetj.diyalbume.R
import me.shetj.base.tools.app.ArmsUtils

/**
 * 扫描二维码view
 */
class ScanView : View {
    private var mPaint: Paint? = null
    private var transparentPaint //画扫描框
            : Paint? = null
    // 扫描框的宽高
    private val frameWidth = ArmsUtils.dip2px(220f).toFloat()
    private val frameHeight = ArmsUtils.dip2px(220f).toFloat()
    //扫描框 距离顶部距离
    private val frameMarginTop = ArmsUtils.dip2px(120f).toFloat()
    // 扫描框边角颜色
    private val innerCornerColor = Color.parseColor("#45DDDD")
    // 扫描框边角长度
    private val innerCornerLength = ArmsUtils.dip2px(25f)
    // 扫描框边角宽度
    private val innerCornerWidth = ArmsUtils.dip2px(4f)
    private var lineTop = ArmsUtils.dip2px(4f).toFloat()
    //线的高度
    private val drawOvalHeight = ArmsUtils.dip2px(1f)
    private var animator: ValueAnimator? = null

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        mPaint = Paint()
        // 去锯齿
        mPaint!!.isAntiAlias = true
        // 防抖动
        mPaint!!.isDither = true
        // 图像过滤
        mPaint!!.isFilterBitmap = true
        //透明的画笔
        transparentPaint = Paint()
        transparentPaint!!.color = Color.TRANSPARENT
        transparentPaint!!.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        animator = ObjectAnimator.ofFloat(frameMarginTop + lineTop, frameMarginTop + frameHeight)
        animator!!.addUpdateListener { animation: ValueAnimator ->
            lineTop = animation.animatedValue as Float
            invalidate()
        }
        animator!!.repeatCount = -1
        animator!!.repeatMode = ValueAnimator.REVERSE
        animator!!.duration = 1500
        startScan()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val width = width
        val height = height
        //将绘制操作保存到新的图层，因为图像合成是很昂贵的操作，将用到硬件加速，这里将图像合成的处理放到离屏缓存中进行
        val saveCount = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), mPaint)

        //首先画背景，背景半透明
        //主要用到的技术是PorterDuffXfermode的PorterDuff.Mode.XOR模式
        mPaint!!.color = ContextCompat.getColor(context, R.color.halfTransparent)
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), mPaint!!)
        val left = (width - frameWidth) / 2
        canvas.drawRect(left, frameMarginTop, left + frameWidth, frameMarginTop + frameHeight, transparentPaint!!)

        //画四个角
        mPaint!!.color = innerCornerColor
        //左上角
        canvas.drawRect(left, frameMarginTop, left + innerCornerWidth, frameMarginTop + innerCornerLength, mPaint!!)
        canvas.drawRect(left, frameMarginTop, left + innerCornerLength, frameMarginTop + innerCornerWidth, mPaint!!)
        //右上角
        canvas.drawRect(left + frameWidth - innerCornerWidth, frameMarginTop, left + frameWidth, frameMarginTop + innerCornerLength, mPaint!!)
        canvas.drawRect(left + frameWidth - innerCornerLength, frameMarginTop, left + frameWidth, frameMarginTop + innerCornerWidth, mPaint!!)
        //左下角
        canvas.drawRect(left, frameMarginTop + frameHeight - innerCornerLength, left + innerCornerWidth, frameMarginTop + frameHeight, mPaint!!)
        canvas.drawRect(left, frameMarginTop + frameHeight - innerCornerWidth, left + innerCornerLength, frameMarginTop + frameWidth, mPaint!!)
        //右下角
        canvas.drawRect(left + frameWidth - innerCornerWidth, frameMarginTop + frameHeight - innerCornerLength, left + frameWidth, frameMarginTop + frameHeight, mPaint!!)
        canvas.drawRect(left + frameWidth - innerCornerLength, frameMarginTop + frameHeight - innerCornerWidth, left + frameWidth, frameMarginTop + frameWidth, mPaint!!)

        //在画线 同时 有一个属性动画进行 线的高度变化
        mPaint!!.maskFilter = BlurMaskFilter(1.5f, BlurMaskFilter.Blur.SOLID)
        canvas.drawOval(left + innerCornerWidth, lineTop, left + frameWidth - innerCornerWidth, lineTop + drawOvalHeight, mPaint!!)
        mPaint!!.maskFilter = null

        //保存图册
        canvas.restoreToCount(saveCount)
    }

    private fun startScan() {
        animator!!.start()
    }
}