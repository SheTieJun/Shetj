package me.shetj.tencentx5;

import android.app.Activity;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

public class X5WebChromeClient extends WebChromeClient {

    private Activity mCtx;
    private ProgressBar progressView;
    private TextView titleView;

    public X5WebChromeClient(Activity a, ProgressBar progressView, TextView titleView) {
        mCtx = a;
        this.progressView = progressView;
        this.titleView = titleView;
    }


    @Override
    public boolean onJsConfirm(WebView arg0, String arg1, String arg2,
                               JsResult arg3) {
        return super.onJsConfirm(arg0, arg1, arg2, arg3);
    }

    View myVideoView;
    View myNormalView;
    IX5WebChromeClient.CustomViewCallback callback;

//    /**
//     * 全屏播放配置
//     */
//    @Override
//    public void onShowCustomView(View view,
//                                 IX5WebChromeClient.CustomViewCallback customViewCallback) {
//        FrameLayout normalView = (FrameLayout) view.findViewById(R.id.activity_filechooser);//todo
//        ViewGroup viewGroup = (ViewGroup) normalView.getParent();
//        viewGroup.removeView(normalView);
//        viewGroup.addView(view);
//        myVideoView = view;
//        myNormalView = normalView;
//        callback = customViewCallback;
//    }

    @Override
    public void onHideCustomView() {
        if (callback != null) {
            callback.onCustomViewHidden();
            callback = null;
        }
        if (myVideoView != null) {
            ViewGroup viewGroup = (ViewGroup) myVideoView.getParent();
            viewGroup.removeView(myVideoView);
            viewGroup.addView(myNormalView);
        }
    }


    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        if (title != null) {
            titleView.setText(title);
        }
    }

    @Override
    public boolean onJsAlert(WebView arg0, String arg1, String arg2,
                             JsResult arg3) {
        /**
         * 这里写入你自定义的window alert
         */
        return super.onJsAlert(null, arg1, arg2, arg3);
    }

    // For Android 3.0+
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
        X5Utils.openFileChooseProcess(mCtx);
    }

    // For Android < 3.0
    public void openFileChooser(ValueCallback<Uri> uploadMsgs) {
        X5Utils.openFileChooseProcess(mCtx);
    }

    // For Android  > 4.1.1
    @Override
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        X5Utils.openFileChooseProcess(mCtx);
    }

    // For Android  >= 5.0

    @Override
    public boolean onShowFileChooser(com.tencent.smtt.sdk.WebView webView,
                                     ValueCallback<Uri[]> filePathCallback,
                                     WebChromeClient.FileChooserParams fileChooserParams) {

        if (fileChoooseListener != null) {
	        fileChoooseListener.listener(filePathCallback);
        }
        X5Utils.openFileChooseProcess(mCtx);
        return true;
    }


    @Override
    public void onProgressChanged(WebView webView, int i) {
        if (progressView != null){
            if (i == 100) {
                progressView.setVisibility(View.GONE);
            } else {
                //更新进度
                progressView.setProgress(i);
            }

        }
        super.onProgressChanged(webView, i);
    }

    public FileChoooseListener fileChoooseListener;

    public void setFileChooseListener(FileChoooseListener f) {
        fileChoooseListener = f;
    }

    public interface FileChoooseListener {
        void listener(ValueCallback<Uri[]> filePathCallback);
    }
}