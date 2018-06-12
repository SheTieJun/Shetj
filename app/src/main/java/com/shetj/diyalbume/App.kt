package com.shetj.diyalbume

import android.app.Application
import com.devyok.ipc.ServiceManager
import com.devyok.ipc.utils.LogControler
import me.shetj.base.http.rxEasyHttp.EasyHttpUtils
import me.shetj.base.http.xutils.XUtil

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
        XUtil.init(this,BuildConfig.DEBUG)
        EasyHttpUtils.init(this,BuildConfig.DEBUG,"http://baidu.com/")
        ServiceManager.init(this)
        if(BuildConfig.DEBUG){
            LogControler.enableDebug()
        }
    }
}