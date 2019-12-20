package com.shetj.diyalbume.image

import android.graphics.*
import android.os.Build
import android.os.Bundle
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.jakewharton.rxbinding3.view.clicks
import com.shetj.diyalbume.R
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_image_test.*

@Route(path = "/shetj/ImageTestActivity")
class ImageTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_test)

        btn_commit.clicks()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    iv_preview.setImageBitmap(getShareBitmap(BitmapFactory.decodeResource(resources,R.mipmap.icon_qr),edit_query.text.toString(),20))
                }
    }


    /**
     * get图片添加文字
     * @param imageBitmap 图片
     * @param des 文字
     * @param textSize  文字大小
     * @return
     */
    private fun getShareBitmap(imageBitmap: Bitmap, des: String, textSize: Int): Bitmap {
        val config = imageBitmap.config
        val sourceBitmapHeight = imageBitmap.height
        val sourceBitmapWidth = imageBitmap.width
        val paint = Paint()
        // 画笔颜色
        paint.color = Color.BLACK
        val textPaint = TextPaint(paint)
        // 文字大小
        textPaint.textSize = textSize.toFloat()
        // 抗锯齿
        textPaint.isAntiAlias = true
        val staticLayout : StaticLayout
        staticLayout = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StaticLayout.Builder
                    .obtain(des,0,des.length,textPaint,sourceBitmapWidth)
                    .apply {
                        setAlignment(Layout.Alignment.ALIGN_CENTER)
                        setIncludePad(true)
                    }.build()
        }else {
            StaticLayout(des, textPaint,
                    sourceBitmapWidth, Layout.Alignment.ALIGN_CENTER, 1f, 1f, true)
        }

        val shareBitmap = Bitmap.createBitmap(sourceBitmapWidth, sourceBitmapHeight + staticLayout.height, config)
        val canvas = Canvas(shareBitmap)

        canvas.drawColor(Color.WHITE)
        // 绘制图片
        canvas.drawBitmap(imageBitmap, 0f, 0f, paint)
        // 玩下移动
        canvas.translate(0f, sourceBitmapHeight.toFloat()-staticLayout.height)
        staticLayout.draw(canvas)
        return shareBitmap
    }
}
