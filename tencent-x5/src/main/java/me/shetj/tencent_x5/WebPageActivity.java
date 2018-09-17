package me.shetj.tencent_x5;

import android.os.Bundle;

/**
 * 网页
 *
 * @author shetj
 */
public class WebPageActivity extends BaseX5WebActivity {

	@Override
	protected int getContextViewId() {
		return R.layout.x5_activity_web_page;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void initView() {
		mWebView  = getX5WebView();
		mWebView.setX5ChromeClient(new X5WebChromeClient(getRxContext()));
		mWebView.setX5JSI(new X5JS(mWebView),"shetj_X5");
		mWebView.setX5DownLoadListener(new X5DownLoadListener());
		mWebView.setX5ViewClient(new X5WebViewClient());
	}

	@Override
	protected void initData() {
		mWebView.loadUrl("http://www.baidu.com");
	}

	@Override
	protected X5WebView getX5WebView() {
		return findViewById(R.id.x5webView);
	}
}
