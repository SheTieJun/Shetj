package me.shetj.testmybase

import com.qcshendeng.toyo.common.CommonPresenter
import me.shetj.base.base.BaseActivity
import me.shetj.base.base.BaseMessage

class MainActivity : BaseActivity<CommonPresenter>() {

    fun getContentViewId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {

    }

    override fun initData() {
        mPresenter = CommonPresenter(this)
    }

    override fun updateView(message: BaseMessage<*>) {
        super.updateView(message)
        when(message.type){

        }
    }

}
