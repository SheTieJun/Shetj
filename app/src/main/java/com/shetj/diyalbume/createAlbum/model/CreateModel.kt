package com.shetj.diyalbume.createAlbum.model

import com.shetj.diyalbume.createAlbum.bean.Album
import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * **@packageName：** com.shetj.diyalbume.createAlbum<br></br>
 * **@author：** shetj<br></br>
 * **@createTime：** 2017/12/5<br></br>
 * **@company：**<br></br>
 * **@email：** 375105540@qq.com<br></br>
 * **@describe**<br></br>
 */

class CreateModel{
    fun saveAlbum(){
        val album = Album("1","1","1")

    }

    fun test(){
        val okHttpClient = OkHttpClient()
        val request = Request.Builder().url("http://baidu.com").build()

        var cal = okHttpClient.newCall(request)

        val execute = cal.execute()
        execute.body()

    }
}
