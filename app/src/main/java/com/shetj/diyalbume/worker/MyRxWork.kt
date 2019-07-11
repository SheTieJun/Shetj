package com.shetj.diyalbume.worker

import android.content.Context
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import io.reactivex.Single
import timber.log.Timber

class MyRxWork(context: Context,workerParameters: WorkerParameters) :RxWorker(context,workerParameters){


    override fun createWork(): Single<Result> {
        val infoPut = this.inputData.getString("infoPut")
        val info = this.inputData.getString("info")
        Timber.d("infoPut = $infoPut  && info = $info")
        return  Single.just(Result.success())
    }

}