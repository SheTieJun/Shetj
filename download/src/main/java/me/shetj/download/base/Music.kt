package me.shetj.download.base

import android.text.TextUtils
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloader
import com.liulishuo.filedownloader.download.ProcessCallback
import com.liulishuo.filedownloader.util.FileDownloadUtils
import java.io.File
import java.lang.NullPointerException

class Music {
    var url:String?=null
    var title :String ?=null
    var imgUrl:String ?=null
    var duration:String ? = null
    var isLoading = false

    @JvmOverloads
    constructor( name: String?,url: String?= null,imgUrl:String? = null) {
        this.url = url
        this.title = name
        this.imgUrl = imgUrl
    }
    constructor()
}

fun Music.downloadMusic(callBack: ProcessCallback){
    if (!TextUtils.isEmpty(url)){
        val defaultSaveFilePath = FileDownloadUtils.getDefaultSaveFilePath(url)

        val task = FileDownloader.getImpl().create(url)
                .setPath(defaultSaveFilePath)
                .setAutoRetryTimes(5)
                .setCallbackProgressTimes(100)
                .setListener(object :DownloadSampleListener(){
                    override fun progress(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
                        super.progress(task, soFarBytes, totalBytes)
                        callBack.onProgress((soFarBytes*100/totalBytes).toLong())
                    }

                    override fun completed(task: BaseDownloadTask?) {
                        super.completed(task)
                        callBack.onCompleted(null, 0,0)
                    }
                })
    }else{
        callBack.onError(NullPointerException("url is Null"))
    }

}

fun Music.isDownload( ): Boolean {
    url?.let {
        return if (url!!.startsWith("http",ignoreCase = true)){
            val defaultSaveFilePath = FileDownloadUtils.getDefaultSaveFilePath(url)
            File(defaultSaveFilePath).exists()
        }else{
            true
        }
    }
    return true
}


fun Music.getCacheUrl():String?{
    url?.let {
        return if (isDownload() &&url!!.startsWith("http",ignoreCase = true)){
            FileDownloadUtils.getDefaultSaveFilePath(url)
        }else{
            url
        }
    }
    return url
}
