package me.shetj.video.bean

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

    @ColumnInfo(name = "video_name")
    var videoName:String ?=null //播放地址

    @ColumnInfo(name = "position")
    var position:Long ?= null //播放的位置

    @ColumnInfo(name = "duration")
    var duration:Long ?= null //时长

    @ColumnInfo(name = "save_time")
    var saveTime:Long?=null

}