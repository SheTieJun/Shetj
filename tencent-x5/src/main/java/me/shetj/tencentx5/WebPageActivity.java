package me.shetj.tencentx5;

import android.os.Bundle;

import me.shetj.base.tools.app.ArmsUtils;

/**
 * 网页
 *
 * @author shetj
 */
public class WebPageActivity extends BaseX5WebActivity {


	private X5WebView mWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.x5_activity_web_page);
		ArmsUtils.statuInScreen(this,true);
		initView();
		initData();
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
		mWebView.loadUrl("https://gitee.com/shetj");
	}

	@Override
	protected X5WebView getX5WebView() {
		return findViewById(R.id.x5webView);
	}

}
