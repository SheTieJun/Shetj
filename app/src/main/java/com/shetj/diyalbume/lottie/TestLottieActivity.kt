package com.shetj.diyalbume.lottie

import android.animation.Animator
import android.animation.ValueAnimator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.shetj.diyalbume.R
import kotlinx.android.synthetic.main.activity_test_lottie.*

class TestLottieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_lottie)
        LoadingDialog2.showLoading(this,true)
        animation_view.addAnimatorListener(object :Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {

            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {

            }

        })
        animation_view.setOnClickListener{
            val animator = ValueAnimator.ofFloat(0f, 1f)
            animator.addUpdateListener { animation -> animation_view.progress = animation.animatedValue as Float }
            animator.duration = 5000
            animator.start()
        }
    }
}
