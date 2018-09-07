package com.shetj.diyalbume.miui

import cn.jiguang.imui.commons.models.IMessage
import java.text.SimpleDateFormat
import java.util.*
import com.shetj.diyalbume.R
import me.shetj.base.base.BaseModel
import me.shetj.base.s


/**
 * **@packageName：** com.shetj.diyalbume.miui<br></br>
 * **@author：** shetj<br></br>
 * **@createTime：** 2017/12/25<br></br>
 * **@company：**<br></br>
 * **@email：** 375105540@qq.com<br></br>
 * **@describe**<br></br>
 */

class MiUiModel :BaseModel() {
    override fun onDestroy() {
    }

    fun getInfo(page: Int): ArrayList<MyMessage> {
        val list = ArrayList<MyMessage>()
        val res = s.getApp().applicationContext.resources
        val messages = res.getStringArray(R.array.messages_array)
        for (i in messages.indices) {
            val message: MyMessage
            if (i % 2 == 0) {
                message = MyMessage(messages[i], IMessage.MessageType.RECEIVE_VOICE.ordinal)
                message.mediaFilePath = "http://oss.ppt66.com/1244/161513841963301.mp3"
                message.duration = 1000
                message.setUserInfo(DefaultUser("0", "DeadPool", "http://oss.ppt66.com/1244/16/20171210_130113.png"))
            } else {
                message = MyMessage(messages[i], IMessage.MessageType.SEND_VOICE.ordinal)
                message.mediaFilePath ="http://oss.ppt66.com/1244/161513841963301.mp3"
                message.duration = 10000
                message.setUserInfo(DefaultUser("1", "IronMan", "http://ppt66.com:8080/ppt_api/upload/images/2017/11/02/20171102_102131.png"))
            }
            message.timeString = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
            list.add(message)
        }
        list.reverse()
        return list


    }

}
