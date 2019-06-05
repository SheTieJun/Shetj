package com.shetj.diyalbume.jobscheduler;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.annotation.RequiresApi;

import java.util.List;

/**
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2019/3/17<br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b>  {@link android.app.job.JobService}
 * {@link android.app.job.JobScheduler}
 * {@link android.app.job.JobInfo} <br>
 *    需要权限
 *   <pre class="prettyprint"> android:permission="android.permission.BIND_JOB_SERVICE </pre>
 * <br>schedule() 定义：安排一个Job任务。
 * <br>enqueue() 定义：安排一个Job任务，但是可以将一个任务排入队列。
 * <br>cancel() 定义：取消一个执行ID的Job。
 * <br>cancelAll()定义：取消该app所有的注册到JobScheduler里的任务。
 * <br>getAllPendingJobs() 定义：获取该app所有的注册到JobScheduler里未完成的任务列表。
 * <br>getPendingJob() 定义：按照ID检索获得JobScheduler里未完成的该任务的JobInfo信息。
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobUtils {

	public static final String WORK_DURATION_KEY = "WORK_DURATION_KEY";
	public static final String MESSENGER_INTENT_KEY = "MESSENGER_INTENT_KEY";

	private static  JobScheduler scheduler;

	public static JobScheduler getJobScheduler(Context context){
		if (scheduler == null){
			scheduler =  (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
		}
		return scheduler;
	}

	/**
	 * 构建一个{@link JobInfo}
	 * @param JobId  job_id
	 * @param extras   PersistableBundle extras = new PersistableBundle()
	 * @param mServiceComponent ComponentName	mServiceComponent = new ComponentName( context.getPackageName(), MyJobService.class.getName() );
	 * @return {@link JobInfo}
	 *
	 */
	public static JobInfo getJobInfo(int JobId,	PersistableBundle extras,ComponentName mServiceComponent){
		JobInfo.Builder builder = new JobInfo.Builder(JobId, mServiceComponent);
		//设置至少延迟多久后执行，单位毫秒.
		builder.setMinimumLatency(1000);
		//设置最多延迟多久后执行，单位毫秒。
		builder.setOverrideDeadline(5000);
		//JobInfo.NETWORK_TYPE_NONE（无网络时执行，默认）、
		//JobInfo.NETWORK_TYPE_ANY（有网络时执行）、
		//JobInfo.NETWORK_TYPE_UNMETERED（网络无需付费时执行）
		builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);
		//是否在空闲时执行
		builder.setRequiresDeviceIdle(false);
		//是否在充电时执行
		builder.setRequiresCharging(false);
		builder.setExtras(extras);
		return builder.build();
	}

	/**
	 * 安排一个任务
	 * @param context
	 * @param jobInfo
	 * @return result返回1是成功，0是失败
	 */
	public static int schedule(Context context, JobInfo jobInfo){
		return getJobScheduler(context).schedule(jobInfo);
	}


	/**
	 * 取消执行 ID 的任务
	 * @param context
	 * @param jobId
	 */
	public static void cancelJob(Context context,int jobId){
		getJobScheduler(context).cancel(jobId);
	}


	/**
	 * 取消所有的任务
	 * @param context
	 */
	public static void cancelAllJob(Context context){
		getJobScheduler(context).cancelAll();
	}

	public static void enqueueJob(Context context,JobInfo jobInfo){

	}

	/**
	 * 通过JobId 获取JobInfo
	 * @param context
	 * @param jobId
	 * @return
	 */
	@RequiresApi(api = Build.VERSION_CODES.N)
	public static JobInfo getPendingJob(Context context, int jobId){
		return  getJobScheduler(context).getPendingJob(jobId);
	}

	/**
	 * 获取该app所有的注册到JobScheduler里未完成的任务列表
	 * @param context
	 * @return
	 */
	public static List<JobInfo> getAllPendingJobs(Context context){
		return getJobScheduler(context).getAllPendingJobs();
	}


}
