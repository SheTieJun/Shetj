package com.shetj.diyalbume.behavior

import android.view.MotionEvent
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout

class CustomBehvior : CoordinatorLayout.Behavior<View>() {

    /*是否拦截触摸事件*/
    override fun onInterceptTouchEvent(parent: CoordinatorLayout, child: View, ev: MotionEvent): Boolean {
        return super.onInterceptTouchEvent(parent, child, ev)
    }

    /*确定使用Behavior的View要依赖的View的类型*/
    override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        //告知监听的dependency是什么  寻找被观察View
        return super.layoutDependsOn(parent, child, dependency)
    }

    /*处理触摸事件*/
    override fun onTouchEvent(parent: CoordinatorLayout, child: View, ev: MotionEvent): Boolean {
        return super.onTouchEvent(parent, child, ev)
    }

    /*当被依赖的View状态改变时回调*/
    override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        //被观察View变化的时候回调用的方法
        return super.onDependentViewChanged(parent, child, dependency)
    }

    /*当被依赖的View移除时回调*/
    override fun onDependentViewRemoved(parent: CoordinatorLayout, child: View, dependency: View) {
        super.onDependentViewRemoved(parent, child, dependency)
    }
    /*测量使用Behavior的View尺寸*/
    override fun onMeasureChild(parent: CoordinatorLayout, child: View, parentWidthMeasureSpec: Int, widthUsed: Int, parentHeightMeasureSpec: Int, heightUsed: Int): Boolean {
        return super.onMeasureChild(parent, child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed)
    }
    /*嵌套滑动开始（ACTION_DOWN），确定Behavior是否要监听此次事件*/
    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type)
    }
    /*：嵌套滑动结束（ACTION_UP或ACTION_CANCEL）*/
    override fun onStopNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, type: Int) {
        super.onStopNestedScroll(coordinatorLayout, child, target, type)
    }
    /*确定使用Behavior的View位置*/
    override fun onLayoutChild(parent: CoordinatorLayout, child: View, layoutDirection: Int): Boolean {
        return super.onLayoutChild(parent, child, layoutDirection)
    }

    /*嵌套滑动进行中，要监听的子 View的滑动事件已经被消费*/
    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type)
    }
    /*嵌套滑动进行中，要监听的子 View将要滑动，滑动事件即将被消费（但最终被谁消费，可以通过代码控制）*/
    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
    }

    /*要监听的子 View在快速滑动中*/

    override fun onNestedFling(coordinatorLayout: CoordinatorLayout, child: View, target: View, velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed)
    }

   /* 要监听的子View即将快速滑动*/
    override fun onNestedPreFling(coordinatorLayout: CoordinatorLayout, child: View, target: View, velocityX: Float, velocityY: Float): Boolean {
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY)
    }


}