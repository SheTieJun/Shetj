package com.shetj.diyalbume.view

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageView
import com.shetj.diyalbume.R

/**
 * **@packageName：** com.shetj.diyalbume.view<br></br>
 * **@author：** shetj<br></br>
 * **@createTime：** 2017/12/19<br></br>
 * **@company：**<br></br>
 * **@email：** 375105540@qq.com<br></br>
 * **@describe**<br></br>
 */
@RequiresApi(Build.VERSION_CODES.HONEYCOMB)
class AlbumImageView : AppCompatImageView {


    private val controlDrawable: Drawable? = null//移动图片
    private val deleteDrawable: Drawable? = null//删除图片


    private var mPaint: Paint? = null

    private val mPath = Path()//外框

    private val mFrameColor = DEFAULT_FRAME_COLOR
    private var isDrag = false

    private var lastX: Float = 0.toFloat()
    private var lastY: Float = 0.toFloat()

    private val isNotDrag: Boolean
        get() = !isDrag

    val json: String
        get() = "{$x,$y}"


    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        obtainStyledAttributes(attrs)
    }

    private fun obtainStyledAttributes(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.AlbumImageView)

    }

    private fun init() {
        mPaint = Paint()
        mPaint!!.color = mFrameColor//设置画笔颜色
        mPaint!!.isAntiAlias = true//设置抗锯齿
        mPaint!!.style = Paint.Style.STROKE
        val pathEffect = DashPathEffect(floatArrayOf(15f, 10f), 5f)
        mPaint!!.pathEffect = pathEffect
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPath.reset()
        mPath.moveTo(0f, 0f)
        mPath.lineTo(width.toFloat(), 0f)
        mPath.lineTo(width.toFloat(), height.toFloat())
        mPath.lineTo(0f, height.toFloat())
        mPath.lineTo(0f, 0f)
        canvas.drawPath(mPath, mPaint!!)

    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.rawX
        val y = event.rawY
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                isDrag = false
                lastX = x
                lastY = y
            }

            MotionEvent.ACTION_MOVE -> {
                isDrag = true
                val dx = x - lastX
                val dy = y - lastY
                val x1 = getX() + dx
                val y1 = getY() + dy
                setX(x1)
                setY(y1)
                lastX = x
                lastY = y
            }
            MotionEvent.ACTION_UP -> {
            }
            else -> {
            }
        }
        return !isNotDrag || super.onTouchEvent(event)
    }

    companion object {

        val DEFAULT_FRAME_COLOR = Color.RED
    }
}
