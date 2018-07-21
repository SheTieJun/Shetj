package com.shetj.diyalbume.animator

import android.animation.ValueAnimator
import android.view.View
import android.view.animation.*
import android.widget.Button
import com.shetj.diyalbume.R
import me.shetj.base.base.BaseModel
import me.shetj.base.base.BasePresenter
import me.shetj.base.base.IView
import me.shetj.base.tools.app.ArmsUtils

class AnimatorPresenter(view :IView) :BasePresenter<BaseModel>(view){

    private var animType = true

    init {

    }

    fun setType() {
        animType = !animType
    }

    fun startAlphaAnim(it: View) {
        if (animType) {
            val animation = AnimationUtils.loadAnimation(view.rxContext, R.anim.anim_alpha)
            it.startAnimation(animation)
        }else {
            val animation2 = AlphaAnimation(1f, 0.2f)
            animation2.duration = 3000
            it.startAnimation(animation2)
        }
    }

    fun startTran(it: View) {
        if (animType) {
            val animation = AnimationUtils.loadAnimation(view.rxContext, R.anim.anim_tran)
            it.startAnimation(animation)
        }else {
            val animation2 =  TranslateAnimation(0f,800f,0f,800f)
            animation2.duration = 3000
            it.startAnimation(animation2)
        }

    }

    fun startRota(it: View) {
        if (animType) {
            val animation = AnimationUtils.loadAnimation(view.rxContext, R.anim.anim_rota)
            it.startAnimation(animation)
        }else {
            val animation2 = RotateAnimation(0f,360f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f)
            animation2.duration = 3000
            it.startAnimation(animation2)
        }
    }

    fun startScale(it: View) {
        if (animType) {
            val animation = AnimationUtils.loadAnimation(view.rxContext, R.anim.anim_scale)
            it.startAnimation(animation)
        }else {
            val animation2 =  ScaleAnimation(0f,3f,0f,3f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f)
            animation2.duration = 3000
            it.startAnimation(animation2)
        }

    }

    fun startValue(it: View) {
        val animation = ValueAnimator.ofInt(0,100)
        animation.duration = 1000
        animation.addUpdateListener { animation ->
            var currentValue = animation?.animatedValue
            ArmsUtils.makeText("currentValue = $currentValue")
            val button = it as Button
            button.text = "currentValue = $currentValue"
        }


    }


}
