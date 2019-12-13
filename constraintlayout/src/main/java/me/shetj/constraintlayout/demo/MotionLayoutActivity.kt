package me.shetj.constraintlayout.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_motion_layout.*
import me.shetj.base.kt.start
import me.shetj.constraintlayout.R

class MotionLayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_motion_layout)

        btn_CoordinatorLayout.setOnClickListener {
           start(MotionCoordinatorLayoutActivity::class.java)
        }

        btn_DrawerLayout.setOnClickListener{
            start(DrawerLayoutActivity::class.java)
        }

        btn_fragment.setOnClickListener {
            start(FragmentMotionActivity::class.java)
        }
    }
}
