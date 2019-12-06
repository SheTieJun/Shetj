package me.shetj.constraintlayout.demo

import android.animation.ValueAnimator
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_layer.*
import me.shetj.base.base.BaseActivity
import me.shetj.constraintlayout.ConstrainLayoutPresenter
import me.shetj.constraintlayout.R

class LayerActivity : BaseActivity<ConstrainLayoutPresenter>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layer)

    }

    override fun startAnimation() {
    }

    override fun endAnimation() {
    }
    override fun initData() {
        btn_change.setOnClickListener {
            val anim = ValueAnimator.ofFloat(0f, 360f)
            anim.duration = 300
            anim.addUpdateListener { animation ->
                val angle = animation.animatedValue as Float
                layer.rotation = angle
                //放大
                layer.scaleX = 1 + (180 - Math.abs(angle - 180)) / 20f
                layer.scaleY = 1 + (180 - Math.abs(angle - 180)) / 20f
                var shift_x = 500 * Math.sin(Math.toRadians((angle * 5).toDouble())).toFloat()
                var shift_y = 500 * Math.sin(Math.toRadians((angle * 7).toDouble())).toFloat()
                //移动
                layer.translationX = shift_x
                layer.translationY = shift_y

                //通过移动和放大，让用户感觉接近了画面
            }
            anim.duration = 4000
            anim.start()
        }
    }

    override fun initView() {
    }
}
