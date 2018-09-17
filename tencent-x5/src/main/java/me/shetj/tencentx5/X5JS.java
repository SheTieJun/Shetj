package me.shetj.tencentx5;

import android.content.Context;
import android.webkit.JavascriptInterface;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;

/**
 * @link sendApps(java.lang.String) 发送数据给android
 *
 *  #openImage(java.lang.String) 打开图片
 */
public class X5JS {

    private static final String SEND_APPS = "x5_send_message";

    private X5WebView webView;
    private Context context;
    private ImageClickListener imageClickListener;

    public X5JS(X5WebView context) {
        webView = context;
       this.context = context.getContext();
    }

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