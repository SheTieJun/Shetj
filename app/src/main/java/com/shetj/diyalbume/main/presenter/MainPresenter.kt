package com.shetj.diyalbume.main.presenter

import android.content.Intent
import cn.a51mofang.base.base.BasePresenter
import com.shetj.diyalbume.createAlbum.CreateActivity
import com.shetj.diyalbume.main.view.MainActivity

/**
 *
 * <b>@packageName：</b> com.shetj.diyalbume.main.presenter<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2017/12/4<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */
class MainPresenter(mainActivity: MainActivity) :BasePresenter() {

    var mMainActivity = mainActivity

    fun startCreateAlbum() {
        val intent = Intent(mMainActivity, CreateActivity::class.java)
        mMainActivity.startActivity (intent)
    }


}