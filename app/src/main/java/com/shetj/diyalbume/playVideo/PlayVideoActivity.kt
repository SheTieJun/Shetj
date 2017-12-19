package com.shetj.diyalbume.playVideo

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import cn.a51mofang.base.tools.app.UiUtils
import com.shetj.diyalbume.R
import com.shuyu.gsyvideoplayer.utils.CommonUtil

import kotlinx.android.synthetic.main.activity_paly_video.*
import kotlinx.android.synthetic.main.content_play_video.*

class PlayVideoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paly_video)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        showRecycleView()
    }

    private fun showRecycleView() {
        var list = ArrayList<String>()
        for (i in 1..100 step 1){
            list.add(i.toString()+"itme")
        }

        var adapter = AutoRecycleView(list)

        var linearLayoutManager = LinearLayoutManager(this)
        UiUtils.configRecycleView(iRecyclerView,linearLayoutManager)

        //限定范围为屏幕一半的上下偏移100
        val playTop = CommonUtil.getScreenHeight(this) / 2 - CommonUtil.dip2px(this, 200f)
        val playBottom = CommonUtil.getScreenHeight(this) / 2 + CommonUtil.dip2px(this, 200f)
        //自定播放帮助类
        var scrollCalculatorHelper = ScrollCalculatorHelper(R.id.tv_string,CommonUtil.getScreenHeight(this) / 2, playTop, playBottom,adapter)



        iRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            internal var firstVisibleItem: Int = 0
            internal var lastVisibleItem: Int = 0

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                scrollCalculatorHelper.onScrollStateChanged(recyclerView, newState,linearLayoutManager)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition()
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                scrollCalculatorHelper.onScroll(recyclerView, firstVisibleItem, lastVisibleItem, lastVisibleItem - firstVisibleItem)

            }


        })


        iRecyclerView.adapter =adapter

        adapter.setPlay(0)
    }

}
