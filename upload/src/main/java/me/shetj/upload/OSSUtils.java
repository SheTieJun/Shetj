package me.shetj.upload;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.shetj.base.s;
import me.shetj.base.tools.json.EmptyUtils;
import timber.log.Timber;

/**
 * <b>@packageName：</b> com.aycm.dsy.utils<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2018/3/1<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b> 阿里云oss <br>
 */
@Keep
public class OSSUtils {


	private static final  OSSUtils ourInstance = new  OSSUtils();
	private String endpoint = "--";
	private Map<String ,OSSAsyncTask> uploadTask ;
	private String AccessKeyId = "-";
	private String  SecretKeyId ="-";
	private String  SecurityToken ="-";


	private static OSSClient mOss;
	private String URLHead = "---";
	private String mBucketName = "--";




	private OSSUtils() {
	}

	//public
	public static  OSSUtils getInstance() {
		return ourInstance;
	}


	private  String getUploadKey(String key, String data) {
		String[] split = new File(data).getName().split("\\.");
		if (split.length > 1){
			return "Android" + "/" + key + System.currentTimeMillis()+ "."+split[1];
		}
		return   "Android" + "/"  +key + System.currentTimeMillis()+ "."+split[0];
	}
	/**
	 * 当失效时更新，或者重新获取到SecurityToken 更新
	 * @param AccessKeyId
	 * @param SecretKeyId
	 * @param SecurityToken
	 */
	public void updateOss(@NonNull String AccessKeyId,@NonNull String  SecretKeyId,
	                      @NonNull String  SecurityToken,@NonNull String  urlHead,@NonNull String bucketName) {
		if (mOss != null) {
			URLHead = urlHead;
			mBucketName = bucketName;
			mOss.updateCredentialProvider(new OSSStsTokenCredentialProvider(AccessKeyId, SecretKeyId, SecurityToken));
		}else {
			mOss = getOSS(AccessKeyId, SecretKeyId, SecurityToken);
		}
	}

	/**
	 * 得到OSSClient
	 * @param AccessKeyId
	 * @param SecretKeyId
	 * @param SecurityToken
	 * @return
	 */
	public OSSClient getOSS(@NonNull String AccessKeyId,
	                        @NonNull String  SecretKeyId,
	                        @Nullable String  SecurityToken) {
		if (this.AccessKeyId.equals(AccessKeyId) &&
						this.SecretKeyId .equals(SecretKeyId) &&
						this.SecurityToken .equals( SecurityToken)) {
			return mOss;
		}
		this.AccessKeyId = AccessKeyId;
		this.SecretKeyId = SecretKeyId;
		this.SecurityToken = SecurityToken;
		if (mOss == null){
			mOss = getOssClient(AccessKeyId,SecretKeyId,SecurityToken);
		}

		return mOss;
	}

	public void asyncUpload(final String filePath,
	                        String packageName,
	                        final UploadFileCallBack<String> callBack){
		if (!filePath.startsWith("http")) {
			if (new File(filePath).exists()) {
				String uploadFilePath = getUploadKey(packageName,filePath);
				asyncUpload(filePath, packageName, getCompletedCallback(uploadFilePath, callBack));
			}
		}
	}

	public void asyncUpload(final String filePath,
	                        String packageName,
	                        @NonNull OSSCompletedCallback completedCallback ){
		if (!filePath.startsWith("http")) {
			asyncUpload(filePath,packageName, new OSSProgressCallback<PutObjectRequest>() {
				@Override
				public void onProgress(PutObjectRequest request, long l, long l1) {

				}

			},completedCallback);
		}
	}

	public void asyncUpload(@NonNull final String filePath,
	                        @Nullable String urlPath,
	                        @NonNull OSSProgressCallback ossProgressCallback,
	                        @NonNull OSSCompletedCallback completedCallback ){
		if (!filePath.startsWith("http")) {
			if (new File(filePath).exists()) {
				String uploadFilePath =  getUploadKey(urlPath,filePath);

				OSSAsyncTask task = asyncUploadFile(mBucketName, uploadFilePath, filePath,
								ossProgressCallback, completedCallback);
				getUploadTask().put(uploadFilePath, task);
			}
		}
	}



	/**
	 * 上传多个文件
	 * @param files
	 * @param urlPath
	 * @param callBack
	 */
	public void  uploadPhotoFilePath(@Nullable List<String> files ,
	                                 @NonNull String urlPath,
	                                 @NonNull final UploadFileCallBack<List<String>> callBack){
		if (null == files || files.size() == 0){
			callBack.onFail( "数据为空~！" );
			return;
		}
		List<File>  fileList = new ArrayList<>();
		for (String file : files) {
			File file1 = new File(file);
			if (!file1.exists()){
				callBack.onFail("存在错误文件！请检测");
				return;
			}
			fileList.add(file1);
		}
		uploadPhotoFile(fileList,urlPath,callBack);
	}

	/**
	 * 上传多个文件
	 * @param files
	 * @param urlPath
	 * @param callBack
	 */
	public Disposable uploadPhotoFile(@NonNull final List<File> files ,
	                                  @NonNull final String urlPath,
	                                  @NonNull final UploadFileCallBack<List<String>> callBack){

		final List<String> uploadUrl = new ArrayList<>(files.size());
		final ArrayList<String> strings = new ArrayList<>();
		return Flowable.create(new FlowableOnSubscribe<String>() {
			@Override
			public void subscribe(final FlowableEmitter<String> emitter) {
				for (int i = 0; i < files.size(); i++) {
					final File file = files.get(i);
					asyncUpload(file.getPath(), urlPath, new UploadFileCallBack<String>() {
						@Override
						public void progress(long size, long allSize) {
							callBack.progress(strings.size(), files.size());
						}

						@Override
						public void succeed(String fileUrl) {
							uploadUrl.add(files.indexOf(file),fileUrl);
							emitter.onNext(fileUrl);
							if (strings.size() == files.size()) {
								emitter.onComplete();
							}
						}

						@Override
						public void onFail(String msg) {
							emitter.onError(new Throwable(msg));
						}
					});
				}
			}
		}, BackpressureStrategy.BUFFER)
						.subscribe(new Consumer<String>() {
							@Override
							public void accept(String s) {
								strings.add(s);
							}
						}, new Consumer<Throwable>() {
							@Override
							public void accept(Throwable throwable) {
								callBack.onFail(throwable.getMessage());
							}
						}, new Action() {
							@Override
							public void run() {
								callBack.succeed(uploadUrl);
							}
						});

	}

	/**
	 * 是否打印log
	 * @param enable
	 */
	public void enableLog(boolean enable){
		if (enable) {
			OSSLog.enableLog();
		}else {
			OSSLog.disableLog();
		}
	}

	/**
	 * 清除所有任务
	 */
	public void clearAllTask() {
		Map<String, OSSAsyncTask> uploadTask = getUploadTask();
		for (String url : uploadTask.keySet()) {
			OSSAsyncTask ossAsyncTask = uploadTask.get(url);
			if (ossAsyncTask!=null && !ossAsyncTask.isCanceled()){
				ossAsyncTask.cancel();
			}
		}
	}


	//private
	private OSSClient getOssClient(String AccessKeyId, String  SecretKeyId, String  SecurityToken) {
		if (EmptyUtils.isEmpty(SecurityToken)){
			return getOssClient(AccessKeyId,SecretKeyId);
		}
		OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(AccessKeyId,SecretKeyId,SecurityToken);
		ClientConfiguration conf = new ClientConfiguration();
		conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
		conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
		conf.setMaxConcurrentRequest(9); // 最大并发请求书，默认5个
		conf.setMaxErrorRetry(3); // 失败后最大重试次数，默认2次
		return  new OSSClient(s.getApp().getApplicationContext(), endpoint, credentialProvider, conf);
	}

	//private
	private OSSClient getOssClient(String AccessKeyId, String  SecretKeyId) {
		OSSCredentialProvider credentialProvider= new OSSPlainTextAKSKCredentialProvider(AccessKeyId, SecretKeyId);
		ClientConfiguration conf = new ClientConfiguration();
		conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
		conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
		conf.setMaxConcurrentRequest(9); // 最大并发请求书，默认5个
		conf.setMaxErrorRetry(3); // 失败后最大重试次数，默认2次
		return  new OSSClient(s.getApp().getApplicationContext(), endpoint, credentialProvider, conf);
	}

	//private
	private OSSAsyncTask asyncUploadFile(String bucketName, String filePath, String uploadFilePath
					, OSSProgressCallback ossProgressCallback, OSSCompletedCallback completedCallback ){
		// 构造上传请求
		PutObjectRequest put = new PutObjectRequest(bucketName, filePath, uploadFilePath);
		// 异步上传时可以设置进度回调
		if (ossProgressCallback!=null) {
			put.setProgressCallback(ossProgressCallback);
		}
		return mOss.asyncPutObject(put, completedCallback);
	}

	/**
	 * 得到所有的上传任务
	 * @return
	 */
	private Map<String, OSSAsyncTask> getUploadTask() {
		if (uploadTask == null){
			uploadTask = new HashMap<>(16);
		}
		return uploadTask;
	}


	private OSSCompletedCallback getCompletedCallback(@NonNull final String uploadFilePath,
	                                                  @NonNull final UploadFileCallBack<String> callBack) {
		return new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
			@Override
			public void onSuccess(PutObjectRequest request, PutObjectResult result) {
				Timber.i("PutObject ：UploadSuccess");
				Timber.i("ETag = %s", result.getETag());
				Timber.i("RequestId = %s", result.getRequestId());
				callBack.succeed(URLHead + uploadFilePath);
				callBack.progress(1,1);
				getUploadTask().remove(uploadFilePath);
			}
			@Override
			public void onFailure(PutObjectRequest request,
			                      ClientException clientException,
			                      ServiceException serviceException) {
				// 请求异常
				if (clientException != null) {
					clientException.printStackTrace();
					callBack.onFail("请求异常");
				}
				if (serviceException != null) {
					callBack.onFail("服务异常");
					// 服务异常
					Timber.e("ErrorCode = %s", serviceException.getErrorCode());
					Timber.e("RequestId = %s", serviceException.getRequestId());
					Timber.e("HostId = %s", serviceException.getHostId());
					Timber.e("RawMessage = %s", serviceException.getRawMessage());
				}
				clearAllTask();

			}
		} ;
	}

}
