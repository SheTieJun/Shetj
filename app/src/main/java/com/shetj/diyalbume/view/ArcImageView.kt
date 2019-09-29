package com.shetj.diyalbume.view

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.shetj.diyalbume.R

/**
 * 弧度ImageView
 */
class ArcImageView : AppCompatImageView {

    private var mArcHeight: Int = 0 //圆弧高度
    private lateinit var mPaint: Paint  //画笔
    private lateinit var arcPath:Path   //圆弧
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
        // Load attributes
        val a = context.obtainStyledAttributes(
                attrs, R.styleable.ArcImageView, defStyle, 0)
        if(a.hasValue(R.styleable.ArcImageView_ArcImage)){
            a.getDrawable(R.styleable.ArcImageView_ArcImage)
        }
        mArcHeight = a.getDimensionPixelSize(R.styleable.ArcImageView_ArcHeight, 0)
        a.recycle()

        arcPath = Path()
        mPaint = Paint().apply {
            flags = Paint.ANTI_ALIAS_FLAG
            isAntiAlias = true
            color = Color.WHITE
            //设定是否使用图像抖动处理，会使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
            isDither = true
            style = Paint.Style.FILL
        }
    }

    override fun onDraw(canvas: Canvas) {
        arcPath.reset()
        arcPath.moveTo(0f,0f)
        arcPath.lineTo(0f,height.toFloat()-2*mArcHeight)
        arcPath.quadTo((width/2).toFloat(),height.toFloat(),width.toFloat(),height.toFloat()-2*mArcHeight)
        arcPath.lineTo(width.toFloat(),0f)
        arcPath.close()
        super.onDraw(canvas)
    }

}

