package com.shetj.diyalbume.gesture

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.shetj.diyalbume.R

/**
 * 手势
 */
@Route(path = "/shetj/GestureActivity")
class GestureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gesture)
    }
}
