package com.shetj.diyalbume.animator

import android.content.Intent
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.annotation.RequiresApi
import android.transition.Explode
import android.transition.Slide
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.jakewharton.rxbinding2.view.RxView
import com.shetj.diyalbume.R
import com.transitionseverywhere.ChangeBounds
import com.transitionseverywhere.ChangeClipBounds
import com.transitionseverywhere.Scene
import com.transitionseverywhere.TransitionManager
import kotlinx.android.synthetic.main.activity_transtion.*
import me.shetj.base.base.BaseActivity
import me.shetj.transition.transitionexample.Main3Activity

class TranstionActivity : BaseActivity<AnimatorPresenter>() {

    private var isScene1 = false

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transtion)

        val slide=  Slide()
        slide.duration = 500
        slide.slideEdge = Gravity.BOTTOM
        window.enterTransition = slide
        window.reenterTransition = Explode().setDuration(600)


        initView()
        initData()
    }

    override fun initView() {
        val sceneForLayout1 = Scene.getSceneForLayout(iRoot, R.layout.scen1, rxContext)
        val sceneForLayout2 = Scene.getSceneForLayout(iRoot, R.layout.scen2, rxContext)
        TransitionManager.go(sceneForLayout1)
        RxView.clicks(btn_change).subscribe {
            isScene1 = !isScene1
            TransitionManager.go(if(isScene1)sceneForLayout2 else sceneForLayout1, ChangeBounds())

        }


        val inflate = LayoutInflater.from(this).inflate(R.layout.scen_clip_1, null)
        val inflate2 = LayoutInflater.from(this).inflate(R.layout.scen_clip_2, null)

        val imageView = inflate.findViewById(R.id.cutegirl) as ImageView
        val imageView2 = inflate2.findViewById(R.id.cutegirl) as ImageView

        imageView.clipBounds = Rect(0, 0, 200, 200)
        imageView2.clipBounds = Rect(200, 200, 400, 400)

        val scene1 = Scene(iRoot, inflate)
        val scene2 = Scene(iRoot, inflate2)

        RxView.clicks(btn_changeClipBounds).subscribe {
            isScene1 = !isScene1
            TransitionManager.go(if(isScene1)scene1 else scene2, ChangeClipBounds())

        }

        RxView.clicks(btn_example)
                .subscribe {
                    startActivity(Intent(rxContext, Main3Activity::class.java))
                }
    }

    override fun initData() {

    }

    protected fun initScene(@IdRes rootView: Int, @LayoutRes scene1_layout: Int, @LayoutRes scene2_layout: Int) {
        val sceneRoot = findViewById<View>(rootView) as ViewGroup
        val scene1 = Scene.getSceneForLayout(sceneRoot, scene1_layout, this)
//        val scene2 = Scene.getSceneForLayout(sceneRoot, scene2_layout, this)
        TransitionManager.go(scene1)
    }
}
