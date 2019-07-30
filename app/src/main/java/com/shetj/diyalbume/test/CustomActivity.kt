package com.shetj.diyalbume.test

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.alibaba.android.arouter.facade.annotation.Route
import com.shetj.diyalbume.R
import com.shetj.diyalbume.view.AlbumImageView
import kotlinx.android.synthetic.main.activity_custom.*
import kotlinx.android.synthetic.main.content_custom.*
import me.shetj.base.tools.app.ArmsUtils
@Route(path = "/shetj/CustomActivity")
class CustomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom)

        button_image.setOnClickListener { addImage() }

    }

    private fun addImage() {
        val image = AlbumImageView(this)
        image.layoutParams = ViewGroup.LayoutParams(200,200)
        image.setImageResource(R.mipmap.shetj_logo)

        image.setOnClickListener {
            ArmsUtils.makeText(image.json)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}


