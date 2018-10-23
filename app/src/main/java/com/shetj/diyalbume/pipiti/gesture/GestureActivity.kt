package com.shetj.diyalbume.pipiti.gesture

import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.shetj.diyalbume.R
import kotlinx.android.synthetic.main.activity_gesture.*
import me.shetj.base.base.BaseActivity
import me.shetj.base.base.BasePresenter

/**
 * 手势
 */
@Route(path = "/shetj/GestureActivity")
class GestureActivity : BaseActivity<BasePresenter<*>>() {


    private lateinit var gestureDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gesture)
        initView()
        initData()
    }
    var x = 0f
    var y = 0f
    override fun initView() {

        gestureDetector = GestureDetector(rxContext,object : GestureDetector.SimpleOnGestureListener(){
            override fun onShowPress(e: MotionEvent?) {
            }

            override fun onSingleTapUp(e: MotionEvent?): Boolean {
                iv_icon.visibility = View.GONE
                return false
            }

            override fun onDown(e: MotionEvent?): Boolean {
                iv_icon.visibility = View.VISIBLE
                e?.let {
                    x = e.x
                    y = e.y
                    iv_icon.x = x  - iv_icon.width/2
                    iv_icon.y = y - iv_icon.height/2
                }

                return false
            }

            override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {


                return false
            }

            override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
                iv_icon.x= iv_icon.x - distanceX
                iv_icon.y = iv_icon.y - distanceY
                return false
            }

            override fun onLongPress(e: MotionEvent?) {
            }


        })
    }

    override fun initData() {

    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        gestureDetector.onTouchEvent(ev)
        if (ev?.action == MotionEvent.ACTION_UP){
            iv_icon.visibility = View.GONE
        }
        return super.dispatchTouchEvent(ev)
    }


}
