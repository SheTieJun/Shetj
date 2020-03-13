package me.shetj.tencentx5;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ActionMode;

import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebView;

/**
 * @author shetj
 */
public class X5WebView extends WebView {


	private  X5WebViewManager x5WebViewManager;

	public X5WebView(Context context) {
		super(context);
		x5WebViewManager = new X5WebViewManager(this);
		x5WebViewManager.setX5Settings();
		this.getView().setClickable(true);
	}

	public X5WebView(Context arg0, AttributeSet arg1) {
		super(arg0, arg1);
		x5WebViewManager = 	new X5WebViewManager(this);
		x5WebViewManager.setX5Settings();
		this.getView().setClickable(true);
	}

	/**
	 * 设置Url ,同时设置cookie
	 */
	public void loadUrlByCookie(Context context, String url, String cookie) {
		CookieSyncManager.createInstance(context);
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.setAcceptCookie(true);
		cookieManager.removeAllCookie();
		cookieManager.setCookie(url, cookie);
		CookieSyncManager.getInstance().sync();
		super.loadUrl(url);
	}

	//FIX
	@Override
	public ActionMode startActionMode(ActionMode.Callback callback) {
		ActionMode actionMode = super.startActionMode(callback);
		return x5WebViewManager.resolveActionMode(actionMode);
	}

	@Override
	public ActionMode startActionMode(ActionMode.Callback callback, int type) {
		ActionMode actionMode = super.startActionMode(callback,type);
		return x5WebViewManager.resolveActionMode(actionMode);
	}

	/**
	 * 设置点击回掉
	 * @param actionSelectListener
	 */
	public void setActionSelectListener(ActionSelectListener actionSelectListener) {
		x5WebViewManager.setActionSelectListener(actionSelectListener);
	}

	public void setX5ChromeClient(X5WebChromeClient a) {
		this.setWebChromeClient(a);
	}

	public void setX5ViewClient(X5WebViewClient a) {
		this.setWebViewClient(a);
	}

	public void setX5DownLoadListener(X5DownLoadListener a) {
		this.setDownloadListener(a);
	}

	public void setX5JSI(X5JS var1, String jsName) {
		this.addJavascriptInterface(var1, jsName);
	}

	public boolean isCanBack(){
		return x5WebViewManager.goBack();
	}

}
