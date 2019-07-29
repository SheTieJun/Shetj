package com.shetj.diyalbume.swipcard

import android.content.Context
import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView
import me.shetj.base.tools.app.ArmsUtils

/**
 *
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2019/7/26<br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b>
 * 计算每个ItemView的位置
 * 添加滑动事件
 * 实现缓存<br>
 */
class SwipLayoutManager(private var context: Context) :RecyclerView.LayoutManager(){

    private val MAX_SHOW_COUNT = 3

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT,
                RecyclerView.LayoutParams.WRAP_CONTENT)
    }

    /**
     *
     */
    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        if (itemCount <= 0 || state.isPreLayout) {
            return
        }

        // 先把所有的View先从RecyclerView中detach掉，
        // 然后标记为"Scrap"状态，表示这些View处于可被重用状态(非显示中)。
        // 实际就是把View放到了Recycler中的一个集合中。
        detachAndScrapAttachedViews(recycler)

        //计算子部分
        calculateChildrenSite(recycler)
        super.onLayoutChildren(recycler, state)

    }


    private fun calculateChildrenSite(recycler: RecyclerView.Recycler) {
        for (i in 0 until itemCount) {
            // 遍历Recycler中保存的View取出来
            val view = recycler.getViewForPosition(i)
            addView(view) // 因为刚刚进行了detach操作，所以现在可以重新添加
            measureChildWithMargins(view, 0, 0) // 通知测量view的margin值
            val width = getDecoratedMeasuredWidth(view) // 计算view实际大小，包括了ItemDecorator中设置的偏移量。
            val height = getDecoratedMeasuredHeight(view)

            val mTmpRect = Rect()
            //调用这个方法能够调整ItemView的大小，以除去ItemDecorator。
            calculateItemDecorationsForChild(view, mTmpRect)

            // 调用这句我们指定了该View的显示区域，并将View显示上去，此时所有区域都用于显示View，
            layoutDecorated(view, 0, 0, width,   height)
        }
    }

}