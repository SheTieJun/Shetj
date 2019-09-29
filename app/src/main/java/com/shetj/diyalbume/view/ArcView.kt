package com.shetj.diyalbume.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Keep
import me.shetj.base.tools.app.ArmsUtils

@Keep
class ArcView : View {

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

}