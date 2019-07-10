package com.shetj.diyalbume.animator

import android.content.Intent
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.jakewharton.rxbinding3.view.clicks
import com.shetj.diyalbume.R
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_animator.*
import me.shetj.base.base.BaseActivity

@Route(path = "/shetj/AnimatorActivity")
class AnimatorActivity : BaseActivity<AnimatorPresenter>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animator)
        initView()
        initData()
    }

    override fun initView() {
        mPresenter  = AnimatorPresenter(this)


        btn_change.clicks()
                .subscribe {
                    mPresenter?.setType()
                }
        btn_transitionManager.clicks()
                .subscribe {
                    val intent = Intent(rxContext, TranstionActivity::class.java)
                    ActivityCompat.startActivity(rxContext, intent, ActivityOptionsCompat.makeSceneTransitionAnimation(rxContext).toBundle())
                }
    }

    override fun initData() {

        btn_alpha.clicks()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mPresenter?.startAlphaAnim(btn_alpha)
                }

        btn_tran.clicks()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mPresenter?.startTran(btn_tran)
                }

        btn_rotation.clicks()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    mPresenter?.startRota(btn_rotation)
                }

        btn_sca.clicks()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mPresenter?.startScale(btn_sca)
                }

        btn_value.clicks()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mPresenter?.startValue(btn_value)
                }

        btn_object.clicks()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mPresenter?.startObject(btn_object)
                }

        btn_set.clicks()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mPresenter?.startSet(btn_set)
                }

        btn_view_property.clicks()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mPresenter?.startViewProperty(btn_view_property)
                }
    }
}
