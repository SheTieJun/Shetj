package me.shetj.base.download

import android.app.Application
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import zlc.season.rxdownload3.RxDownload
import zlc.season.rxdownload3.core.DownloadConfig
import zlc.season.rxdownload3.core.Status
import zlc.season.rxdownload3.extension.ApkInstallExtension
import zlc.season.rxdownload3.http.OkHttpClientFactoryImpl
import zlc.season.rxdownload3.notification.NotificationFactoryImpl

/**
 * **@packageName：** me.shetj.base.Download<br></br>
 * **@author：** shetj<br></br>
 * **@createTime：** 2018/1/9<br></br>
 * **@company：**<br></br>
 * **@email：** 375105540@qq.com<br></br>
 * **@describe**<br></br>
 */

object  RxDownloadUtils{
    fun init(app : Application) {
         val builder = DownloadConfig.Builder.create(app)
                 .setFps(20)                         //设置更新频率
                 .enableAutoStart(true)              //自动开始下载
                 .setDefaultPath("custom download path")     //设置默认的下载地址
                 .enableDb(true)                             //启用数据库
//                 .enableService(true)                        //启用Service
                 .enableNotification(true)                   //启用Notification
                 .setNotificationFactory(NotificationFactoryImpl())        //自定义通知
                 .setOkHttpClientFacotry(OkHttpClientFactoryImpl())        //自定义OKHTTP
                 .addExtension(ApkInstallExtension::class.java)          //添加扩展
         DownloadConfig.init(builder)
    }

    fun statrDownload(url : String) : Flowable<Status>? {
        return RxDownload.create(url)
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun stopDownload(url: String){
        RxDownload.stop(url).subscribe()
    }

    fun stopAll(){
        RxDownload.stopAll()
    }

    fun startAll(){
        RxDownload.startAll()
    }



}
