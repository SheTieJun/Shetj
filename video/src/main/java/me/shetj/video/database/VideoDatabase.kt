package me.shetj.video.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import me.shetj.video.bean.Video
import me.shetj.video.bean.VideoDao

@Database(entities = [Video::class],version = 1)
abstract class VideoDatabase : RoomDatabase() {

    abstract fun videoDao():VideoDao

    companion object{
        @Volatile
        private var videoDatabase:VideoDatabase ?=null

//        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL("ALTER TABLE users " + " ADD COLUMN last_update INTEGER")
//            }
//        }

        fun getDataBase(context:Context):VideoDatabase{
            val temp = videoDatabase
            if (temp !=null){
                return temp
            }
            synchronized(this){
                val instance =
                        Room.databaseBuilder(context.applicationContext,
                        VideoDatabase::class.java,"video_database")
//                                .fallbackToDestructiveMigration(),所有表都会被丢弃
                                //增加下面这一行
//                                .addMigrations(MIGRATION_1_2)
                                .build()
                videoDatabase = instance
                return instance
            }
        }
    }


}