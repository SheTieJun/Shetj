package me.shetj.video

import android.content.Context
import me.shetj.base.base.BaseModel
import me.shetj.video.database.VideoDatabase
import me.shetj.video.model.VideoDao

class MainModel(context: Context):BaseModel() {

    var videoDao:VideoDao = VideoDatabase.getDataBase(context).videoDao()

}
