package com.shetj.diyalbume.aidltest

import android.app.Service
import android.content.Intent
import android.os.IBinder

import com.shetj.diyalbume.IMyAidlInterface
import com.shetj.diyalbume.main.view.Main3Activity

class MyService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return MyBinder()
    }

    internal inner class MyBinder : IMyAidlInterface.Stub() {

        override fun getName(): String {
            //通过AIDL 启动APP
            startActivity(Intent(baseContext, Main3Activity::class.java))
            return "AIDL-test：getPackageName:$packageName"
        }
    }

}
