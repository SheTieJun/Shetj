package com.shetj.diyalbume.playVideo.video

import android.os.Handler
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView

/**
 * 计算滑动，自动播放的帮助类
 */

class ScrollCalculatorHelper(private val playId: Int, private val center: Int, private val rangeTop: Int, private val rangeBottom: Int, private val autoAdapter: AutoRecycleView) {

    private var firstVisible = 0
    private var lastVisible = 0
    private var visibleCount = 1
    private var runnable: PlayRunnable? = null
    private val playHandler = Handler()

    fun onScrollStateChanged(view: RecyclerView, scrollState: Int, linearLayoutManager: LinearLayoutManager) {
        when (scrollState) {
            RecyclerView.SCROLL_STATE_IDLE -> playVideo(view)
            RecyclerView.SCROLL_STATE_DRAGGING -> isStop(view, linearLayoutManager)
            RecyclerView.SCROLL_STATE_SETTLING -> isStop(view, linearLayoutManager)
        }
    }

    private fun isStop(view: RecyclerView, linearLayoutManager: LinearLayoutManager) {
        val firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()
        val lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition()
        autoAdapter.isStop(firstVisibleItemPosition, lastVisibleItemPosition)
    }


    fun onScroll(view: RecyclerView, firstVisibleItem: Int, lastVisibleItem: Int, visibleItemCount: Int) {
        if (firstVisible == firstVisibleItem) {
            return
        }
        firstVisible = firstVisibleItem
        lastVisible = lastVisibleItem
        visibleCount = visibleItemCount + 1
    }


    internal fun playVideo(view: RecyclerView?) {

        if (view == null) {
            return
        }

        val layoutManager = view.layoutManager

        var BaseTextView: TextView? = null
        var needPlay = false
        for (i in 0 until visibleCount) {

            if (layoutManager!!.getChildAt(i) != null && layoutManager.getChildAt(i)!!.findViewById<View>(playId) != null) {
                val textView = layoutManager.getChildAt(i)!!.findViewById<TextView>(playId)
                val screenPosition = IntArray(2)
                textView.getLocationOnScreen(screenPosition)
                val halfHeight = textView.height / 2
                val rangePosition = screenPosition[1] + halfHeight
                //中心点在播放区域内
                if (rangePosition >= rangeTop && rangePosition <= rangeBottom) {
                    if (runnable != null && runnable!!.textView === textView) {
                        textView.text = "同一个$firstVisible$i"
                        return
                    }
                    BaseTextView = textView
                    BaseTextView!!.tag = firstVisible + i
                    needPlay = true
                    break
                }

            }
        }


        if (BaseTextView != null && needPlay) {
            if (runnable != null) {
                val tmpPlayer = runnable!!.textView
                playHandler.removeCallbacks(runnable)
                runnable = null
                if (tmpPlayer === BaseTextView) {
                    return
                }
            }
            runnable = PlayRunnable(BaseTextView)
            //降低频率
            playHandler.postDelayed(runnable, 100)
        }


    }

    private inner class PlayRunnable(internal var textView: TextView) : Runnable {

        override fun run() {
            autoAdapter.setPlay(textView.tag as Int)
        }
    }


}
