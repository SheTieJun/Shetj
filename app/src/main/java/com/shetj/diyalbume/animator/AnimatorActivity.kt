package com.shetj.diyalbume.animator

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import com.jakewharton.rxbinding2.view.RxView
import com.shetj.diyalbume.R
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_animator.*
import me.shetj.base.base.BaseActivity

class AnimatorActivity : BaseActivity<AnimatorPresenter>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animator)
        initView()
        initData()
    }

    override fun initView() {
        mPresenter  = AnimatorPresenter(this)


        RxView.clicks(btn_change)
                .subscribe {
                    mPresenter.setType()
                }
        RxView.clicks(btn_transitionManager)
                .subscribe {
                    val intent = Intent(rxContext, TranstionActivity::class.java)
                    ActivityCompat.startActivity(rxContext, intent, ActivityOptionsCompat.makeSceneTransitionAnimation(rxContext).toBundle())
                }
    }

    override fun initData() {

        RxView.clicks(btn_alpha)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mPresenter.startAlphaAnim(btn_alpha)
                }

        RxView.clicks(btn_tran)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mPresenter.startTran(btn_tran)
                }

        RxView.clicks(btn_rotation)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    mPresenter.startRota(btn_rotation)
                }

        RxView.clicks(btn_sca)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mPresenter.startScale(btn_sca)
                }

        RxView.clicks(btn_value)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mPresenter.startValue(btn_value)
                }

        RxView.clicks(btn_object)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mPresenter.startObject(btn_object)
                }

        RxView.clicks(btn_set)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mPresenter.startSet(btn_set)
                }

        RxView.clicks(btn_view_property)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mPresenter.startViewProperty(btn_view_property)
                }
    }
}
