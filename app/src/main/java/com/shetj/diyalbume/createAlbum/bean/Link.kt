package com.shetj.diyalbume.createAlbum.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import org.xutils.x

/**
 *
 * <b>@packageName：</b> com.shetj.diyalbume.createAlbum.bean<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2017/12/8<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */
class Link(var type: Int,
           var content:String) :MultiItemEntity {
    val VIDEO = 1
    val URL = 2
    val SOUND = 3
    val TEXT = 4

    override fun getItemType(): Int {
        return when (type) {
            1 -> VIDEO
            2 -> URL
            3 -> SOUND
            4 -> TEXT
            else -> 100
        }
    }


}