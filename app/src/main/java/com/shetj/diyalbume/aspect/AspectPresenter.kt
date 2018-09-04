package com.shetj.diyalbume.aspect

import me.shetj.aspect.debug.DebugTrace
import me.shetj.base.base.BasePresenter
import me.shetj.base.base.IView

class AspectPresenter(view :IView) :BasePresenter<AspectModel>(view) {
    @DebugTrace
    fun testAspect() {
        view.updateView(getMessage(1,"测试"))
    }

    init {
        model = AspectModel()
    }

}
