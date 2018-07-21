package com.shetj.diyalbume.animator

import android.os.Bundle
import android.view.View
import com.jakewharton.rxbinding2.view.RxView
import com.shetj.diyalbume.R
import kotlinx.android.synthetic.main.activity_animator.*
import me.shetj.base.base.BaseActivity

class AnimatorActivity : BaseActivity<AnimatorPresenter>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animator)
    }

    override fun initView() {
        mPresenter  = AnimatorPresenter(this)

        RxView.clicks(btn_change)
                .subscribe {
                    mPresenter.setType()
                }
    }

    override fun initData() {

        RxView.clicks(btn_alpha)
                .subscribe {
                    mPresenter.startAlphaAnim(it as View)
                }

        RxView.clicks(btn_tran)
                .subscribe {
                    mPresenter.startTran(it as View)
                }

        RxView.clicks(btn_rotation)
                .subscribe{
                    mPresenter.startRota(it as View)
                }

        RxView.clicks(btn_sca)
                .subscribe {
                    mPresenter.startScale(it as View)
                }

    }
}
