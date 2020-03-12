package com.shetj.diyalbume.ppttest

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadListener
import com.liulishuo.filedownloader.FileDownloader
import com.liulishuo.filedownloader.util.FileDownloadUtils
import com.shetj.diyalbume.R
import com.tencent.smtt.sdk.QbSdk
import com.tencent.smtt.sdk.TbsReaderView
import com.zhouyou.http.EasyHttp
import com.zhouyou.http.callback.DownloadProgressCallBack
import com.zhouyou.http.exception.ApiException
import kotlinx.android.synthetic.main.activity_x5_reader.*
import me.shetj.base.kt.toMD5
import me.shetj.base.tools.file.SDCardUtils
import me.shetj.download.base.TasksManager
import timber.log.Timber
import java.io.File

class X5ReaderActivity : AppCompatActivity() {
    private var mTbsReaderView : TbsReaderView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_x5_reader)
        initTbsReader()
    }

    fun initTbsReader(){
        if (!QbSdk.isTbsCoreInited()) {
            QbSdk.preInit(applicationContext, null)
        }
        QbSdk.initX5Environment(applicationContext, null)

        mTbsReaderView =  TbsReaderView(this, TbsReaderView.ReaderCallback { p0, p1, p2 ->

        })
        prview.addView(mTbsReaderView,   FrameLayout.LayoutParams(-1, -1))
        val filePah =  "https://media.lycheer.net/material/6583/5e6851502b69bd50585a4318.pdf"
        val fileType = filePah.substring(filePah.lastIndexOf(".") + 1)
        //taskID

        val savePath= SDCardUtils.cache+"/${filePah.toMD5() + "." + fileType}"

        EasyHttp.downLoad(filePah)
                .saveName(filePah.toMD5() + "." + fileType)
                .execute(object : DownloadProgressCallBack<String>() {
                    override fun onSuccess(t: String?) {

                    }

                    override fun onError(e: ApiException?) {
                    }

                    override fun update(bytesRead: Long, contentLength: Long, done: Boolean) {
                    }

                    override fun onComplete(path: String?) {
                        mTbsReaderView?.openFile(path)
                    }

                    override fun onStart() {
                    }
                })
    }

    fun TbsReaderView?.openFile(filePah:String?){
        if (filePah == null) return
        if (null == this ) return
        val tbsReaderTemp =   SDCardUtils.cache + "/TbsReaderTemp"
        val bundle =   Bundle()
        bundle.putString("filePath", filePah)
        bundle.putString("tempPath", tbsReaderTemp)
        val fileType = filePah.substring(filePah.lastIndexOf(".") + 1)
        val result = preOpen(fileType, false)
        if (result) {
            openFile(bundle)
        } else {
            Timber.i("open error")
        }
    }

    override fun onDestroy() {

        mTbsReaderView?.onStop()
        mTbsReaderView?.destroyDrawingCache()
        super.onDestroy()

    }
}


