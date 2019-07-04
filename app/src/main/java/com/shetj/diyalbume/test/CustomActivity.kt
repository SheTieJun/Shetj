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
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        button_image.setOnClickListener { addImage() }
        radar.start()

        RadarImageView.animation =  AnimationUtils.loadAnimation(this,R.anim.rotating_3000)

        RadarImageView.start()
    }

    private fun addImage() {
        val image = AlbumImageView(this)
        image.layoutParams = ViewGroup.LayoutParams(200,200)
        image.setImageResource(R.mipmap.ic_error)
        iFrameRoot.addView(image)

        image.setOnClickListener {
            ArmsUtils.longSnackbar(this,image.json)
        }

    }

    override fun onDestroy() {
//        radar.stop()
        RadarImageView.stop()
        super.onDestroy()
    }
}


