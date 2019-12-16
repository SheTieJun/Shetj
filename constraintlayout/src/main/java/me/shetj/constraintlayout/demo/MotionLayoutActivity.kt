package me.shetj.constraintlayout.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.motion.widget.MotionLayout.*
import kotlinx.android.synthetic.main.activity_motion_layout.*
import kotlinx.android.synthetic.main.motion_basic_01.*
import me.shetj.base.kt.start
import me.shetj.constraintlayout.R
import me.shetj.constraintlayout.demo.youtubedemo.YouTubeDemoActivity

class MotionLayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_motion_layout)

        btn_CoordinatorLayout.setOnClickListener {
           start(MotionCoordinatorLayoutActivity::class.java)
        }

        btn_DrawerLayout.setOnClickListener{
            start(MotionDrawerLayoutActivity::class.java)
        }

        btn_fragment.setOnClickListener {
            start(MotionFragmentActivity::class.java)
        }
        //    public static final int DEBUG_SHOW_NONE = 0;
        //    public static final int DEBUG_SHOW_PROGRESS = 1;
        //    public static final int DEBUG_SHOW_PATH = 2;
        motion_1.setDebugMode(DEBUG_SHOW_PATH)

        btn_youTu.setOnClickListener {
            start(YouTubeDemoActivity::class.java)
        }
    }
}
