package me.shetj.video

import me.shetj.base.base.BasePresenter
import me.shetj.base.base.IView

class MainPresenter (view:IView): BasePresenter<MainModel>(view) {

    init {
        model = MainModel(view.rxContext)
    }

    fun initDate() {
        addDispose(model?.videoDao
                ?.getVideos()?.subscribe {
                    view.updateView(getMessage(1,it))
                }!!)
    }

}
