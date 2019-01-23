package me.shetj.tencentx5;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;

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
        if (!url.startsWith("http:") && !url.startsWith("https:")) {
            Intent intent = null;
            if (url.startsWith("weixin://wap/pay?")) {
                intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                view.getContext().startActivity(intent);
                return true;
            }
            if (parseScheme(url)) {
                try {
                    intent = Intent.parseUri(url,
                            Intent.URI_INTENT_SCHEME);
                    intent.addCategory("android.intent.category.BROWSABLE");
                    intent.setComponent(null);
                    view.getContext().startActivity(intent);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            view.getContext().startActivity(intent);
            return true;
        }else if (url.contains("me.shetj.com")){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            view.getContext().startActivity(intent);
            return true;
        }
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

    private  boolean parseScheme(String url) {
        if (url.contains("platformapi/startApp")) {
            return true;
        }
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
                && (url.contains("platformapi") && url.contains("startapp"));
    }

}