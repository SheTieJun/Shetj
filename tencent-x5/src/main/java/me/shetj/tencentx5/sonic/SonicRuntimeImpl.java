package me.shetj.tencentx5.sonic;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.sonic.sdk.SonicRuntime;
import com.tencent.sonic.sdk.SonicSessionClient;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import me.shetj.tencentx5.BuildConfig;
import timber.log.Timber;

public class SonicRuntimeImpl extends SonicRuntime {

    public SonicRuntimeImpl(Context context) {
        super(context);
    }

    public static final String USER_AGENT = String.format(
            " Lycheer/%s SystemName/%s SystemVersion/%s Device/%s   Language/%s  SdkVersion/%d Flavor/%s LycheerVersion/%d",
            BuildConfig.VERSION_NAME,
            "Android",
            Build.VERSION.RELEASE,
            Build.MODEL,
            Locale.getDefault().getLanguage() + "_" + Locale.getDefault().getCountry(),
            Build.VERSION.SDK_INT,
            BuildConfig.FLAVOR,
            BuildConfig.VERSION_CODE);

    /**
     * 获取用户UA信息
     * @return
     */
    @Override
    public String getUserAgent() {
        return USER_AGENT;
    }

    /**
     * 获取用户ID信息
     * @return
     */
    @Override
    public String getCurrentUserAccount() {
        return "me.shetj.sonic&tencentx5";
    }

    @Override
    public String getCookie(String url) {
        CookieManager cookieManager = CookieManager.getInstance();
        return cookieManager.getCookie(url);
    }

    @Override
    public void log(String tag, int level, String message) {
        switch (level) {
            case Log.ERROR:
                Timber.e(tag, message);
                break;
            case Log.INFO:
                Timber.i(tag, message);
                break;
            default:
                Timber.d(tag, message);
        }
    }

    @Override
    public Object createWebResourceResponse(String mimeType, String encoding, InputStream data, Map<String, String> headers) {
        WebResourceResponse resourceResponse =  new WebResourceResponse(mimeType, encoding, data);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            resourceResponse.setResponseHeaders(headers);
        }
        return resourceResponse;
    }

    @Override
    public void showToast(CharSequence text, int duration) {

    }

    @Override
    public void notifyError(SonicSessionClient client, String url, int errorCode) {

    }

    @Override
    public boolean isSonicUrl(String url) {
        return true;
    }

    @Override
    public boolean setCookie(String url, List<String> cookies) {
        if (!TextUtils.isEmpty(url) && cookies != null && cookies.size() > 0) {
            CookieManager cookieManager = CookieManager.getInstance();
            for (String cookie : cookies) {
                cookieManager.setCookie(url, cookie);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean isNetworkValid() {
        return true;
    }

    @Override
    public void postTaskToThread(Runnable task, long delayMillis) {
        Thread thread = new Thread(task, "SonicThread");
        thread.start();
    }

    @Override
    public File getSonicCacheDir() {
        if (BuildConfig.DEBUG) {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "sonic/";
            File file = new File(path.trim());
            if(!file.exists()){
                file.mkdir();
            }
            return file;
        }
       return super.getSonicCacheDir();
    }

    @Override
    public String getHostDirectAddress(String url) {
        return null;
    }
}