package com.shetj.diyalbume.aspect

import me.shetj.aspect.debug.DebugTrace
import me.shetj.aspect.network.CheckNetwork
import me.shetj.base.base.BasePresenter
import me.shetj.base.base.IView
import me.shetj.base.tools.app.ArmsUtils
import me.shetj.base.tools.app.ArmsUtils.Companion.getMessage

class AspectPresenter(view :IView) :BasePresenter<AspectModel>(view) {
    @DebugTrace
    fun testAspect() {
        view.updateView(getMessage(1,"测试"))
    }

    @CheckNetwork
    fun testNetWork() {
        ArmsUtils.makeText("有网")
    }

    init {
        model = AspectModel()
    }

}
