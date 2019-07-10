package com.shetj.diyalbume.jobscheduler

import android.app.Service
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Message
import android.os.Messenger
import android.os.RemoteException
import androidx.annotation.RequiresApi

import timber.log.Timber

import com.shetj.diyalbume.jobscheduler.JobUtils.MESSENGER_INTENT_KEY
import com.shetj.diyalbume.jobscheduler.JobUtils.WORK_DURATION_KEY


/**
 * 1、[JobService] 是[android.app.job.JobScheduler]的回调 <br></br>
 * 2、需要我们重新覆写[.onStartJob],里面是实际实现的任务逻辑 <br></br>
 * 3、因为是在*主线程*中响应的，所有必须考虑一些异步的任务 <br></br>
 * 4、执行结束需要自己掉用 [.jobFinished]<br></br>
 * <pre class="prettyprint">
 * Intent startServiceIntent = new Intent(this, MyJobService.class);
 * Messenger messengerIncoming = new Messenger(mHandler);
 * startServiceIntent.putExtra(MESSENGER_INTENT_KEY, messengerIncoming);
 * startService(startServiceIntent);
</pre> *
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
class MyJobService : JobService() {


    private var mActivityMessenger: Messenger? = null

    /**
     * Service被初始化后的回调。<br></br>
     * 可以在这里设置BroadcastReceiver或者ContentObserver<br></br>
     */
    override fun onCreate() {
        super.onCreate()
        Timber.i(TAG, "Service created")
    }

    /**
     * Service被销毁前的回调。<br></br>
     * 可以在这里注销BroadcastReceiver或者ContentObserver<br></br>
     */
    override fun onDestroy() {
        super.onDestroy()
        Timber.i(TAG, "Service destroyed")
    }

    /**
     * When the app's MainActivity is created, it starts this service. This is so that the
     * activity and this service can communicate back and forth. See "setUiCallback()"
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            mActivityMessenger = intent.getParcelableExtra(MESSENGER_INTENT_KEY)
        }
        return Service.START_NOT_STICKY
    }

    /**
     * 时间执行的任务
     * @param params  参数
     * @return   Return true as there's more work to be done with this job.
     */
    override fun onStartJob(params: JobParameters): Boolean {
        // The work that this service "does" is simply wait for a certain duration and finish
        // the job (on another thread).

        sendMessage(MSG_COLOR_START, params.jobId)

        val duration = params.extras.getInt(WORK_DURATION_KEY)

        // Uses a handler to delay the execution of jobFinished().
        val handler = Handler()
        handler.postDelayed({
            sendMessage(MSG_COLOR_STOP, params.jobId)
            jobFinished(params, false)
        }, duration.toLong())
        Timber.i(TAG, "on start job: %s", params.jobId)
        return true
    }

    /**
     * 停止job,<br></br>
     * 当JobScheduler发觉该Job条件不满足的时候，或者job被抢占被取消的时候的强制回调。
     * @param params
     * @return true 表示：“任务应该计划在下次继续。 false to drop the job.
     */
    override fun onStopJob(params: JobParameters): Boolean {
        sendMessage(MSG_COLOR_STOP, params.jobId)
        Timber.i(TAG, "on stop job: %s", params.jobId)
        return false
    }

    private fun sendMessage(messageID: Int, params: Any?) {
        if (mActivityMessenger == null) {
            Timber.d(TAG, "Service is bound, not started. There's no callback to send a message to.")
            return
        }
        val m = Message.obtain()
        m.what = messageID
        m.obj = params
        try {
            mActivityMessenger!!.send(m)
        } catch (e: RemoteException) {
            Timber.e(TAG, "Error passing service object back to activity.")
        }

    }

    companion object {
        private val TAG = MyJobService::class.java.simpleName
        private val MSG_COLOR_START = 1
        private val MSG_COLOR_STOP = 2
    }
}