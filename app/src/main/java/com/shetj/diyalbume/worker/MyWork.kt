package com.shetj.diyalbume.worker

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import timber.log.Timber

class MyWork(context: Context,workerParameters: WorkerParameters) :Worker(context,workerParameters){


    override fun doWork(): Result {
        val infoPut = this.inputData.getString("infoPut")
        val info = this.inputData.getString("info")
        Timber.d("infoPut = $infoPut  && info = $info")
        return Result.success(getOutDate(info))
    }


    fun getOutDate(info: String?): Data {
        val outData = Data.Builder()
        outData.putString("outData","测试 = "+ System.currentTimeMillis())
        outData.putString("info",info)
        return outData.build()
    }
}