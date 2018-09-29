package me.shetj.tencentx5;

import android.annotation.TargetApi;
import android.graphics.Bitmap;

import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.tencent.sonic.sdk.SonicSession;

import me.shetj.base.tools.time.TimeUtil;
import timber.log.Timber;

public class X5WebViewClient extends WebViewClient {
    private SonicSession sonicSession;
    private long time = 0;
    public X5WebViewClient(SonicSession sonicSession) {
        this.sonicSession  = sonicSession ;
    }

	@Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return false;
    }

    @Override
    public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
        super.onPageStarted(webView, s, bitmap);
        Timber.i("onPageStarted = %s",time =  TimeUtil.getTime());
    }

    @TargetApi(21)
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        return shouldInterceptRequest(view, request.getUrl().toString());
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        if (sonicSession != null) {
            return (WebResourceResponse) sonicSession.getSessionClient().requestResource(url);
        }
        return null;
    }
    @Override
    public void onPageFinished(WebView webView, String s) {
        super.onPageFinished(webView, s);
        if (sonicSession != null) {
            sonicSession.getSessionClient().pageFinish(s);
        }
        Timber.i("onPageFinished = %s", TimeUtil.getTime()-time);
    }
}