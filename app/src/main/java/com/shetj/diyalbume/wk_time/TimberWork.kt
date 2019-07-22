package com.shetj.diyalbume.wk_time

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class TimberWork (context: Context,workerParameters:WorkerParameters):Worker(context,workerParameters){

    override fun doWork(): Result {


        return Result.success()
    }

}