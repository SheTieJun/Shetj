package com.shetj.diyalbume.createAlbum.bean

/**
 * **@packageName：** com.shetj.diyalbume.createAlbum.bean<br></br>
 * **@author：** shetj<br></br>
 * **@createTime：** 2017/12/7<br></br>
 * **@company：**<br></br>
 * **@email：** 375105540@qq.com<br></br>
 * **@describe**<br></br>
 */

class Photo (var url:String ,
             var size:String,
             var position: String,
             var angle:String,
             var link: Link){
    fun getLink():String{
        return link.content
    }
}
