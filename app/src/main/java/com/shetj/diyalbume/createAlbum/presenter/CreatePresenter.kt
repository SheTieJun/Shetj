package com.shetj.diyalbume.createAlbum.presenter

import me.shetj.base.base.BasePresenter
import me.shetj.base.tools.app.LogUtil
import com.shetj.diyalbume.createAlbum.bean.AlbumContent
import com.shetj.diyalbume.createAlbum.view.CreateActivity
import me.shetj.base.base.BaseModel
import me.shetj.base.base.IView

/**
 *
 * <b>@packageName：</b> com.shetj.diyalbume.createAlbum<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2017/12/4<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */
class CreatePresenter(view: IView) : BasePresenter<BaseModel>(view){



    private lateinit var albumContent :AlbumContent


    fun createAlbum() {
        albumContent = AlbumContent(ArrayList())
        LogUtil.show(albumContent.photos.size.toString())
    }

}