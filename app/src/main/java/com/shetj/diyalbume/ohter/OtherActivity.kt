package com.shetj.diyalbume.ohter

import android.os.Bundle
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import com.shetj.diyalbume.R
import com.shetj.diyalbume.ohter.presenter.OtherPresenter
import com.shetj.diyalbume.ohter.view.MainFragment
import me.shetj.base.qmui.BaseQMUIActivity

class OtherActivity : BaseQMUIActivity<OtherPresenter>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QMUIStatusBarHelper.setStatusBarLightMode(this)
        if (savedInstanceState == null) {
            val fragment = MainFragment()
            supportFragmentManager
                    .beginTransaction()
                    .add(contextViewId, fragment, fragment.javaClass.simpleName)
                    .addToBackStack(fragment.javaClass.simpleName)
                    .commit()
        }
        initView()
        initData()
    }

    override fun getContextViewId(): Int {
      return  R.id.other_qmui
    }

    override fun initData() {



    }

    override fun initView() {

    }
}
