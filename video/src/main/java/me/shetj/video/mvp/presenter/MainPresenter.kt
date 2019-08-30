package me.shetj.video.mvp.presenter

import me.shetj.base.base.BasePresenter
import me.shetj.base.base.IView
import me.shetj.video.mvp.model.MainModel

class MainPresenter (view:IView): BasePresenter<MainModel>(view) {

    companion object{
        const val INIT_VIDEO_INFO = 1
    }


    init {
        model = MainModel(view.rxContext)
    }

    fun initDate() {
        addDispose(model?.videoDao
                ?.getVideos()?.subscribe {
                    view.updateView(getMessage(INIT_VIDEO_INFO,it))
                }!!)
    }

}
