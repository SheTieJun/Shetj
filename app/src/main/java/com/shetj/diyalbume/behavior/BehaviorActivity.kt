package com.shetj.diyalbume.behavior

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.shetj.diyalbume.R
import kotlinx.android.synthetic.main.activity_behavior.*
import me.shetj.base.tools.app.ArmsUtils
import timber.log.Timber

/**
 * [BottomSheetBehavior]
 */
class BehaviorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_behavior)
        val behavior  =  BottomSheetBehavior.from<LinearLayout>(tab_layout)
        behavior.peekHeight = ArmsUtils.dip2px(50f)

        behavior.isHideable = true

//        app:behavior_peekHeight	BottomSheet收缩时展示的高度
//        app:behavior_hideable	滑动是否可隐藏（一般是向下滑动）

        behavior.setBottomSheetCallback(object :BottomSheetBehavior.BottomSheetCallback(){
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                Timber.i("behavior.slideOffset =$slideOffset ")
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                Timber.i("behavior.state =$newState ")
            }
        })

        btn_bottom_sheet_control.setOnClickListener {
            //STATE_COLLAPSED	收缩状态 (setPeekHeight)相互作用
            //STATE_EXPANDED	展开状态
            //STATE_DRAGGING	正在拖动状态
            //STATE_HIDDEN	    隐藏状态

           when(behavior.state){
               BottomSheetBehavior.STATE_HIDDEN ->{
                   behavior.state = BottomSheetBehavior.STATE_EXPANDED
               }
               BottomSheetBehavior.STATE_EXPANDED ->{
                   behavior.state = BottomSheetBehavior.STATE_HIDDEN
               }
               BottomSheetBehavior.STATE_COLLAPSED ->{
                   behavior.state = BottomSheetBehavior.STATE_HIDDEN
               }
           }
        }
    }
}
