package com.shetj.diyalbume.view

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import com.shetj.diyalbume.R
import me.shetj.base.tools.app.ArmsUtils


//测量 绘制
class CircleProgressView
@JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
        BaseCustomView(context, attrs, defStyle) {

    private var valueAnimator: ValueAnimator? = null
    private val DEFAULT_COLOR = Color.YELLOW
    private var DEF_WITH: Float = ArmsUtils.dip2px(10f).toFloat()

    private var progressColor: Int = DEFAULT_COLOR
    private var backProgressWith: Float = DEF_WITH
    private var backProgressColor: Int = DEFAULT_COLOR
    private var progressWith: Float = backProgressWith + ArmsUtils.dip2px(4f)

    private var max = 100
    private var progress = 0

    val path = Path()

    private val mProgressPaint = Paint().apply {
        isAntiAlias = true
        color = Color.YELLOW
        strokeWidth = progressWith
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND //
        isDither = true //防止抖动
        maskFilter = BlurMaskFilter(5f, BlurMaskFilter.Blur.SOLID)  //上左：内发光(INNER)
//        上右：外发光(SOLID)
//        下左：内外发光(NORMAL)
//        下右：仅显示发光效果(OUTER),该模式下仅会显示发光效果，会把原图像中除了发光部分，全部变为透明
    }

    private val mBgProgressPaint = Paint().apply {
        isAntiAlias = true
        color = Color.DKGRAY
        strokeWidth = backProgressWith
        isDither = true //防止抖动
        style = Paint.Style.STROKE
    }

    private val mTextPaint = Paint().apply {
        isAntiAlias = true
        color = Color.YELLOW
        textSize = ArmsUtils.dip2px(20f).toFloat()
    }

    init {
        if (attrs != null) {
            context.obtainStyledAttributes(attrs, R.styleable.CircleProgressView).apply {
                backProgressColor = getColor(R.styleable.CircleProgressView_backProgressColor, Color.DKGRAY)
                backProgressWith = getDimension(R.styleable.CircleProgressView_backProgressWith, DEF_WITH)
                progressColor = getColor(R.styleable.CircleProgressView_progressColor, Color.YELLOW)
                progressWith = getDimension(R.styleable.CircleProgressView_progressWith, DEF_WITH)
                max = getInt(R.styleable.CircleProgressView_max, 100)
                progress = getInt(R.styleable.CircleProgressView_progressSize, 0)
                initConfig()
            }.recycle()
        }
    }

    private fun initConfig() {
        mProgressPaint.color = progressColor
        mProgressPaint.strokeWidth = progressWith
        mBgProgressPaint.color = backProgressColor
        mBgProgressPaint.strokeWidth = backProgressWith

    }

    //      LinearGradient gradient;//线型渐变色 一把只能在绘制横竖直线 或者矩形图形的时候效果好。
    //      RadialGradient gradient;//圆形渐变色 就像树了轮廓一样 一环套一环的着色 下一环的半径是比上一环半径的一个radius值
    //      BitmapShader gradient;  位图型渐变色，其实就相当于把位图当颜色。
    //      SweepGradient gradient;//角度渐变色
    //     ComposeShader gradient;//组合渐变色


    fun setProgress(progress: Int) {
        if (valueAnimator?.isRunning == true) {
            valueAnimator?.cancel()
        }
        startProgressAnim(this.progress, progress)
    }

    private fun startProgressAnim(start: Int, end: Int) {
        post {
            valueAnimator = ValueAnimator.ofInt(start, end)?.apply {
                addUpdateListener {
                    progress = it.animatedValue as Int
                    postInvalidate()
                }
                interpolator = AccelerateDecelerateInterpolator()
                duration = 600
            }
            valueAnimator?.start()
        }
    }

    //测量
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val fl = progressWith / 2 + 5f
        //首先画背景圆
        canvas?.drawCircle(width / 2f, height / 2f, (width) / 2f - fl, mBgProgressPaint)
        //再画上层
        val oval = RectF(fl, fl, width - fl, height - fl)
        val sweepGradient = LinearGradient(fl, fl, width - fl, height - fl, Color.parseColor("#FEDE47"), Color.parseColor("#FEBB22"), Shader.TileMode.REPEAT)

//        val sweepGradient = SweepGradient(width/2f,height/2f,Color.parseColor("#FEDE47"), Color.parseColor("#FEBB22"))
        mProgressPaint.shader = sweepGradient
        canvas?.drawArc(oval, -90f, progress / max.toFloat() * 360, false, mProgressPaint)

        val widthCentre = width / 2
        val heightCentre = height / 2
        val textWidth = mTextPaint.measureText("Level 2")
        canvas?.drawText("Level 2", widthCentre - textWidth / 2, heightCentre - (mTextPaint.ascent() + mTextPaint.descent()) / 2, mTextPaint)
    }


}