package com.shetj.diyalbume.jobscheduler

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.ComponentName
import android.content.Intent
import android.os.*
import com.shetj.diyalbume.jobscheduler.JobUtils.MESSENGER_INTENT_KEY
import com.shetj.diyalbume.jobscheduler.JobUtils.WORK_DURATION_KEY
import me.shetj.base.base.BaseModel
import me.shetj.base.base.BasePresenter
import me.shetj.base.base.IView
import me.shetj.base.tools.app.ArmsUtils.Companion.getMessage
import me.shetj.base.tools.json.GsonKit
import timber.log.Timber


class JobSchedulerPresenter(view:IView) :BasePresenter<BaseModel>(view){


    private var jobId: Int = 1
    private var handler: Handler

    private var info = ""

    init {
       handler = @SuppressLint("HandlerLeak")
       object :  Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                Timber.i(GsonKit.objectToJson(msg))
                info = StringBuilder(info).append("\n jobId = ${msg.obj}  and  start or close = ${when(msg.what){
                1 -> "start"
                else -> "close"
                }} ").toString()
                view.updateView(getMessage(1, info))

            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun startJob() {
        //参数
        val persistableBundle = PersistableBundle()
        persistableBundle.putInt(WORK_DURATION_KEY,10000)
        //获取组件的名称
        val componentName = ComponentName(view.rxContext.packageName,MyJobService::class.java.name)
        //获取任务
        val jobInfo = JobUtils.getJobInfo(jobId++, persistableBundle, componentName)
        //开始执行任务
        val result = JobUtils.schedule(view.rxContext, jobInfo)
        Timber.i( "jobId = $jobId  and  result:$result")
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun startService() {
        val intent = Intent(view.rxContext,MyJobService::class.java)
        val messenger = Messenger(handler)
        intent.putExtra(MESSENGER_INTENT_KEY,messenger)
        view.rxContext.startService(intent)
    }


    private fun stopService(){
        val intent = Intent(view.rxContext,MyJobService::class.java)
        view.rxContext.stopService(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService()
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun cancel() {
        JobUtils.cancelAllJob(view.rxContext)
    }
}
