package com.shetj.diyalbume.swipcard

import androidx.recyclerview.widget.RecyclerView
import me.shetj.base.tools.app.ArmsUtils
import kotlin.math.min

/**
 *
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2019/7/26<br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe 叠层 </b>
 */
class TierLayoutManager : RecyclerView.LayoutManager() {

    private val MAX_SHOW_COUNT = 5
    private val marTop = ArmsUtils.dip2px(10f)
    private val marLeftAndRight = ArmsUtils.dip2px(10f)
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT,
                RecyclerView.LayoutParams.WRAP_CONTENT)
    }

    /**
     *
     */
    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        if (itemCount == 0) {
            detachAndScrapAttachedViews(recycler)
            return
        }
        //state.isPreLayout()是支持动画的
        if (itemCount == 0 && state.isPreLayout) {
            return
        }
        // 先把所有的View先从RecyclerView中detach掉，
        // 然后标记为"Scrap"状态，表示这些View处于可被重用状态(非显示中)。
        // 实际就是把View放到了Recycler中的一个集合中。
        detachAndScrapAttachedViews(recycler)
        //计算子部分
        calculateChildrenSite(recycler)
    }


    //    private fun calculateChildrenSite(recycler: RecyclerView.Recycler) {
//        val min = min(MAX_SHOW_COUNT, itemCount)
//        for (i in  0 until min) {
//            // 遍历Recycler中保存的View取出来
//            val view = recycler.getViewForPosition(i)
//            addView(view) // 因为刚刚进行了detach操作，所以现在可以重新添加
//            measureChildWithMargins(view, 0, 0) // 通知测量view的margin值
//            val width = getDecoratedMeasuredWidth(view) // 计算view实际大小，包括了ItemDecorator中设置的偏移量。
//            val height = getDecoratedMeasuredHeight(view)
//            layoutDecorated(view, 0,marTop*i , width,   height+marTop*i )
//            view.scaleX = 1 - ((marLeftAndRight*2*i/width.toFloat()))
//            view.scaleY = 1 - ((marLeftAndRight*2*i/width.toFloat()))
//        }
//    }

    private fun calculateChildrenSite(recycler: RecyclerView.Recycler) {
        val min = min(MAX_SHOW_COUNT, itemCount)
        for (i in (0 until min).reversed()) {
            // 遍历Recycler中保存的View取出来
            val view = recycler.getViewForPosition(i)
            addView(view) // 因为刚刚进行了detach操作，所以现在可以重新添加
            measureChildWithMargins(view, 0, 0) // 通知测量view的margin值
            val width = getDecoratedMeasuredWidth(view) // 计算view实际大小，包括了ItemDecorator中设置的偏移量。
            val height = getDecoratedMeasuredHeight(view)
            layoutDecorated(view, 0, marTop * (4-i), width, height + marTop * (4-i))
            view.scaleX = 1 - ((marLeftAndRight * 2 * i / width.toFloat()))
            view.scaleY = 1 - ((marLeftAndRight * 2 * i / width.toFloat()))
            view.alpha = 1 - ((marLeftAndRight * 2 * i / width.toFloat()))
        }
    }
}