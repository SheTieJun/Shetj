package me.shetj.upload;

import android.os.Message;
import android.support.annotation.NonNull;

import com.qiniu.android.common.FixedZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.shetj.base.base.SimBaseCallBack;
import me.shetj.base.tools.app.ArmsUtils;
import me.shetj.base.tools.json.EmptyUtils;
import me.shetj.base.tools.time.TimeUtil;
import timber.log.Timber;

/**
 * <b>@packageName：</b> com.aycm.dsy.utils.upload<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2018/3/22<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b>7牛上传文件<br>
 */

public class SNiuUtils {

	public static UploadManager uploadManager;

	public static String urlHead = "";



	public static void init(){
		Configuration config = new Configuration.Builder()
						.chunkSize(512 * 1024)        // 分片上传时，每片的大小。 默认256K
						.putThreshhold(1024 * 1024)   // 启用分片上传阀值。默认512K
						.connectTimeout(10)           // 链接超时。默认10秒
						.useHttps(true)               // 是否使用https上传域名
						.responseTimeout(60)          // 服务器响应超时。默认60秒
						.recorder(null)           // recorder分片上传时，已上传片记录器。默认null
						.zone(FixedZone.zone2)        // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
						.build();
		if (uploadManager == null){
			uploadManager = new UploadManager(config);
		}
		if (EmptyUtils.isEmpty(urlHead)){
			Timber.i("请设置urlHead");
		}
	}

	public static void setUrlHead(@NonNull String headUrl){
		urlHead = headUrl;
	}
	/**
	 * data = <File对象、或 文件路径、或 字节数组>
	 * String key = <指定七牛服务上的文件名，或 null>;
	 * String token = <从服务端SDK获取>;
	 * @param data
	 * @param token
	 * @param callback
	 */
	public static void uploadFile(String data, final String keyPath, String token, final SimBaseCallBack<Message> callback){
		if (uploadManager == null){
			init();
		}
		String key = keyPath + "/"  + TimeUtil.getTime() +new File(data).getName();
		uploadManager.put(data, key, token,
						new UpCompletionHandler() {
							@Override
							public void complete(String key, ResponseInfo info, JSONObject res) {
								if(info.isOK()) {
									Timber.i("Upload Success");
									Message message = new Message();
									message.obj = urlHead + key;
									callback.onSuccess(message);
								} else {
									Timber.i( "Upload Fail");
									callback.onFail();
								}
							}
						}, null);
	}



	/**
	 * data = <File对象、或 文件路径、或 字节数组>
	 * String key = <指定七牛服务上的文件名，或 null>;
	 * String token = <从服务端SDK获取>;
	 * @param data
	 * @param token
	 */
	public static Disposable uploadFile(RxAppCompatActivity activity, final ArrayList<String> data,
	                              final String keyPath, final String token, final SimBaseCallBack<Message> callback){
		if (uploadManager == null){
			init();
		}
		final ArrayList<String> image = new ArrayList<>();
		final ArrayList<String> size = new ArrayList<>();
		return Flowable.create(new FlowableOnSubscribe<String>() {
			@Override
			public void subscribe(final FlowableEmitter<String> emitter) {
				for (int i = 0; i < data.size(); i++) {
					final String file = data.get(i);
					String key = keyPath + "/" + TimeUtil.getTime() + new File(file).getName();
					image.add(urlHead + key);
					uploadManager.put(file, key, token,
									new UpCompletionHandler() {
										@Override
										public void complete(String key, ResponseInfo info, JSONObject res) {
											if (info.isOK()) {
												emitter.onNext(key);
												if (size.size() == data.size()) {
													emitter.onComplete();
												}
											} else {
												emitter.onError(new Throwable("Upload Fail"));
											}
										}
									}, null);
				}
			}
		}, BackpressureStrategy.BUFFER)
						.compose(activity.<String>bindToLifecycle())
						.subscribe(new Consumer<String>() {
							@Override
							public void accept(String s) {
								size.add(s);
							}
						}, new Consumer<Throwable>() {
							@Override
							public void accept(Throwable throwable) {
								callback.onFail();
							}
						}, new Action() {
							@Override
							public void run() {
								Message message = new Message();
								message.obj = image;
								callback.onSuccess(message);
							}
						});

	}
}

