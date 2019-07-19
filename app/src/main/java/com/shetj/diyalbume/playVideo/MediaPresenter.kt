package com.shetj.diyalbume.playVideo

import me.shetj.base.base.BaseModel
import me.shetj.base.base.BasePresenter
import me.shetj.base.base.IView

class MediaPresenter(view:IView) :BasePresenter<MediaModel>(view){

    init {
        model  = MediaModel()
    }


}
