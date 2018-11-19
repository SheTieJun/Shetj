package me.shetj.fresco;

import android.app.Application;

import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.listener.RequestLoggingListener;

import java.util.HashSet;
import java.util.Set;

import okhttp3.OkHttpClient;

/**
 * <b>@packageName：</b> me.shetj.fresco<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2018/9/26 0026<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe:</b>  <br>
 * <b>  初始化 {@link FrescoUtils#init(Application, boolean)} <br>
 * <b>  获取 {@link ImageLoader} 使用 {@link FrescoUtils#getImageLoader()} <br>
 */
public class FrescoUtils {

	private   static  ImageLoader  imageLoader = null;

	/**
	 * 	初始化Fresco
	 */
	public static void init(Application application,boolean isDebug) {
		if (isDebug) {
			OkHttpClient mOkHttpClient = new OkHttpClient();
			Set<RequestListener> listeners = new HashSet<>();
			listeners.add(new RequestLoggingListener());
			ImagePipelineConfig config = OkHttpImagePipelineConfigFactory
							.newBuilder(application, mOkHttpClient)
							.setDownsampleEnabled(true)
							.setRequestListeners(listeners)
							.build();
			Fresco.initialize(application, config);
			FLog.setMinimumLoggingLevel(FLog.VERBOSE);
		}else {
			OkHttpClient mOkHttpClient = new OkHttpClient();
			ImagePipelineConfig config = OkHttpImagePipelineConfigFactory
							.newBuilder(application, mOkHttpClient)
							.setDownsampleEnabled(true)
							.build();
			Fresco.initialize(application, config);
		}
	}

	/**
	 * 获取图片加载对象
	 * 你也可以自己创建不用
	 * @return {@link ImageLoader}
	 */
	public static ImageLoader getImageLoader(){
		if (imageLoader == null){
			imageLoader = new  FrescoImageLoader();
		}
		return imageLoader;
	}
}
