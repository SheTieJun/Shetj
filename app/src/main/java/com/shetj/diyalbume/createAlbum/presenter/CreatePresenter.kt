package com.shetj.diyalbume.createAlbum.presenter

import cn.a51mofang.base.base.BasePresenter
import com.shetj.diyalbume.createAlbum.bean.AlbumContent
import com.shetj.diyalbume.createAlbum.view.CreateActivity

/**
 *
 * <b>@packageName：</b> com.shetj.diyalbume.createAlbum<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2017/12/4<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */
class CreatePresenter(createActivity: CreateActivity) :BasePresenter(){


    private var createActivity = createActivity
    private lateinit var albumContent :AlbumContent

    fun addPhoto(){
         createActivity.addView()
     }

    fun createAlbum() {
        albumContent = AlbumContent(ArrayList())
    }

}