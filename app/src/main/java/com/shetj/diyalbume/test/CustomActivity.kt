package com.shetj.diyalbume.test

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.customview.widget.ViewDragHelper
import com.alibaba.android.arouter.facade.annotation.Route
import com.shetj.diyalbume.R
import com.shetj.diyalbume.view.AlbumImageView
import kotlinx.android.synthetic.main.activity_custom.*
import kotlinx.android.synthetic.main.content_custom.*
import me.shetj.base.tools.app.ArmsUtils
import timber.log.Timber

@Route(path = "/shetj/CustomActivity")
class CustomActivity : AppCompatActivity() {

    private var mViewDragHelper: ViewDragHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom)

        button_image.setOnClickListener { addImage() }
        mViewDragHelper =    ViewDragHelper.create(root,object :ViewDragHelper.Callback(){
            override fun tryCaptureView(child: View, pointerId: Int): Boolean {

                return true
            }

            //子空间竖直滑动
            override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {

                //return 0 表示屏蔽
                //不能滑出顶部
                return Math.max(top, 0)
//                return super.clampViewPositionVertical(child, top, dy)
            }


            //水平滑动
            override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
                return left
            }

            /**
             * 子控件位置改变时触发（包括X和Y轴方向）
             *
             * @param left position.
             * @param top  position.
             * @param dx   change in X position from the last call.
             * @param dy   change in Y position from the last call.
             */
            override fun onViewPositionChanged(changedView: View, left: Int, top: Int, dx: Int, dy: Int) {
                Timber.i("left: $left, top: $top, dx:$dx , dy: $dy")
            }

            //释放
            override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
                super.onViewReleased(releasedChild, xvel, yvel)
            }

            //点击或者拖动
            override fun getViewHorizontalDragRange(child: View): Int {
                return super.getViewHorizontalDragRange(child)
            }

            fun computeScroll() {
                if (mViewDragHelper != null && mViewDragHelper!!.continueSettling(true)) {
                }
            }
        })
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        mViewDragHelper?.processTouchEvent(event)
        return true
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


