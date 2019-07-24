/*
 * Copyright 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.shetj.diyalbume.playVideo.contentcatalogs


import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat

import com.shetj.diyalbume.BuildConfig
import com.shetj.diyalbume.R

import java.util.ArrayList
import java.util.HashMap
import java.util.TreeMap
import java.util.concurrent.TimeUnit


object MusicLibrary {


    /**
     *
     */
    // 构造音频数据
    private val music = TreeMap<String, MediaMetadataCompat>()
    // 图片资源id
    private val albumRes = HashMap<String, Int>()
    // 音频名称
    private val musicFileName = HashMap<String, String>()

    val root: String
        get() = "root"

    val mediaItems: List<MediaBrowserCompat.MediaItem>
        get() {
            val result = ArrayList<MediaBrowserCompat.MediaItem>()
            for (metadata in music.values) {
                result.add(
                        MediaBrowserCompat.MediaItem(
                                metadata.description, MediaBrowserCompat.MediaItem.FLAG_PLAYABLE))
            }
            return result
        }

    /**
     * 构造音频数据
     * @param mediaId         音频id
     * @param title           标题
     * @param artist          作者
     * @param album           图片
     * @param genre           种类
     * @param duration        时长
     * @param durationUnit    时间单位
     * @param musicFilename   音频文件
     * @param albumArtResId   资源id
     * @param albumArtResName
     */
    init {
        createMediaMetadataCompat(
                "Jazz_In_Paris",
                "Jazz in Paris",
                "Media Right Productions",
                "Jazz & Blues",
                "Jazz",
                103,
                TimeUnit.SECONDS,
                "jazz_in_paris.mp3",
                R.drawable.album_jazz_blues,
                "album_jazz_blues")
        createMediaMetadataCompat(
                "The_Coldest_Shoulder",
                "The Coldest Shoulder",
                "The 126ers",
                "Youtube Audio Library Rock 2",
                "Rock",
                160,
                TimeUnit.SECONDS,
                "the_coldest_shoulder.mp3",
                R.drawable.album_youtube_audio_library_rock_2,
                "album_youtube_audio_library_rock_2")
    }

    private fun getAlbumArtUri(albumArtResName: String): String {
        return ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                BuildConfig.APPLICATION_ID + "/drawable/" + albumArtResName
    }

    fun getMusicFilename(mediaId: String): String? {
        return if (musicFileName.containsKey(mediaId)) musicFileName[mediaId] else null
    }

    private fun getAlbumRes(mediaId: String): Int {
        return if (albumRes.containsKey(mediaId)) albumRes[mediaId]!! else 0
    }

    /**
     * 根据id 获取图片
     *
     * @param context
     * @param mediaId  可以对mediaID 头部做区分（本地音乐，应用内音乐，线上音乐）
     * @return
     */
    fun getAlbumBitmap(context: Context, mediaId: String): Bitmap {
        return BitmapFactory.decodeResource(context.resources, getAlbumRes(mediaId))
    }


    /**
     * 拷贝一份音频数据
     *
     * @param context
     * @param mediaId
     * @return
     */
    fun getMetadata(context: Context, mediaId: String): MediaMetadataCompat {
        // 根据id 音频列表获取音频数据
        val metadataWithoutBitmap = music[mediaId]
        // 获取音频图片数据
        val albumArt = getAlbumBitmap(context, mediaId)

        // Since MediaMetadataCompat is immutable, we need to create a copy to set the album art.
        // We don't set it initially on all items so that they don't take unnecessary memory.
        val builder = MediaMetadataCompat.Builder()
        // 设置数据
        for (key in arrayOf(
                MediaMetadataCompat.METADATA_KEY_MEDIA_ID,
                MediaMetadataCompat.METADATA_KEY_ALBUM,
                MediaMetadataCompat.METADATA_KEY_ARTIST,
                MediaMetadataCompat.METADATA_KEY_GENRE,
                MediaMetadataCompat.METADATA_KEY_TITLE)) {
            builder.putString(key, metadataWithoutBitmap!!.getString(key))
        }
        //
        builder.putLong(
                MediaMetadataCompat.METADATA_KEY_DURATION,
                metadataWithoutBitmap!!.getLong(MediaMetadataCompat.METADATA_KEY_DURATION))
        // 添加图片
        builder.putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, albumArt)
        return builder.build()
    }


    /**
     * @param mediaId         音频id
     * @param title           标题
     * @param artist          作者
     * @param album           图片
     * @param genre           种类
     * @param duration        时长
     * @param durationUnit    时间单位
     * @param musicFilename   音频文件
     * @param albumArtResId   资源id
     * @param albumArtResName
     */
    private fun createMediaMetadataCompat(
            mediaId: String,
            title: String,
            artist: String,
            album: String,
            genre: String,
            duration: Long,
            durationUnit: TimeUnit,
            musicFilename: String,
            albumArtResId: Int,
            albumArtResName: String) {
        // 音频数据
        music[mediaId] = getMediaMetadataCompat(mediaId, album, artist, duration, durationUnit, genre, albumArtResName, title)
        // 图片资源
        albumRes[mediaId] = albumArtResId
        // 音频名称
        musicFileName[mediaId] = musicFilename
    }

    fun getMediaMetadataCompat(mediaId: String = "",
                                       album: String = "",
                                       artist: String = "",
                                       duration: Long = 0L,
                                       durationUnit: TimeUnit,
                                       genre: String= "",
                                       albumArtResName: String= "",
                                       title: String= ""): MediaMetadataCompat{
        return MediaMetadataCompat.Builder()
                .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, mediaId)
                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, album)
                .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, artist)
                .putLong(MediaMetadataCompat.METADATA_KEY_DURATION,
                        TimeUnit.MILLISECONDS.convert(duration, durationUnit))
                .putString(MediaMetadataCompat.METADATA_KEY_GENRE, genre)
                .putString(
                        MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI,
                        getAlbumArtUri(albumArtResName))
                .putString(
                        MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI,
                        getAlbumArtUri(albumArtResName))
                .putString(MediaMetadataCompat.METADATA_KEY_TITLE, title)
                .build()
    }
}