package com.shetj.diyalbume.worker

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.work.*
import com.shetj.diyalbume.R
import kotlinx.android.synthetic.main.activity_worker.*
import me.shetj.base.tools.json.GsonKit
import timber.log.Timber
import java.lang.StringBuilder
import java.util.concurrent.TimeUnit

class WorkerActivity : AppCompatActivity() {
    var info = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_worker)

        addWorker.setOnClickListener {
            startOneWork()
        }
        addPeriodicWorkRequest.setOnClickListener {
            startPeriodicWork()
        }

    }


    fun getConstraints(): Constraints {
        val constraints = Constraints.Builder()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            constraints.setRequiresDeviceIdle(true)
        }
        constraints.setRequiredNetworkType(NetworkType.NOT_ROAMING)//不是漫游网络
        constraints.setRequiresBatteryNotLow(true)//设备电池不低于临界值
        constraints.setRequiresStorageNotLow(true)//设置存储必须要有空间
        constraints.setRequiresCharging(true)//网络状态

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            constraints.addContentUriTrigger()
        }

        return constraints.build()
    }


    fun getData(info :String): Data {
        val data = Data.Builder()
        data.putString("infoPut","helloWord")
        data.putString("info",info)
        return data.build()
    }


    fun getOneWork(): OneTimeWorkRequest {
        return OneTimeWorkRequestBuilder<MyWork>().setConstraints(getConstraints())
                .setInputData(getData("getOneWork"))
                .addTag("getOneWork")
                .build()
    }


    fun getPeriodicWork(): PeriodicWorkRequest {

        return PeriodicWorkRequestBuilder<MyWork>(15,TimeUnit.MINUTES)
                .setConstraints(getConstraints())
                .setInputData(getData("getPeriodicWork"))
                .addTag("getPeriodicWork")
                .build()
    }


    fun startOneWork(){

        val oneWork = getOneWork()



        WorkManager.getInstance().apply {
            enqueue(oneWork)


        }
        WorkManager.getInstance().getWorkInfoByIdLiveData(oneWork.id)
                .observe(this, Observer<WorkInfo> {
                    Timber.d(GsonKit.objectToJson(it))
                        if (it.state.isFinished) {
                            it.showInfo()
                        }
                })
    }

    fun startPeriodicWork(){
        val periodicWork = getPeriodicWork()
        WorkManager.getInstance().apply { enqueue(periodicWork) }

        WorkManager.getInstance().getWorkInfoByIdLiveData(periodicWork.id)
                .observe(this, Observer<WorkInfo> {
                    it.apply {
                        if (state.isFinished) {
                            showInfo()
                        }
                    }
                })
    }

    private fun WorkInfo.showInfo() {
        runOnUiThread{
            info = StringBuilder(info).append(GsonKit.objectToJson(this)+"\n").toString()
            tv_msg.text = info
        }
    }



    fun cancel(){
        WorkManager.getInstance().cancelAllWork()
//        WorkManager.getInstance().cancelAllWorkByTag("getOneWork")
//        WorkManager.getInstance().cancelAllWorkByTag("getPeriodicWork")
//        WorkManager.getInstance().cancelWorkById()
//        WorkManager.getInstance().cancelUniqueWork()
    }
}