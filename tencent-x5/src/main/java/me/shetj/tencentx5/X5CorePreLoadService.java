package me.shetj.tencentx5;

import android.app.IntentService;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.Nullable;

import com.tencent.smtt.sdk.QbSdk;

import timber.log.Timber;

/**
 * 启动下载x5 在App中执行
 * 不能后台启动
 * @author shetj
 */
public class X5CorePreLoadService extends IntentService {
    private static final String TAG = X5CorePreLoadService.class.getSimpleName();

    private long time = 0;

    public X5CorePreLoadService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        time = System.currentTimeMillis();
        initX5();
    }

    /**
     * 初始化X5内核
     */
    private void initX5() {
        Timber.i("init-x5");
        if (!QbSdk.isTbsCoreInited()) {
            QbSdk.preInit(getApplicationContext(), null);
        }
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

        @Override
        public void onViewInitFinished(boolean arg0) {
            Timber.i("X5=%s", String.valueOf(System.currentTimeMillis()- time) );
        }

        @Override
        public void onCoreInitFinished() {
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}