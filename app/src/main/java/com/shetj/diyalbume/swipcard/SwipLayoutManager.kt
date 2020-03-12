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
    private val marTop = ArmsUtils.dip2px(5f)

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT,
                RecyclerView.LayoutParams.WRAP_CONTENT)
    }

    /**
     *
     */
    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        if (itemCount == 0){
            detachAndScrapAttachedViews(recycler)
            return
        }
        //state.isPreLayout()是支持动画的
        if (itemCount == 0 && state.isPreLayout){
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

        var scale = 1f
        for (i in  0 until MAX_SHOW_COUNT ) {
            // 遍历Recycler中保存的View取出来
            val view = recycler.getViewForPosition(i)
            addView(view) // 因为刚刚进行了detach操作，所以现在可以重新添加
            measureChildWithMargins(view, 0, 0) // 通知测量view的margin值
            val width = getDecoratedMeasuredWidth(view) // 计算view实际大小，包括了ItemDecorator中设置的偏移量。
            val height = getDecoratedMeasuredHeight(view)

            val mTmpRect = Rect()
            //调用这个方法能够调整ItemView的大小，以除去ItemDecorator。
            calculateItemDecorationsForChild(view, mTmpRect)
            scale *= (1f - 0.03f)
            val mWidth = width * scale
            // 调用这句我们指定了该View的显示区域，并将View显示上去，此时所有区域都用于显示View，
            layoutDecorated(view, marTop*i,marTop*i , mWidth.toInt()-marTop,   height+ marTop*i)
        }
    }


    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        offsetVertical(dy)
        // 填充
        fillView(dy,recycler,state)
        // 滚动
        //滚动
        offsetChildrenVertical(dy*-1)
        // 回收已经离开界面的
        recyclerOut(dy,recycler,state)

        return dy
    }

    /**
     * 通过getChildCount() 获取当前所有的子View
     * 例如向上滚动,name就回收最下面的,最下面的View的top滑动后超出了RecyclerView的高度,说明这个View全部在界面外了,可以回收了,使用removeAndRecycleView移除并回收
     * 向下滚动就判断顶部的Bottom是否小于0
     */
    private fun recyclerOut(dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        for (i in 0 until itemCount ){
            val v = getChildAt(i)
            if (dy > 0){
                //下滑
                v?.let {
                    if (v.bottom -dy < 0){
                        recycler?.let { it1 -> removeAndRecycleView(v, it1) }
                    }
                }
            }else{
                //上滑
                v?.let {
                    if (v.top -dy > height){
                        recycler?.let { it1 -> removeAndRecycleView(v, it1) }
                    }
                }
            }
        }
    }

    private fun offsetVertical(dy: Int) {
        var canScroll: Int
        if (dy > 0) {
            val lastView = getChildAt(childCount - 1)
            lastView?.let {
                val lastPos = getPosition(lastView)
                if (lastPos >= itemCount - 1) {
                    if (lastView.bottom - dy < height) {
                        canScroll = lastView.bottom - height
                        offsetChildrenVertical(canScroll * -1)
                    }
                }
            }
        } else {
            val firView = getChildAt(0)
            firView?.let {
                val firstPos = getPosition(firView)
                if (firstPos <= 0) {
                    if (firView.top - dy >= 0) {
                        canScroll = firView.top
                        offsetChildrenVertical(canScroll * -1)
                    }
                }
            }
        }
    }

    override fun scrollHorizontallyBy(dx: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        return super.scrollHorizontallyBy(dx, recycler, state)
    }

    /**
     * 通过getChildAt获取最后一个View
     * 再通过getPosition获取这个View的Adapter中的位置,最后一个了,就不要继续填充了,因为没有了,如果有下一个,就继续
     * 这里还没有滑动,但是即将滑动的距离dy传进来了,如果最后一个View滑动dy后小于RecyclerView的高度了
     * 说明最后一个View已经全部出现在界面上了,之后就是空白了,需要添加新的子View
     * 那就做获取,测量,添加操作
     * 向上滚动是一样的逻辑
     */
    private fun fillView(dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        //childCount  Number of attached children ,已经添加在界面的child view
        if (dy >0){
            //向下滚动
            val lastView = getChildAt(childCount - 1)
            lastView?.let {
                val position = getPosition(it)
                //判断是不是最后一个，如果已经是最后一个 什么都没必要干了
                if(position == childCount- 1){
                    return
                }
                //如果小于item的高度，说明
                if (lastView.bottom - dy < height){
                    //开始填充
                    val view = recycler?.getViewForPosition(position + 1)

                    view?.apply {
                        addView(this)
                        //开启测量
                        measureChildWithMargins(this,0,0)
                        val height = getDecoratedMeasuredHeight(this)
                        val width = getDecoratedMeasuredWidth(this)
                        //布局
                        layoutDecorated(this,0,lastView.bottom,width,lastView.bottom+height)
                    }
                }

            }
        }else{
            //向上滚动
            val firstView = getChildAt(0)
            firstView?.let {
                val position = getPosition(it)
                if (position == 0){
                    return
                }

                if (firstView.top > 0){
                    val view = recycler?.getViewForPosition(position + 1)
                    view?.apply {
                        addView(this)
                        //开启测量
                        measureChildWithMargins(this,0,0)
                        val height = getDecoratedMeasuredHeight(this)
                        val width = getDecoratedMeasuredWidth(this)
                        //布局
                        layoutDecorated(this,0,firstView.top-height,width,firstView.top)
                    }
                }
            }

        }
    }

}