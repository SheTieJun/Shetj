package com.shetj.diyalbume.createAlbum.presenter

import me.shetj.base.base.BasePresenter
import com.shetj.diyalbume.createAlbum.bean.AlbumContent
import me.shetj.base.base.BaseModel
import me.shetj.base.base.IView
import org.xutils.common.util.LogUtil

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
    }

}