package me.shetj.tencentx5;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.tencent.sonic.sdk.SonicCacheInterceptor;
import com.tencent.sonic.sdk.SonicConfig;
import com.tencent.sonic.sdk.SonicConstants;
import com.tencent.sonic.sdk.SonicEngine;
import com.tencent.sonic.sdk.SonicSession;
import com.tencent.sonic.sdk.SonicSessionConfig;
import com.tencent.sonic.sdk.SonicSessionConnection;
import com.tencent.sonic.sdk.SonicSessionConnectionInterceptor;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.shetj.base.tools.app.ArmsUtils;
import me.shetj.base.tools.json.EmptyUtils;
import me.shetj.tencentx5.sonic.SonicJavaScriptInterface;
import me.shetj.tencentx5.sonic.SonicRuntimeImpl;
import me.shetj.tencentx5.sonic.SonicSessionClientImpl;
import timber.log.Timber;

/**
 * 网页
 *
 * @author shetj
 */
public class WebPageActivity extends BaseX5WebActivity {
	public static final int MODE_DEFAULT = 0;

	public static final int MODE_SONIC = 1;
	public static final int MODE_SONIC_WITH_OFFLINE_CACHE = 2;

	private static final int PERMISSION_REQUEST_CODE_STORAGE = 1;
	public final static String PARAM_URL = "param_url";

	public final static String PARAM_MODE = "param_mode";

	private X5WebView mWebView;

	private SonicSession sonicSession;
	private SonicSessionClientImpl sonicSessionClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.x_activity_web_page);
		ArmsUtils.statuInScreen(this,true);
		initView();
		initData();
	}

	public static void startBrowserActivity(Activity context, String url , int mode) {
		Intent intent = new Intent(context, WebPageActivity.class);
		intent.putExtra(WebPageActivity.PARAM_URL, url);
		intent.putExtra(WebPageActivity.PARAM_MODE, mode);
		intent.putExtra(SonicJavaScriptInterface.PARAM_CLICK_TIME, System.currentTimeMillis());
		context.startActivityForResult(intent, -1);
	}

	@Override
	protected void initView() {
		Intent intent = getIntent();
		String url = intent.getStringExtra(PARAM_URL);
		int mode = intent.getIntExtra(PARAM_MODE, -1);
		if (EmptyUtils.isEmpty(url)){
			url = "https://gitee.com/shetj";
		}else {
			mWebView.loadUrl(url);
		}
		if (TextUtils.isEmpty(url) || -1 == mode) {
			finish();
			return;
		}
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
	 sonicSessionClient = null;
		// init sonic engine if necessary, or maybe u can do this when application created
		if (!SonicEngine.isGetInstanceAllowed()) {
			SonicEngine.createInstance(new SonicRuntimeImpl(getApplication()), new SonicConfig.Builder().build());
		}

		// if it's sonic mode , startup sonic session at first time
		if ( MODE_DEFAULT != mode) { // sonic mode
			SonicSessionConfig.Builder sessionConfigBuilder = new SonicSessionConfig.Builder();
			sessionConfigBuilder.setSupportLocalServer(true);
			sonicSession = SonicEngine.getInstance().createSession(url, sessionConfigBuilder.build());
			if (null != sonicSession) {
				sonicSession.bindClient(sonicSessionClient = new SonicSessionClientImpl());
			} else {
				Timber.i( "this only happen when a same sonic session is already running," +
								"create sonic session fail!" );
			}
		}
		mWebView  = getX5WebView();
		mWebView.setX5ChromeClient(new X5WebChromeClient(getRxContext()));
//		mWebView.setX5JSI(new X5JS(mWebView),"shetj_X5");
		mWebView.setX5DownLoadListener(new X5DownLoadListener());
		mWebView.setX5ViewClient(new X5WebViewClient(sonicSession));
		mWebView.removeJavascriptInterface("searchBoxJavaBridge_");
		intent.putExtra(SonicJavaScriptInterface.PARAM_LOAD_URL_TIME, System.currentTimeMillis());
		mWebView.addJavascriptInterface(new SonicJavaScriptInterface(mWebView,sonicSessionClient, intent), "sonic");
		if (sonicSessionClient != null) {
			sonicSessionClient.bindWebView(mWebView);
			sonicSessionClient.clientReady();
		} else { // default mode
			mWebView.loadUrl(url);
		}
	}

	@Override
	protected void initData() {


	}

	@Override
	protected X5WebView getX5WebView() {
		return findViewById(R.id.x5webView);
	}

	@Override
	public void onBackPressed() {
		if (!mWebView.isCanBack()){
			super.onBackPressed();
		}
	}

	@Override
	protected void onDestroy() {
		if (null != sonicSessionClient  ) {
			sonicSessionClient.destroy();
		} else { // default mode
			if (null != mWebView) {
				mWebView.destroy();
				mWebView = null;
			}
		}
		if (null != sonicSession) {
			sonicSession.destroy();
			sonicSession = null;
		}
		super.onDestroy();
	}
}
