package com.shetj.diyalbume.createAlbum.bean

import cn.shetj.base.tools.json.GsonKit

/**
 *
 * <b>@packageName：</b> com.shetj.diyalbume.createAlbum.bean<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2017/12/7<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */
class Album(  var describe : String,
              var preview : String,
              var albumInfoJson:String) {

    fun toJson(): String? {
        return GsonKit.objectToJson(this)
    }

    override fun toString(): String {
        return "Album(describe='$describe', preview='$preview', albumInfoJson='$albumInfoJson')"
    }



}