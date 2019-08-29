package me.shetj.video.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import me.shetj.video.model.Video
import me.shetj.video.model.VideoDao

@Database(entities = [Video::class],version = 1)
abstract class VideoDatabase : RoomDatabase() {

    abstract fun videoDao():VideoDao

    companion object{
        @Volatile
        private var videoDatabase:VideoDatabase ?=null

        fun getDataBase(context:Context):VideoDatabase{
            val temp = videoDatabase
            if (temp !=null){
                return temp
            }
            synchronized(this){
                val instance =
                        Room.databaseBuilder(context.applicationContext,
                        VideoDatabase::class.java,"video_database")
                                .build()
                videoDatabase = instance
                return instance
            }
        }
    }
}