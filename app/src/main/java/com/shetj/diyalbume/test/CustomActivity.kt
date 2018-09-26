package com.shetj.diyalbume.test

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import com.shetj.diyalbume.R
import com.shetj.diyalbume.view.AlbumImageView
import kotlinx.android.synthetic.main.activity_custom.*
import kotlinx.android.synthetic.main.content_custom.*
import me.shetj.base.tools.app.ArmsUtils

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


}


