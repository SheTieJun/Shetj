package com.shetj.diyalbume.jobscheduler;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import timber.log.Timber;

import static com.shetj.diyalbume.jobscheduler.JobUtils.MESSENGER_INTENT_KEY;
import static com.shetj.diyalbume.jobscheduler.JobUtils.WORK_DURATION_KEY;


/**
 * 1、{@link JobService} 是{@link android.app.job.JobScheduler}的回调 <br>
 * 2、需要我们重新覆写{@link #onStartJob(JobParameters)},里面是实际实现的任务逻辑 <br>
 * 3、因为是在*主线程*中响应的，所有必须考虑一些异步的任务 <br>
 * 4、执行结束需要自己掉用 {@link #jobFinished(JobParameters, boolean)}<br>
 *     <pre class="prettyprint">
 *     Intent startServiceIntent = new Intent(this, MyJobService.class);
 *     Messenger messengerIncoming = new Messenger(mHandler);
 *     startServiceIntent.putExtra(MESSENGER_INTENT_KEY, messengerIncoming);
 *     startService(startServiceIntent);
 *     </pre>
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MyJobService extends JobService {
    private static final String TAG = MyJobService.class.getSimpleName();
    private static final int MSG_COLOR_START = 1;
    private static final int MSG_COLOR_STOP = 2;


    private Messenger mActivityMessenger;

    /**
     * Service被初始化后的回调。<br>
     * 可以在这里设置BroadcastReceiver或者ContentObserver<br>
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.i(TAG, "Service created");
    }

    /**
     * Service被销毁前的回调。<br>
     * 可以在这里注销BroadcastReceiver或者ContentObserver<br>
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.i(TAG, "Service destroyed");
    }

    /**
     * When the app's MainActivity is created, it starts this service. This is so that the
     * activity and this service can communicate back and forth. See "setUiCallback()"
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent!=null) {
            mActivityMessenger = intent.getParcelableExtra(MESSENGER_INTENT_KEY);
        }
        return START_NOT_STICKY;
    }

    /**
     * 时间执行的任务
     * @param params  参数
     * @return   Return true as there's more work to be done with this job.
     */
    @Override
    public boolean onStartJob(final JobParameters params) {
        // The work that this service "does" is simply wait for a certain duration and finish
        // the job (on another thread).

        sendMessage(MSG_COLOR_START, params.getJobId());

        int duration = params.getExtras().getInt(WORK_DURATION_KEY);

        // Uses a handler to delay the execution of jobFinished().
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sendMessage(MSG_COLOR_STOP, params.getJobId());
                jobFinished(params, false);
            }
        }, duration);
        Timber.i(TAG, "on start job: %s", params.getJobId());
        return true;
    }

    /**
     * 停止job,<br>
     * 当JobScheduler发觉该Job条件不满足的时候，或者job被抢占被取消的时候的强制回调。
     * @param params
     * @return true 表示：“任务应该计划在下次继续。 false to drop the job.
     */
    @Override
    public boolean onStopJob(JobParameters params) {
        sendMessage(MSG_COLOR_STOP, params.getJobId());
        Timber.i(TAG, "on stop job: %s", params.getJobId());
        return false;
    }

    private void sendMessage(int messageID, @Nullable Object params) {
        if (mActivityMessenger == null) {
            Timber.d(TAG, "Service is bound, not started. There's no callback to send a message to.");
            return;
        }
        Message m = Message.obtain();
        m.what = messageID;
        m.obj = params;
        try {
            mActivityMessenger.send(m);
        } catch (RemoteException e) {
            Timber.e(TAG, "Error passing service object back to activity.");
        }
    }
}