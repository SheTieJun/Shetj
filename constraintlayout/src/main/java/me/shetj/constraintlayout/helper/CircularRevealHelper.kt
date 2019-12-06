package me.shetj.constraintlayout.helper

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.ViewAnimationUtils
import androidx.constraintlayout.widget.ConstraintHelper
import androidx.constraintlayout.widget.ConstraintLayout


/**
 * 测量，布局，绘制
 */
class CircularRevealHelper
@JvmOverloads constructor(context: Context, attrs: AttributeSet?=null, defStyleAttr: Int = 0):
        ConstraintHelper(context,attrs,defStyleAttr){


    //updatePostLayout会在 onLayout 之后调用，在这里做动画就可以。
    override fun updatePostLayout(container: ConstraintLayout?) {
        super.updatePostLayout(container)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val views = getViews(container)
            for (view in views) {
                val anim = ViewAnimationUtils.createCircularReveal(view, view.width / 2,
                        view.height / 2, 0f,
                        Math.hypot((view.height / 2).toDouble(), (view.width / 2).toDouble()).toFloat())
                anim.duration = 3000
                anim.start()
            }
        }
    }

    //onLayout 之前
    override fun updatePreLayout(container: ConstraintLayout?) {
        super.updatePreLayout(container)

    }


}