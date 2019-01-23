package com.shetj.diyalbume.ohter.view


import android.view.LayoutInflater
import android.view.View
import com.shetj.diyalbume.R
import com.shetj.diyalbume.ohter.presenter.OtherPresenter
import me.shetj.base.qmui.BaseQMUIFragment
import timber.log.Timber

class TipDialogFragment : BaseQMUIFragment<OtherPresenter>() {


    override fun onCreateView(): View {
        return  LayoutInflater.from(context).inflate(R.layout.fragment_tip_dialog, null)
    }

    override fun initEventAndData() {
        Timber.i("initEventAndData")
    }

    override fun lazyLoadData() {
        Timber.i("lazyLoadData")
    }
}
