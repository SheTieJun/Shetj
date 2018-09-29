package com.shetj.diyalbume

import android.app.Application
import android.content.Context
import android.content.Intent
import android.support.multidex.MultiDex
import com.devyok.ipc.ServiceManager
import com.devyok.ipc.utils.LogControler
import com.taobao.sophix.SophixManager
import me.shetj.base.s
import me.shetj.bdmap.BMapManager
import me.shetj.fresco.FrescoUtils
import me.shetj.tencentx5.X5CorePreLoadService

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
        ServiceManager.init(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}