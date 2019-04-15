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
import com.alibaba.android.arouter.facade.annotation.Route
import com.jakewharton.rxbinding2.view.RxView
import com.shetj.diyalbume.R
import com.transitionseverywhere.ChangeBounds
import com.transitionseverywhere.ChangeClipBounds
import com.transitionseverywhere.Scene
import com.transitionseverywhere.TransitionManager
import kotlinx.android.synthetic.main.activity_transtion.*
import me.shetj.base.base.BaseActivity
import me.shetj.transition.transitionexample.Main3Activity
@Route(path = "/shetj/TranstionActivity")
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
    /*
    ChangeBounds	检测View的位置边界创建移动和缩放动画(关注布局边界的变化)
ChangeTransform	检测View的scale和rotation创建缩放和旋转动画(关注scale和ratation的变化)
ChangeClipBounds	检测View的剪切区域的位置边界，和ChangeBounds类似。不过ChangeBounds针对的是view而ChangeClipBounds针对的是view的剪切区域setClipBound(Rect rect) 中的rect(关注的是setClipBounds(Rect rect)rect的变化)
ChangeImageTransform	检测ImageView的ScaleType，并创建相应动画(关注的是ImageView的scaleType)
Fade	根据View的visibility状态的的不同创建淡入淡动画,调整的是透明度(关注的是View的visibility的状态)
Slide	根据View的visibility状态的的不同创建滑动动画(关注的是View的visibility的状态)
Explode	根据View的visibility状态的的不同创建分解动画(关注的是View的visibility的状态)
AutoTransition	默认动画，ChangeBounds、Fade动画的集合

     */

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
        val scene2 = Scene.getSceneForLayout(sceneRoot, scene2_layout, this)
        TransitionManager.go(scene1)
    }
}
