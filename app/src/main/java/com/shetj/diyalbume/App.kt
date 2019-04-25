package com.shetj.diyalbume

import android.app.Application
import android.content.Context
import android.content.Intent
import android.support.multidex.MultiDex
import com.devyok.ipc.utils.LogControler
import com.liulishuo.filedownloader.FileDownloader
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection
import com.liulishuo.filedownloader.util.FileDownloadLog
import com.taobao.sophix.SophixManager
import me.shetj.base.s
import me.shetj.bdmap.BMapManager
import me.shetj.fresco.FrescoUtils
import me.shetj.router.RouterUtils
import me.shetj.tencentx5.X5CorePreLoadService
import java.net.Proxy

/**
 *
 * <b>@packageName：</b> com.shetj.diyalbume<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2017/12/4<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        s.init(this,BuildConfig.DEBUG,"http://baidu.com/")
        if(BuildConfig.DEBUG){
            LogControler.enableDebug()
        }
        BMapManager.init(this)
        FrescoUtils.init(this,BuildConfig.DEBUG)
        startService(Intent(this,X5CorePreLoadService::class.java))
        SophixManager.getInstance().queryAndLoadNewPatch()
        RouterUtils.initRouter(this,BuildConfig.DEBUG)
        initFileDownloader()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
    private fun initFileDownloader() {
        FileDownloadLog.NEED_LOG = true
        FileDownloader.setupOnApplicationOnCreate(this)
                .connectionCreator(FileDownloadUrlConnection
                        .Creator(FileDownloadUrlConnection.Configuration()
                        .connectTimeout(15000) // set connection timeout.
                        .readTimeout(15000) // set read timeout.
                        .proxy(Proxy.NO_PROXY) // set proxy
                ))
                .commit()
    }

}