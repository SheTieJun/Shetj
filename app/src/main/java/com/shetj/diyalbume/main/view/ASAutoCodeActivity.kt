package com.shetj.diyalbume.main.view

import android.os.Bundle
import com.jakewharton.rxbinding3.view.clicks
import com.shetj.diyalbume.R
import com.shetj.diyalbume.main.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_asauto_code.*
import me.shetj.base.base.BaseActivity
import me.shetj.base.tools.app.ArmsUtils
import me.shetj.update.*

class ASAutoCodeActivity : BaseActivity<MainPresenter>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asauto_code)
    }
    override fun initData() {
        btn_DetailFragment.clicks()
                .subscribe {
                    ArmsUtils.startActivity(this, ItemListActivity::class.java)
                }
        btn_BottomNavigationActivity.clicks()
                .subscribe {
                    ArmsUtils.startActivity(this, BottomNavigationActivity::class.java)
                }
        fullscreenActivity.clicks().subscribe {
            ArmsUtils.startActivity(this,  FullscreenActivity::class.java)
        }

        loginActivity.clicks().subscribe {
            ArmsUtils.startActivity(this,  LoginActivity::class.java)
        }

        navigationDrawerActivity.clicks().subscribe {
            ArmsUtils.startActivity(this,  NavigationDrawerActivity::class.java)
        }
        scrollingActivity.clicks().subscribe {
            ArmsUtils.startActivity(this,  ScrollingActivity::class.java)
        }
        tabActivity.clicks().subscribe {
            ArmsUtils.startActivity(this,  TabActivity::class.java)
        }
        settingsActivity.clicks().subscribe {
            ArmsUtils.startActivity(this,  SettingsActivity::class.java)
        }
    }

    override fun initView() {

    }
}
