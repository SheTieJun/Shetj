package com.shetj.diyalbume.lottie

import android.animation.Animator
import android.animation.ValueAnimator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.SeekBar
import com.alibaba.android.arouter.facade.annotation.Route
import com.jakewharton.rxbinding2.view.RxView
import com.shetj.diyalbume.R
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_test_lottie.*
import me.shetj.base.base.BasePresenter
import me.shetj.base.base.BaseSwipeBackActivity
import me.shetj.base.view.LoadingDialog

@Route(path = "/shetj/TestLottieActivity")
class TestLottieActivity : BaseSwipeBackActivity<BasePresenter<*>>() {
    override fun initData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_lottie)
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
            animator.addUpdateListener { animation ->
                animation_view.progress = animation.animatedValue as Float
                seekBar.progress = (animation.animatedValue as Float *100).toInt()
             }
            animator.duration = 5000
            animator.start()
        }
        seekBar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                animation_view.progress = progress *1f/ 100
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        RxView.clicks(loading).observeOn(AndroidSchedulers.mainThread()).subscribe {
            LoadingDialog.showLoading(this,true)
        }
        RxView.clicks(lottie).observeOn(AndroidSchedulers.mainThread()).subscribe {
            LottieDialog.showLoading(this,true)
        }
    }
}
