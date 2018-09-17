package me.shetj.tencentx5;

import android.annotation.SuppressLint;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

/**
 * WebView管理器，提供常用设置
 * @author Administrator
 */
public class X5WebViewManager {
	private WebView webView;
	private WebSettings webSettings;

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
		webSettings.setSavePassword(false);
		webSettings.setSupportMultipleWindows(true);
		// webSettings.setLoadWithOverviewMode(true);
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
		webSettings.setSaveFormData(true);
		// enable navigator.geolocation
		webSettings.setDatabaseEnabled(true);
		// enable Web Storage: localStorage, sessionStorage
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
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
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
        webSettings.setSupportZoom(false);
        webSettings.setUseWideViewPort(false);
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
						+ "        window.shetj_X5.openImage(this.src);  " +
						"    }  " +
						"}" +
						"})()");
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
}