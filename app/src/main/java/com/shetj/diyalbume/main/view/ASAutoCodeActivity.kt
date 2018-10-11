package com.shetj.diyalbume.main.view

import android.os.Bundle
import com.jakewharton.rxbinding2.view.RxView
import com.shetj.diyalbume.R
import com.shetj.diyalbume.main.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_asauto_code.*
import me.shetj.base.base.BaseSwipeBackActivity
import me.shetj.base.tools.app.ArmsUtils
import me.shetj.update.*

class ASAutoCodeActivity : BaseSwipeBackActivity<MainPresenter>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asauto_code)
        initView()
        initData()
    }
    override fun initData() {
        RxView.clicks(btn_DetailFragment)
                .subscribe {
                    ArmsUtils.startActivity(this, ItemListActivity::class.java)
                }
        RxView.clicks(btn_BottomNavigationActivity)
                .subscribe {
                    ArmsUtils.startActivity(this, BottomNavigationActivity::class.java)
                }
        RxView.clicks(fullscreenActivity).subscribe {
            ArmsUtils.startActivity(this,  FullscreenActivity::class.java)
        }

        RxView.clicks(loginActivity).subscribe {
            ArmsUtils.startActivity(this,  LoginActivity::class.java)
        }

        RxView.clicks(navigationDrawerActivity).subscribe {
            ArmsUtils.startActivity(this,  NavigationDrawerActivity::class.java)
        }
        RxView.clicks(scrollingActivity).subscribe {
            ArmsUtils.startActivity(this,  ScrollingActivity::class.java)
        }
        RxView.clicks(tabActivity).subscribe {
            ArmsUtils.startActivity(this,  TabActivity::class.java)
        }
        RxView.clicks(settingsActivity).subscribe {
            ArmsUtils.startActivity(this,  SettingsActivity::class.java)
        }
    }

    override fun initView() {

    }
}
