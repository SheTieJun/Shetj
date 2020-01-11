package com.shetj.diyalbume.animator

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.animation.ValueAnimator.REVERSE
import android.annotation.SuppressLint
import android.view.View
import android.view.animation.*
import android.widget.Button
import androidx.core.animation.addListener
import androidx.core.animation.doOnPause
import androidx.core.animation.doOnResume
import com.shetj.diyalbume.R
import me.shetj.base.base.BaseModel
import me.shetj.base.base.BasePresenter
import me.shetj.base.base.IView

class AnimatorPresenter(view :IView) :BasePresenter<BaseModel>(view){

    private var animType = false

    init {

    }

    fun setType() {
        animType = !animType
    }

    /**
     * 渐变
     */
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

    /**
     * 平移
     */
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


    /**
     * 旋转
     */
    fun startRota(it: View) {
        if (animType) {
            val animation = AnimationUtils.loadAnimation(view.rxContext, R.anim.anim_rota)
            it.startAnimation(animation)
        }else {
            val animation2 = RotateAnimation(0f,360f,Animation.RELATIVE_TO_SELF,
                    0.5f,Animation.RELATIVE_TO_SELF,0.5f)
            animation2.duration = 3000
            it.startAnimation(animation2)
        }
    }

    /**
     * 大小
     */
    fun startScale(it: View) {
        if (animType) {
            val animation = AnimationUtils.loadAnimation(view.rxContext, R.anim.anim_scale)
            it.startAnimation(animation)
        }else {
            val animation2 =  ScaleAnimation(0f,3f,0f,3f,Animation.RELATIVE_TO_SELF,
                    0.5f,Animation.RELATIVE_TO_SELF,0.5f)
            animation2.duration = 3000
            it.startAnimation(animation2)
        }

    }

    @SuppressLint("SetTextI18n")
            /**
     * 值动画
     */
    fun startValue(view1: View) {
        if (animType) {
            val animation = ValueAnimator.ofInt(0, 100)
            animation.duration = 1000
            animation.addUpdateListener {
                val currentValue = it?.animatedValue
                val button = view1 as Button
                button.text = "currentValue = $currentValue"
            }
            animation.start()
        }else{
            val animation2 = ValueAnimator.ofInt(100, 500)
            animation2.duration = 3000
            animation2.repeatMode = REVERSE
            animation2.addUpdateListener{ animation ->
                view1.layoutParams.width = animation?.animatedValue as Int
                view1.requestLayout()
            }
            animation2.start()
        }
    }

    /**
     * 属性动画 set()/get()
     */
    fun startObject(it: View) {
        if (animType){
            val animator1 = AnimatorInflater.loadAnimator(view.rxContext, R.animator.anim_object)
            animator1.setTarget(it)

            animator1.addListener(onCancel = {},onEnd = {},onRepeat = {},onStart = {})

            animator1.doOnPause {

            }
            animator1.doOnResume {

            }
            animator1.start()

        }else{
            val animator2 = ObjectAnimator.ofFloat(it, "alpha", 1f, 0f, 1f)
            // 动画效果是:常规 - 全透明 - 常规
            animator2.duration = 5000
            animator2.start()
        }
    }

    /**
     * AnimatorSet.play(Animator anim)   ：播放当前动画
     * AnimatorSet.after(long delay)   ：将现有动画延迟x毫秒后执行
     * AnimatorSet.with(Animator anim)   ：将现有动画和传入的动画同时执行
     * AnimatorSet.after(Animator anim)   ：将现有动画插入到传入的动画之后执行
     * AnimatorSet.before(Animator anim) ：  将现有动画插入到传入的动画之前执行
     */
    fun startSet(it: View) {
        if (animType){

            val animator =  AnimatorInflater.loadAnimator(view.rxContext, R.animator.anim_set)
            animator.setTarget(it)
            animator.start()
        }else{
            val translation = ObjectAnimator.ofFloat(it, "translationX", 0f, 300f,0f)
            val rotate = ObjectAnimator.ofFloat(it, "rotation", 0f, 360f,0f)
            val alpha = ObjectAnimator.ofFloat(it, "alpha", 1f, 0f, 1f)
            val animSet =  AnimatorSet()
            animSet.play(translation).with(rotate).before(alpha)
            animSet.duration = 5000
            animSet.start()
        }
    }

    /**
     * view 的属性动画
     */
    fun startViewProperty(it: View) {

        if (animType){
            it.animate().alpha(0f).x(500f).y(500f).duration = 3000

        }else {
            it.animate().alpha(0.5f).x(0f).y(300f).rotation(360f).duration = 3000
        }
    }

}
