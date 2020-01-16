package com.shetj.diyalbume.view

import android.content.Context
import android.graphics.*
import android.graphics.Paint.ANTI_ALIAS_FLAG
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
        flags = ANTI_ALIAS_FLAG
    }
    private val bounds = Rect()

    init {
        //获取包含文本的矩形
        paint.getTextBounds(text, 0, text.length, bounds)
        setLayerType(LAYER_TYPE_SOFTWARE,null)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = measureWidth(widthMeasureSpec)
        val height = measureHeight(heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

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

    /**
     * 复制代码
    1.PorterDuff.Mode.CLEAR：所绘制不会提交到画布上。
    2.PorterDuff.Mode.SRC：显示上层绘制图片
    3.PorterDuff.Mode.DST：显示下层绘制图片
    4.PorterDuff.Mode.SRC_OVER：正常绘制显示，上下层绘制叠盖。
    5.PorterDuff.Mode.DST_OVER：上下层都显示。下层居上显示。
    6.PorterDuff.Mode.SRC_IN：取两层绘制交集。显示上层。
    7.PorterDuff.Mode.DST_IN：取两层绘制交集。显示下层。
    8.PorterDuff.Mode.SRC_OUT：上层绘制非交集部分。
    9.PorterDuff.Mode.DST_OUT：取下层绘制非交集部分。
    10.PorterDuff.Mode.SRC_ATOP：取下层非交集部分与上层交集部分
    11.PorterDuff.Mode.DST_ATOP：取上层非交集部分与下层交集部分
    12.PorterDuff.Mode.XOR：异或：去除两图层交集部分
    13.PorterDuff.Mode.DARKEN：取两图层全部区域，交集部分颜色加深
    14.PorterDuff.Mode.LIGHTEN：取两图层全部，点亮交集部分颜色
    15.PorterDuff.Mode.MULTIPLY：取两图层交集部分叠加后颜色
    16.PorterDuff.Mode.SCREEN：取两图层全部区域，交集部分变为透明色

    黄色的圆是DST下层，先进行绘制；
    蓝色的矩形是SRC上层，后进行绘制
     */

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.color = Color.RED
        //设置背景背景
        bgPath.reset()
        bgPath.moveTo(0f,height.toFloat())
        bgPath.lineTo(width.toFloat(),0f)
        bgPath.lineTo(0f,0f)
        bgPath.close()
        canvas?.drawPath(bgPath,paint)

        arcPath.reset()
        arcPath.moveTo(0f,height.toFloat())
        arcPath.lineTo(width.toFloat(),0f)
        // hOffset 和 vOffset。它们是文字相对于 Path 的水平偏移量和竖直偏移量，利用它们可以调整文字的位置。
        // 例如你设置  hOffset 为 5， vOffset 为 10，文字就会右移 5 像素和下移 10 像素。
        paint.color = Color.WHITE
        canvas?.drawTextOnPath(text,arcPath,bounds.height().toFloat(),-5f,paint)


        //还原画布

    }
}