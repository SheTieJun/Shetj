package com.shetj.diyalbume.jobscheduler

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.Build
import android.os.PersistableBundle
import androidx.annotation.RequiresApi

/**
 * **@author：** shetj<br></br>
 * **@createTime：** 2019/3/17<br></br>
 * **@email：** 375105540@qq.com<br></br>
 * **@describe**
 * [android.app.job.JobService]
 * [android.app.job.JobScheduler]
 * [android.app.job.JobInfo] <br></br>
 * 需要权限
 * <pre class="prettyprint"> android:permission="android.permission.BIND_JOB_SERVICE </pre>
 * <br></br>schedule() 定义：安排一个Job任务。
 * <br></br>enqueue() 定义：安排一个Job任务，但是可以将一个任务排入队列。
 * <br></br>cancel() 定义：取消一个执行ID的Job。
 * <br></br>cancelAll()定义：取消该app所有的注册到JobScheduler里的任务。
 * <br></br>getAllPendingJobs() 定义：获取该app所有的注册到JobScheduler里未完成的任务列表。
 * <br></br>getPendingJob() 定义：按照ID检索获得JobScheduler里未完成的该任务的JobInfo信息。
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
object JobUtils {

    val WORK_DURATION_KEY = "WORK_DURATION_KEY"
    val MESSENGER_INTENT_KEY = "MESSENGER_INTENT_KEY"

    private var scheduler: JobScheduler? = null

    fun getJobScheduler(context: Context): JobScheduler {
        if (scheduler == null) {
            scheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        }
        return scheduler as JobScheduler
    }

    /**
     * 构建一个[JobInfo]
     * @param JobId  job_id
     * @param extras   PersistableBundle extras = new PersistableBundle()
     * @param mServiceComponent ComponentName	mServiceComponent = new ComponentName( context.getPackageName(), MyJobService.class.getName() );
     * @return [JobInfo]
     */
    fun getJobInfo(JobId: Int, extras: PersistableBundle, mServiceComponent: ComponentName): JobInfo {
        val builder = JobInfo.Builder(JobId, mServiceComponent)
        //设置至少延迟多久后执行，单位毫秒.
        builder.setMinimumLatency(1000)
        //设置最多延迟多久后执行，单位毫秒。
        builder.setOverrideDeadline(5000)
        //JobInfo.NETWORK_TYPE_NONE（无网络时执行，默认）、
        //JobInfo.NETWORK_TYPE_ANY（有网络时执行）、
        //JobInfo.NETWORK_TYPE_UNMETERED（网络无需付费时执行）
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
        //是否在空闲时执行
        builder.setRequiresDeviceIdle(false)
        //是否在充电时执行
        builder.setRequiresCharging(false)
        builder.setExtras(extras)
        return builder.build()
    }

    /**
     * 安排一个任务
     * @param context
     * @param jobInfo
     * @return result返回1是成功，0是失败
     */
    fun schedule(context: Context, jobInfo: JobInfo): Int {
        return getJobScheduler(context).schedule(jobInfo)
    }


    /**
     * 取消执行 ID 的任务
     * @param context
     * @param jobId
     */
    fun cancelJob(context: Context, jobId: Int) {
        getJobScheduler(context).cancel(jobId)
    }


    /**
     * 取消所有的任务
     * @param context
     */
    fun cancelAllJob(context: Context) {
        getJobScheduler(context).cancelAll()
    }

    fun enqueueJob(context: Context, jobInfo: JobInfo) {

    }

    /**
     * 通过JobId 获取JobInfo
     * @param context
     * @param jobId
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    fun getPendingJob(context: Context, jobId: Int): JobInfo? {
        return getJobScheduler(context).getPendingJob(jobId)
    }

    /**
     * 获取该app所有的注册到JobScheduler里未完成的任务列表
     * @param context
     * @return
     */
    fun getAllPendingJobs(context: Context): List<JobInfo> {
        return getJobScheduler(context).allPendingJobs
    }


}
