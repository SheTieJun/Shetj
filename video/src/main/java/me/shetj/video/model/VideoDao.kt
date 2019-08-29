package me.shetj.video.model

import androidx.room.*
import io.reactivex.Maybe
import io.reactivex.Single

/**
 * 使用接口，编写dao
 */
@Dao
interface VideoDao {

    @Query("SELECT * FROM Video ORDER BY videoId")
    fun getVideos():Maybe<List<Video>>


    @Update
    fun update(video: Video): Single<Long>


    @Insert
    fun insetr(video: Video):Maybe<Long>

    @Delete
    fun delete(video: Video):Maybe<Long>
}
