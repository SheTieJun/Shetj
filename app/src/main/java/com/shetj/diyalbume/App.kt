package com.shetj.diyalbume

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.multidex.MultiDex
import com.liulishuo.filedownloader.FileDownloader
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection
import com.liulishuo.filedownloader.util.FileDownloadLog
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
        s.init(this,BuildConfig.DEBUG )
        BMapManager.init(this)
        FrescoUtils.init(this,BuildConfig.DEBUG)
        startService(Intent(this,X5CorePreLoadService::class.java))
        RouterUtils.initRouter(this,BuildConfig.DEBUG)
        FileDownloadLog.NEED_LOG = BuildConfig.DEBUG
        FileDownloader.setupOnApplicationOnCreate(this)
                .connectionCreator(FileDownloadUrlConnection
                        .Creator(FileDownloadUrlConnection.Configuration()
                                .connectTimeout(15000) // set connection timeout.
                                .readTimeout(15000) // set read timeout.
                                .proxy(Proxy.NO_PROXY) // set proxy
                        ))
                .commit()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}