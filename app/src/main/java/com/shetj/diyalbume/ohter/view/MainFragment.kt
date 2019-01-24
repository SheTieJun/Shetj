package com.shetj.diyalbume.ohter.view


import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.jakewharton.rxbinding2.view.RxView

import com.shetj.diyalbume.R
import com.shetj.diyalbume.ohter.presenter.OtherPresenter
import kotlinx.android.synthetic.main.fragment_main.*
import me.shetj.base.qmui.BaseQMUIFragment
import timber.log.Timber

class MainFragment : BaseQMUIFragment<OtherPresenter>() {

    private val REQUEST_CODE = 1

    override fun onCreateView(): View {
        return  LayoutInflater.from(context).inflate(R.layout.fragment_main, null)
    }


    override fun initEventAndData() {
        Timber.i("initEventAndData")
        mPresenter = OtherPresenter(this);
        RxView.clicks(bt_tip).subscribe {
            startFragmentForResult(TipDialogFragment(),REQUEST_CODE)
        }
    }

    override fun lazyLoadData() {
        Timber.i("lazyLoadData")
    }

    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onFragmentResult(requestCode, resultCode, data)
        mPresenter.onFragmentResult(requestCode, resultCode, data)
    }
}
