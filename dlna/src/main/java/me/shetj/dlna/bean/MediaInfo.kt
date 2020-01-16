package me.shetj.dlna.bean

/**
 * Description：多媒体信息
 * <BR></BR>
 * Creator：yankebin
 * <BR></BR>
 * CreatedAt：2019-07-09
 */
class MediaInfo {
    var mediaName: String? = null
    var mediaId: String? = null
    var mediaType = TYPE_UNKNOWN
    var uri: String? = null
    var filePath: String? = null
    var duration: Long = 0
    var bulbulName: String? = null
    var theAlbumName: String? = null
    var index = 0

    constructor(mediaName: String?, mediaType: Int, uri: String?, duration: Long) {
        this.mediaName = mediaName
        this.mediaType = mediaType
        this.uri = uri
        this.duration = duration
    }


    companion object {
        const val TYPE_UNKNOWN = 0
        const val TYPE_IMAGE = 1
        const val TYPE_VIDEO = 2
        const val TYPE_AUDIO = 3


    }

}