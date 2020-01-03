package com.shetj.diyalbume.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import me.shetj.base.tools.app.ArmsUtils

class EdgedTextView  @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr){
    private var arcPath: Path = Path()
    private var text = "顶置"

    private val paint: Paint = Paint().apply {
        isAntiAlias = true
        color = Color.BLACK
        textSize = ArmsUtils.dip2px(14f).toFloat()
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val textWidth = paint.measureText(text)
        arcPath.lineTo(0.toFloat(),height.toFloat())
        arcPath.moveTo(textWidth,0.toFloat())
        canvas?.drawTextOnPath(text,arcPath,0f,0f,paint)
    }
}