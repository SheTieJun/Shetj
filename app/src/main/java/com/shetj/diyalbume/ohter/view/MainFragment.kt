package com.shetj.diyalbume.ohter.view


import android.view.LayoutInflater
import android.view.View

import com.shetj.diyalbume.R
import com.shetj.diyalbume.ohter.presenter.OtherPresenter
import me.shetj.base.qmui.BaseQMUIFragment

class MainFragment : BaseQMUIFragment<OtherPresenter>() {

    override fun onCreateView(): View {
        return  LayoutInflater.from(context).inflate(R.layout.fragment_main, null)
    }

    override fun initEventAndData() {
    }

    override fun lazyLoadData() {
    }

}
