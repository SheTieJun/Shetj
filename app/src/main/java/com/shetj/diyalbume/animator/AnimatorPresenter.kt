package com.shetj.diyalbume.animator

import android.view.View
import android.view.animation.*
import com.shetj.diyalbume.R
import me.shetj.base.base.BaseModel
import me.shetj.base.base.BasePresenter
import me.shetj.base.base.IView

class AnimatorPresenter(view :IView) :BasePresenter<BaseModel>(view){

    private var animType = true

    init {

    }

    fun setType() {
        animType = !animType
    }

    fun startAlphaAnim(it: View) {
        if (animType) {
            //方法 -1
            // 步骤1:创建 需要设置动画的 视图View
            val animation = AnimationUtils.loadAnimation(view.rxContext, R.anim.anim_alpha)
            // 步骤2:创建 动画对象 并传入设置的动画效果xml文件
            it.startAnimation(animation)
        }else {
            //方法 -2
            val animation2 = AlphaAnimation(1f, 0.2f)
            // 步骤2：创建透明度动画的对象 & 设置动画效果：透明度动画对应的Animation子类为AlphaAnimation
            // 1. fromAlpha:动画开始时视图的透明度(取值范围: -1 ~ 1)
            // 2. toAlpha:动画结束时视图的透明度(取值范围: -1 ~ 1)
            animation2.duration = 3000
            // 固定属性的设置都是在其属性前加“set”，如setDuration（）
            it.startAnimation(animation2)
        }
    }

    fun startTran(it: View) {
        if (animType) {
            //方法 -1
            // 步骤1:创建 需要设置动画的 视图View
            val animation = AnimationUtils.loadAnimation(view.rxContext, R.anim.anim_tran)
            // 步骤2:创建 动画对象 并传入设置的动画效果xml文件
            it.startAnimation(animation)
        }else {
            //方法 -2
            val animation2 =  TranslateAnimation(0f,800f,0f,800f)
            // 步骤2：创建透明度动画的对象 & 设置动画效果：透明度动画对应的Animation子类为AlphaAnimation
            // 1. fromAlpha:动画开始时视图的透明度(取值范围: -1 ~ 1)
            // 2. toAlpha:动画结束时视图的透明度(取值范围: -1 ~ 1)
            animation2.duration = 3000
            // 固定属性的设置都是在其属性前加“set”，如setDuration（）
            it.startAnimation(animation2)
        }

    }

    fun startRota(it: View) {
        if (animType) {
            //方法 -1
            // 步骤1:创建 需要设置动画的 视图View
            val animation = AnimationUtils.loadAnimation(view.rxContext, R.anim.anim_rota)
            // 步骤2:创建 动画对象 并传入设置的动画效果xml文件
            it.startAnimation(animation)
        }else {

            val animation2 = RotateAnimation(0f,360f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
            animation2.duration = 3000
            it.startAnimation(animation2)
        }


    }

    fun startScale(it: View) {
        if (animType) {
            //方法 -1
            // 步骤1:创建 需要设置动画的 视图View
            val animation = AnimationUtils.loadAnimation(view.rxContext, R.anim.anim_scale)
            // 步骤2:创建 动画对象 并传入设置的动画效果xml文件
            it.startAnimation(animation)
        }else {
            //方法 -2
            val animation2 =  ScaleAnimation(0f,3f,0f,3f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
            // 步骤2：创建透明度动画的对象 & 设置动画效果：透明度动画对应的Animation子类为AlphaAnimation
            // 1. fromAlpha:动画开始时视图的透明度(取值范围: -1 ~ 1)
            // 2. toAlpha:动画结束时视图的透明度(取值范围: -1 ~ 1)
            animation2.duration = 3000
            // 固定属性的设置都是在其属性前加“set”，如setDuration（）
            it.startAnimation(animation2)
        }

    }


}
