package com.shetj.diyalbume.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import me.shetj.base.tools.app.ArmsUtils
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt


class EdgedTextView  @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr){
    private var bgPath: Path = Path()
    private var arcPath: Path = Path()
    private var text = "顶置顶置"
    private val paint: Paint = Paint().apply {
        isAntiAlias = true
        color = Color.BLACK
        style = Paint.Style.FILL
        textSize = ArmsUtils.dip2px(14f).toFloat()
    }
    private val bounds = Rect()

    init {
        //获取包含文本的矩形
        paint.getTextBounds(text, 0, text.length, bounds)

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = measureWidth(widthMeasureSpec)
        val height = measureHeight(heightMeasureSpec)
        setMeasuredDimension(width, height)
    }


    fun measureWidth(measureSpec: Int):Int{
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        var width :Int = 0
        if (specMode == MeasureSpec.AT_MOST){
            //获取到具体的宽高
            width =  getWH()+ paddingLeft + paddingRight
        }else if (specMode == MeasureSpec.EXACTLY){
            width = specSize
        }
        return width
    }


    fun measureHeight(measureSpec: Int):Int{
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        var height :Int = 0
        if (specMode == MeasureSpec.AT_MOST){
            //获取到具体的宽高
            height = getWH() + paddingLeft + paddingRight
        }else if (specMode == MeasureSpec.EXACTLY){
            height = specSize
        }
        return height
    }

    /**
     * 最后一定是正方形
     * 边 = 开方 (w平方/2) +开方 ((2h平方)/2)
     */
    private fun getWH(): Int {
        //Math.sqrt(9);//比如9是要平方的数
        //Math.pow(b,2);//表示b的平方
        return (sqrt(bounds.width().toDouble().pow(2.toDouble()) /2) +
                sqrt( (2 * bounds.height().toDouble()).pow(2.toDouble()) /2)).roundToInt()
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //设置背景背景
        bgPath.reset()
        bgPath.moveTo(0f,height.toFloat())
        bgPath.lineTo(width.toFloat(),0f)
        bgPath.lineTo(0f,0f)
        bgPath.close()
        paint.color = Color.RED
        canvas?.drawPath(bgPath,paint)


        arcPath.reset()
        arcPath.moveTo(0f,height.toFloat())
        arcPath.lineTo(width.toFloat(),0f)
        // hOffset 和 vOffset。它们是文字相对于 Path 的水平偏移量和竖直偏移量，利用它们可以调整文字的位置。
        // 例如你设置  hOffset 为 5， vOffset 为 10，文字就会右移 5 像素和下移 10 像素。
        paint.color = Color.WHITE
        canvas?.drawTextOnPath(text,arcPath,bounds.height().toFloat(),-5f,paint)



    }
}