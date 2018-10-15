package me.shetj.tencentx5.sonic;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.webkit.JavascriptInterface;

import com.tencent.sonic.sdk.SonicDiffDataCallback;

import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import java.util.ArrayList;

import me.shetj.tencentx5.ImageClickListener;
import me.shetj.tencentx5.X5Utils;
import me.shetj.tencentx5.X5WebView;

public class SonicJavaScriptInterface {

    private final SonicSessionClientImpl sessionClient;

    private final Intent intent;

    public static final String PARAM_CLICK_TIME = "clickTime";

    public static final String PARAM_LOAD_URL_TIME = "loadUrlTime";

    public SonicJavaScriptInterface(X5WebView webView,SonicSessionClientImpl sessionClient, Intent intent) {
        this.sessionClient = sessionClient;
        this.intent = intent;
        this.webView = webView;
    }

    @JavascriptInterface
    public void getDiffData() {
        getDiffData2("getDiffDataCallback");
    }

    @JavascriptInterface
    public void getDiffData2(final String jsCallbackFunc) {
        if (null != sessionClient) {
            sessionClient.getDiffData(new SonicDiffDataCallback() {
                @Override
                public void callback(final String resultData) {
                    Runnable callbackRunnable = new Runnable() {
                        @Override
                        public void run() {
                            String jsCode = "javascript:" + jsCallbackFunc + "('"+ toJsString(resultData) + "')";
                            sessionClient.getWebView().loadUrl(jsCode);
                        }
                    };
                    if (Looper.getMainLooper() == Looper.myLooper()) {
                        callbackRunnable.run();
                    } else {
                        new Handler(Looper.getMainLooper()).post(callbackRunnable);
                    }
                }
            });
        }
    }

    @JavascriptInterface
    public String getPerformance() {
        long clickTime = intent.getLongExtra(PARAM_CLICK_TIME, -1);
        long loadUrlTime = intent.getLongExtra(PARAM_LOAD_URL_TIME, -1);
        try {
            JSONObject result = new JSONObject();
            result.put(PARAM_CLICK_TIME, clickTime);
            result.put(PARAM_LOAD_URL_TIME, loadUrlTime);
            return result.toString();
        } catch (Exception e) {

        }

        return "";
    }

    /*
    * * From RFC 4627, "All Unicode characters may be placed within the quotation marks except
    * for the characters that must be escaped: quotation mark,
    * reverse solidus, and the control characters (U+0000 through U+001F)."
    */
    private static String toJsString(String value) {
        if (value == null) {
            return "null";
        }
        StringBuilder out = new StringBuilder(1024);
        for (int i = 0, length = value.length(); i < length; i++) {
            char c = value.charAt(i);


            switch (c) {
                case '"':
                case '\\':
                case '/':
                    out.append('\\').append(c);
                    break;

                case '\t':
                    out.append("\\t");
                    break;

                case '\b':
                    out.append("\\b");
                    break;

                case '\n':
                    out.append("\\n");
                    break;

                case '\r':
                    out.append("\\r");
                    break;

                case '\f':
                    out.append("\\f");
                    break;

                default:
                    if (c <= 0x1F) {
                        out.append(String.format("\\u%04x", (int) c));
                    } else {
                        out.append(c);
                    }
                    break;
            }

        }
        return out.toString();
    }


    private static final String SEND_APPS = "x5_send_message";

    private X5WebView webView;
    private ImageClickListener imageClickListener;

    private ArrayList<String> imgUrl;

    public void setImageClickListener(ImageClickListener imageClickListener) {
        this.imageClickListener = imageClickListener;
    }

    public void setImgUrl(ArrayList<String> imgUrl) {
        this.imgUrl = imgUrl;
    }

    @JavascriptInterface
    public void onX5ButtonClicked() {
        X5Utils.enableX5FullscreenFunc(webView);
    }

    @JavascriptInterface
    public void onCustomButtonClicked() {
        X5Utils.disableX5FullscreenFunc(webView);
    }

    @JavascriptInterface
    public void onLiteWndButtonClicked() {
        X5Utils.enableLiteWndFunc(webView);
    }

    @JavascriptInterface
    public void onPageVideoClicked() {
        X5Utils.enablePageVideoFunc(webView);
    }

    @JavascriptInterface
    public void sendApps(String name){
        EventBus.getDefault().post(name, SEND_APPS);
    }
    @JavascriptInterface
    public void openImageP(String position) {
        if (imgUrl!=null && imageClickListener !=null) {
            imageClickListener.openImages(imgUrl,Integer.parseInt(position));
        }
    }

    @JavascriptInterface
    public void openImageUrl(String img) {
        if (imgUrl!=null && imageClickListener !=null) {
            imageClickListener.openImages(imgUrl,imgUrl.indexOf(img));
        }
    }

    @JavascriptInterface
    public void openImage(String img) {
        ArrayList<String> imgUrl = new ArrayList<>();
        imgUrl.add(img);
        if (imageClickListener !=null) {
            imageClickListener.openImages(imgUrl, 0);
        }
    }
}