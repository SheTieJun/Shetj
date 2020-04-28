package com.shetj.diyalbume.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Keep
import me.shetj.base.tools.app.ArmsUtils


@Keep
class ArcView : View {

    private var mRadius: Int = 0
    private var mHeight: Int = 0
    private var mWidth: Int = 0
    private lateinit var mPaint: Paint
    private var mArcHeight: Int = 0 //圆弧高度
    private lateinit var arcPath: Path   //圆弧


    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    private fun init() {
        mPaint = Paint().apply {
            flags = Paint.ANTI_ALIAS_FLAG
            isAntiAlias = true
            color = Color.RED
            isFilterBitmap = true
            style = Paint.Style.FILL
        }
        mArcHeight = ArmsUtils.dip2px(18f)
        arcPath = Path()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val mArcRectF = RectF(0f,0f,width.toFloat(),height.toFloat())
//        arcPath.moveTo(0f,0f)
//        arcPath.quadTo((width/2).toFloat(), mArcHeight.toFloat()*2,width.toFloat(),0f)
//        arcPath.lineTo(width.toFloat(),height.toFloat())
//        arcPath.lineTo(0f,height.toFloat())
//        arcPath.close()
        canvas.drawArc(mArcRectF, 0f, 180f, true, mPaint)
//        canvas.drawPath(arcPath, mPaint)
    }

//    //test
//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
//        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
//        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
//        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
//        mWidth = if (widthMode == MeasureSpec.EXACTLY) {
//            widthSize
//        } else {
//            widthSize
//        }
//        if (heightMode == MeasureSpec.EXACTLY) {
//            mHeight = heightSize
//        } else {
//            mHeight = defaultHeight
//            if (heightMode == MeasureSpec.AT_MOST) {
//                mHeight = heightSize.coerceAtMost(mHeight)
//            }
//        }
//        mRadius = Math.min(mWidth, mHeight) / 2
//        setMeasuredDimension(mWidth, mHeight)
//    }
}