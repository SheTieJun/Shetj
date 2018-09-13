package com.shetj.diyalbume

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.devyok.ipc.ServiceManager
import com.devyok.ipc.utils.LogControler
import me.shetj.alihotfix.HotFix
import me.shetj.base.http.easyhttp.EasyHttpUtils
import me.shetj.base.s

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
        HotFix.onCreateInit()
        s.init(this,BuildConfig.DEBUG,"http://baidu.com/")
        ServiceManager.init(this)
        if(BuildConfig.DEBUG){
            LogControler.enableDebug()
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
        HotFix.init(this,BuildConfig.DEBUG,"57886dc7c9aa8e86747b324999f7ec8d ")
    }
}