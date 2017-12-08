package com.shetj.diyalbume.createAlbum.bean

/**
 *
 * <b>@packageName：</b> com.shetj.diyalbume.createAlbum.bean<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2017/12/8<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */
class AlbumContent {

    var photos : List<Photo> = ArrayList()
    constructor(photos: List<Photo>){
        this.photos =photos
    }

}