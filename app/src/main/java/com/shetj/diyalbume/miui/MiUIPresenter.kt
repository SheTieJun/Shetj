package com.shetj.diyalbume.miui

import cn.shetj.base.base.BasePresenter

/**
 *
 * <b>@packageName：</b> com.shetj.diyalbume.miui<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2017/12/25<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */
class MiUIPresenter(miUIActivity: MiUIActivity) : BasePresenter() {
    private var mModel = MiUiModel()
    private var activity = miUIActivity
    fun loadNextPage() {
                activity.showMsgList(mModel.getInfo(1))

    }


}