package me.shetj.constraintlayout.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_fragment_motion.*
import me.shetj.constraintlayout.R
import me.shetj.constraintlayout.fragment.BlankFragment
import me.shetj.constraintlayout.fragment.SecondFragment
import kotlin.math.abs

class MotionFragmentActivity : AppCompatActivity(), View.OnClickListener, MotionLayout.TransitionListener {

    private var lastProgress = 0f
    private var fragment : Fragment? = null
    private var last : Float = 0f

    override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
        if (p3 - lastProgress > 0) {
            // from start to end
            val atEnd = abs(p3 - 1f) < 0.1f
            if (atEnd && fragment is BlankFragment) {
                val transaction = supportFragmentManager.beginTransaction()
                transaction
                        .setCustomAnimations(R.animator.show, 0)
                fragment = SecondFragment.newInstance().also {
                    transaction
                            .setCustomAnimations(R.animator.show, 0)
                            .replace(R.id.container, it)
                            .commitNow()
                }
            }
        } else {
            // from end to start
            val atStart = p3 < 0.9f
            if (atStart && fragment is SecondFragment) {
                val transaction = supportFragmentManager.beginTransaction()
                transaction
                        .setCustomAnimations(0, R.animator.hide)
                fragment = BlankFragment.newInstance().also {
                    transaction
                            .replace(R.id.container, it)
                            .commitNow()
                }
            }
        }
        lastProgress = p3
    }

    override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
    }

    override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
    }

    override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_motion)
        if (savedInstanceState == null) {
            fragment = BlankFragment.newInstance().also {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.container, it)
                        .commitNow()
            }
        }
        motionLayout.setTransitionListener(this)
    }

    override fun onClick(view: View?) {
//        if (view?.id == R.id.toggle) {
//            val transaction = supportFragmentManager.beginTransaction()
//            fragment = if (fragment == null || fragment is BlankFragment) {
//                last = 1f
//                transaction
//                        .setCustomAnimations(R.animator.show, 0)
//                SecondFragment.newInstance()
//            } else {
//                transaction
//                        .setCustomAnimations(0, R.animator.hide)
//                BlankFragment.newInstance()
//            }.also {
//                transaction
//                        .replace(R.id.container, it)
//                        .commitNow()
//            }
//        }
    }
}