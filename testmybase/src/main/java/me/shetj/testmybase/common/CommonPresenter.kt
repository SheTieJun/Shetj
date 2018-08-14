package com.qcshendeng.toyo.common

import com.zhouyou.http.EasyHttp
import com.zhouyou.http.callback.SimpleCallBack
import me.shetj.base.base.BasePresenter
import me.shetj.base.base.IView

/**
 *
 * <b>@packageName：</b> com.qcshendeng.toyo.common<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2018/8/3 0003<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */
class CommonPresenter(view :IView) :BasePresenter<CommonModel>(view){
    init {
        model = CommonModel()
    }


    fun getPayType(type: String,simpleCallBack: SimpleCallBack<String>) {

        EasyHttp.get("pay/vipPrice?type=$type")
                .execute(simpleCallBack)
        view.hideLoading()
    }

}