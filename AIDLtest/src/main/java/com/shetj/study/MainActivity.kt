package com.shetj.study

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.shetj.diyalbume.IMyAidlInterface
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var iMyAidlInterface: IMyAidlInterface? =null
    private val conn =object : ServiceConnection{
        override fun onServiceDisconnected(name: ComponentName?) {
            Log.i("xx","连接断开.....")
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            //连接成功
            Log.i("xx","连接.....")
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent("com.shetj.MyService")
        intent.`package` = "com.shetj.diyalbume"
        bindService(intent,conn, BIND_AUTO_CREATE)
        tv_test_msg.setOnClickListener{
            tv_test_msg.text = iMyAidlInterface?.name
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(conn)
    }
}
