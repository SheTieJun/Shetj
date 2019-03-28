package com.shetj.diyalbume.jobscheduler;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.annotation.RequiresApi;

/**
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2019/3/17<br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b>  <br>
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobUtils {

	private static int mJobId =1;
	public static final String WORK_DURATION_KEY = "WORK_DURATION_KEY";
	public static final String MESSENGER_INTENT_KEY = "MESSENGER_INTENT_KEY";
	public static JobScheduler getJobScheduler(Context context){
		JobScheduler tm = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
		return tm;
	}


	/**
	 *
	 * @param context 上下文
	 * @param extras PersistableBundle extras = new PersistableBundle()
	 * @param mServiceComponent 		ComponentName	mServiceComponent = new ComponentName( context.getPackageName(), MyJobService.class.getName() );
	 * @return
	 */
	public static JobInfo getJobInfo(Context context,	PersistableBundle extras,ComponentName mServiceComponent){
		JobInfo.Builder builder = new JobInfo.Builder(mJobId++, mServiceComponent);
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


}
