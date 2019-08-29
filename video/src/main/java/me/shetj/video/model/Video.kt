package me.shetj.video.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Entity(indices = {@Index("name"),

 */
@Entity(tableName = "Video")
class Video {
    @PrimaryKey  //逐渐
    var videoId:Int =0

    @ColumnInfo(name = "video_url")
    var videoUrl:String ?=null //播放地址

    @ColumnInfo(name = "position")
    var position:Long ?= null //播放的位置



}