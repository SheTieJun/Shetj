package com.shetj.diyalbume.image

import android.graphics.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import com.jakewharton.rxbinding2.view.RxView
import com.shetj.diyalbume.R
import com.shetj.diyalbume.R.id.btn_commit
import com.shetj.diyalbume.R.id.iv_preview
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_image_test.*

class ImageTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_test)

        RxView.clicks(btn_commit)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
            iv_preview.setImageBitmap(getShareingBitmap(BitmapFactory.decodeResource(resources,R.mipmap.icon_qr),edit_query.text.toString(),20))
        }
    }


    /**
     * get图片添加文字
     * @param imageBitmap 图片
     * @param des 文字
     * @param textSize  文字大小
     * @return
     */
    private fun getShareingBitmap(imageBitmap: Bitmap, des: String, textSize: Int): Bitmap {
        val config = imageBitmap.config
        val sourceBitmapHeight = imageBitmap.height
        val sourceBitmapWidth = imageBitmap.width
        val paint = Paint()
        // 画笔颜色
        paint.color = Color.BLACK

        val textpaint = TextPaint(paint)
        // 文字大小
        textpaint.textSize = textSize.toFloat()
        // 抗锯齿
        textpaint.isAntiAlias = true

        val title_layout = StaticLayout(des, textpaint,
                sourceBitmapWidth, Layout.Alignment.ALIGN_CENTER, 1f, 1f, true)

        val share_bitmap = Bitmap.createBitmap(sourceBitmapWidth, sourceBitmapHeight + title_layout.height, config)
        val canvas = Canvas(share_bitmap)

        canvas.drawColor(Color.WHITE)

        // 绘制图片
        canvas.drawBitmap(imageBitmap, 0f, 0f, paint)
        canvas.translate(0f, sourceBitmapHeight.toFloat())
        title_layout.draw(canvas)
        canvas.translate(0f, title_layout.height.toFloat())
        return share_bitmap
    }
}
