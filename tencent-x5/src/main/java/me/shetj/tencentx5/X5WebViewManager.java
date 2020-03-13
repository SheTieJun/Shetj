package me.shetj.tencentx5;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.shetj.base.tools.json.EmptyUtils;

/**
 * WebView管理器，提供常用设置
 * @author Administrator
 */
public class X5WebViewManager {
	private WebView webView;
	private WebSettings webSettings;
	private	ActionSelectListener mActionSelectListener;
	private ActionMode mActionMode;
	private List<String> mActionList = new ArrayList<String>() {
		{
			add("复制");
		}
	};

	public X5WebViewManager(WebView webView){
		this.webView = webView;
        webSettings = webView.getSettings();
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
	}

	@SuppressLint("SetJavaScriptEnabled")
	public void setX5Settings() {
		webSettings.setAllowFileAccessFromFileURLs(false);
		webSettings.setAllowUniversalAccessFromFileURLs(false);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setAllowFileAccess(true);
		webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
		webSettings.setSupportZoom(true);
		webSettings.setBuiltInZoomControls(false);
		webSettings.setUseWideViewPort(true);
		webSettings.setSupportMultipleWindows(true);
		webSettings.setAppCacheEnabled(true);
		// webSettings.setDatabaseEnabled(true);
		webSettings.setDomStorageEnabled(true);
		webSettings.setGeolocationEnabled(true);
		webSettings.setAppCacheMaxSize(Long.MAX_VALUE);
		// webSettings.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
		webSettings.setPluginState(WebSettings.PluginState.ON_DEMAND);
		// webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
		webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
		// 设置可以访问文件
		//禁止横屏滚动 网页自适配
		webSettings.setLoadWithOverviewMode(true);
//        // 设置可手动缩放 隐藏缩放工具
		webSettings.setDisplayZoomControls(false);
		webSettings.setDefaultTextEncodingName("UTF-8");//设置默认为utf-8
		// enable navigator.geolocation
		webSettings.setDatabaseEnabled(true);
		// enable Web Storage: localStorage, sessionStorage
		webSettings.setAllowContentAccess(true);
		webSettings.setSavePassword(false);
		webSettings.setSaveFormData(false);
	}

	/**
	 * 设置点击回掉
	 */
	public void setActionSelectListener(ActionSelectListener actionSelectListener) {
		linkJSInterface();
		this.mActionSelectListener = actionSelectListener;
	}

    /**
     * 开启自适应功能
     */
    public X5WebViewManager enableAdaptive(){
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        return this;
    }

    /**
     * 禁用自适应功能
     */
    public X5WebViewManager disableAdaptive(){
        webSettings.setUseWideViewPort(false);
        webSettings.setLoadWithOverviewMode(false);
        return this;
    }

    /**
     * 开启缩放功能
     */
    public X5WebViewManager enableZoom(){
        webSettings.setSupportZoom(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(true);
        return this;
    }

    /**
     * 禁用缩放功能
     */
    public X5WebViewManager disableZoom(){
        webSettings.setBuiltInZoomControls(false);
        return this;
    }

    /**
     * 开启JavaScript
     */
    @SuppressLint("SetJavaScriptEnabled")
    public X5WebViewManager enableJavaScript(){
        webSettings.setJavaScriptEnabled(true);
        return this;
    }

    /**
     * 禁用JavaScript
     */
    @SuppressLint("SetJavaScriptEnabled")
    public X5WebViewManager disableJavaScript(){
        webSettings.setJavaScriptEnabled(false);
        return this;
    }

    /**
     * 开启JavaScript自动弹窗
     */
    public X5WebViewManager enableJavaScriptOpenWindowsAutomatically(){
    	webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
    	return this;
    }

    /**
     * 禁用JavaScript自动弹窗
     */
    public X5WebViewManager disableJavaScriptOpenWindowsAutomatically(){
    	webSettings.setJavaScriptCanOpenWindowsAutomatically(false);
    	return this;
    }


	/**
	 * 处理item，处理点击
	 * @param actionMode
	 */
	public ActionMode resolveActionMode(ActionMode actionMode) {
		if (actionMode != null) {
			final Menu menu = actionMode.getMenu();
			mActionMode = actionMode;
			menu.clear();
			for (int i = 0; i < mActionList.size(); i++) {
				menu.add(mActionList.get(i));
			}
			for (int i = 0; i < menu.size(); i++) {
				MenuItem menuItem = menu.getItem(i);
				menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						getSelectedData((String) item.getTitle());
						releaseAction();
						return true;
					}
				});
			}
		}
		mActionMode = actionMode;
		return actionMode;
	}


	/**
	 * 点击的时候，获取网页中选择的文本，回掉到原生中的js接口
	 * @param title 传入点击的item文本，一起通过js返回给原生接口
	 */
	private void getSelectedData(String title) {

		String js = "(function getSelectedText() {" +
				"var txt;" +
				"var title = \"" + title + "\";" +
				"if (window.getSelection) {" +
				"txt = window.getSelection().toString();" +
				"} else if (window.document.getSelection) {" +
				"txt = window.document.getSelection().toString();" +
				"} else if (window.document.selection) {" +
				"txt = window.document.selection.createRange().text;" +
				"}" +
				"JSActionInterface.callback(txt,title);" +
				"})()";
		webView.evaluateJavascript("javascript:" + js, null);
	}

	public void linkJSInterface() {
		webView.addJavascriptInterface(new ActionSelectInterface(webView), "JSActionInterface");
	}

	/**
	 * 对图片进行重置大小，宽度就是手机屏幕宽度，高度根据宽度比便自动缩放
	 */
	public void imgReset() {
		webView.loadUrl("javascript:(function(){" +
						"var objs = document.getElementsByTagName('img'); " +
						"for(var i=0;i<objs.length;i++)  " +
						"{"
						+    "var img = objs[i];   " +
						"    img.style.maxWidth = '100%';" +
						"    img.style.height = 'auto';  " +
						"}" +
						"})()");
	}


	private void addImageClickListner() {
		// 这段js函数的功能就是，遍历所有的img节点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
		webView.loadUrl("javascript:(function(){" +
						"var objs = document.getElementsByTagName(\"img\"); " +
						"for(var i=0;i<objs.length;i++)  " +
						"{"
						+ "    objs[i].onclick=function()  " +
						"    {  "
						+ "        window.sonic.openImage(this.src);  " +
						"    }  " +
						"}" +
						"})()");
	}


	/**
	 * 给设置localStorage 设置数据
	 * @param itmes
	 */
	private void setlocalStorage(Map<String, String>  itmes){
		StringBuilder jsonBuf = new StringBuilder();
		for (String key : itmes.keySet()){
			if (EmptyUtils.isNotEmpty(itmes.get(key))) {
				jsonBuf.append("localStorage.setItem('key', '")
						.append(itmes.get(key))
						.append("');");
			}
		}
		String info = jsonBuf.toString();
		if (EmptyUtils.isNotEmpty(info)) {
			webView.evaluateJavascript(info, null);
		}
	}


	/**
     * 返回
     * @return true：已经返回，false：到头了没法返回了
     */
	public boolean goBack(){
		if(webView.canGoBack()){
			webView.goBack();
			return true;
		}else{
			return false;
		}
	}

	private void releaseAction() {
		if (mActionMode != null) {
			mActionMode.finish();
			mActionMode = null;
		}
	}


	/**
	 * js选中的回掉接口
	 */
	private class ActionSelectInterface {

		WebView mContext;

		ActionSelectInterface(WebView c) {
			mContext = c;
		}

		@JavascriptInterface
		public void callback(final String value, final String title) {
			if(mActionSelectListener != null) {
				mActionSelectListener.onClick(title, value);
			}
		}
	}
}