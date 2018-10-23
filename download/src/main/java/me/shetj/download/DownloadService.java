package me.shetj.download;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;

import me.shetj.base.tools.app.AppUtils;
import timber.log.Timber;

/**
 * app下载更新
 * install(Context appContext,
 *   String versionName,
 *   String appName,
 *   String downloadUrl)
 * @author shetj
 */
public class DownloadService extends Service {

    //--- Private attributes -----------------------------------------------------------------------

    private static final String EXTRA_DOWNLOAD_VERSION  = "com.shetj.me.DOWNLOAD_VERSION";
    private static final String EXTRA_DOWNLOAD_APK_NAME = "com.shetj.me.DOWNLOAD_APK_NAME";
    private static final String EXTRA_DOWNLOAD_APK_URL  = "com.shetj.me.DOWNLOAD_APK_URL";

    private static final String APK_SUFFIX = "download-";

    private static DownloadManager mDm;
    private BroadcastReceiver mReceiver;
    private long mDownLoadAPKId = -1;
    private String mVersionName;
    private String mNewestAppName;
    private String mDownloadUrl;

    private boolean mIsDownloading = false;

    //--- Override methods -------------------------------------------------------------------------

    @Override
    public void onCreate() {
        super.onCreate();

        mDm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Timber.d(  "start download service");

        if (mIsDownloading) {
            Timber.d(  "download apk task is running, skip...");
        } else {
            mReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
                    if (id == mDownLoadAPKId) {
                        if (null != mReceiver) {
                            unregisterReceiver(mReceiver);
                        }

                        mIsDownloading = false;
                        installApk(context, mNewestAppName);
                        cleanUpOldApkThan(mVersionName);
                        stopSelf();
                    }
                }
            };

            registerReceiver(mReceiver, new IntentFilter( DownloadManager.ACTION_DOWNLOAD_COMPLETE));
            mVersionName = intent.getStringExtra(EXTRA_DOWNLOAD_VERSION);
            mNewestAppName = intent.getStringExtra(EXTRA_DOWNLOAD_APK_NAME);
            mDownloadUrl = intent.getStringExtra(EXTRA_DOWNLOAD_APK_URL);
            startDownLoad();
        }
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //--- Public static methods --------------------------------------------------------------------

    public static void install(Context appContext,@NonNull String versionName,
                               @NonNull String appName,
                               @NonNull String downloadUrl) {
        if (hasDownloadedApk(appName)) {
            installApk(appContext, appName);
        } else {
            Intent updateApkService = new Intent(appContext, DownloadService.class);
            updateApkService.putExtra(EXTRA_DOWNLOAD_VERSION, versionName);
            updateApkService.putExtra(EXTRA_DOWNLOAD_APK_NAME, appName);
            updateApkService.putExtra(EXTRA_DOWNLOAD_APK_URL, downloadUrl);
            appContext.startService(updateApkService);
        }
    }

    public static boolean hasDownloadedApk(@NonNull String apkName) {
        String apkPath = getDownloadedApkPath(apkName);
        File apkFile = new File(apkPath);
        return apkFile.exists();
    }

    public static void installApk(@NonNull Context context, @NonNull String apkName) {
        String path = getDownloadedApkPath(apkName);
        Timber.i(path+context.getPackageName());
        AppUtils.installApp(new File(path),context.getPackageName()+".FileProvider");
    }

    public static String getApkName(@NonNull String versionName, @NonNull String fileName) {
        return (APK_SUFFIX + versionName + '-' + fileName);
    }

    //--- Private methods --------------------------------------------------------------------------

    private void startDownLoad() {
        Timber.d( "start download apk");
        DownloadManager.Request request = new DownloadManager.Request(
                Uri.parse(mDownloadUrl));
        request.setMimeType("application/vnd.android.package-archive");
        request.setAllowedNetworkTypes(
                 DownloadManager.Request.NETWORK_MOBILE |  DownloadManager.Request.NETWORK_WIFI);
        request.setTitle(mNewestAppName);
        request.setNotificationVisibility(
                 DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, mNewestAppName);
        mDownLoadAPKId = mDm.enqueue(request);
        mIsDownloading = true;
    }

    //--- Private static methods -------------------------------------------------------------------

    private static String getDownloadDir() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .getAbsolutePath();
    }

    private static String getDownloadedApkPath(@NonNull String apkName) {
        return (getDownloadDir() + File.separator + apkName);
    }

    private static void cleanUpOldApkThan(String newestVersion) {
        File downloadDir = new File(getDownloadDir());
        //noinspection ConstantConditions
        if (null != downloadDir && downloadDir.isDirectory()) {
            File[] files = downloadDir.listFiles();
            for (File file : files) {
                if (file.getName().startsWith(APK_SUFFIX)) {
                    if (!file.getName().contains(newestVersion)) {
                        boolean isDeleted = file.delete();
                        Timber.v( "cleanUpOldApkThan: " + isDeleted
                                + ": " + file.getAbsolutePath());
                    }
                }
            }
        }
    }
}