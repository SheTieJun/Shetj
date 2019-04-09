package me.shetj.upload;

import android.content.Context;
import android.util.Log;

import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.CosXmlSimpleService;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.listener.CosXmlProgressListener;
import com.tencent.cos.xml.listener.CosXmlResultListener;
import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.transfer.COSXMLUploadTask;
import com.tencent.cos.xml.transfer.TransferConfig;
import com.tencent.cos.xml.transfer.TransferManager;
import com.tencent.cos.xml.transfer.TransferState;
import com.tencent.cos.xml.transfer.TransferStateListener;
import com.tencent.qcloud.core.auth.QCloudCredentialProvider;
import com.tencent.qcloud.core.auth.SessionCredentialProvider;
import com.tencent.qcloud.core.http.HttpRequest;

import java.net.MalformedURLException;
import java.net.URL;

public class MTAOSSUtils {

	final String url ="";
	private static CosXmlSimpleService cosXml = null;
	private MTAOSSUtils(Context context){
		if (cosXml !=null){
			cosXml = getCosService(context,url);
		}
	}



	public static void init(Context context){

	}

	/**
	 * 获取授权
	 * @param serverUrl
	 */
	public CosXmlSimpleService getCosService(Context context,String serverUrl){
		//创建 CosXmlServiceConfig 对象，根据需要修改默认的配置参数
		CosXmlServiceConfig serviceConfig = new CosXmlServiceConfig.Builder()
						.setRegion("record")
						.setDebuggable(true)
						.builder();
		URL url = null; // 后台授权服务的 url 地址
		try {
			url = new URL(serverUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		QCloudCredentialProvider credentialProvider = new SessionCredentialProvider(new HttpRequest.Builder<String>()
						.url(url)
						.method("GET")
						.build());
		return new CosXmlSimpleService(context, serviceConfig, credentialProvider);
	}


	/**
	 * 上传文件
	 * @param bucket //bucket
	 * @param cosPath //key
	 * @param srcPath //本地地址
	 * @param uploadFileCallBack //回调
	 */
	public COSXMLUploadTask uploadFile(String bucket, String cosPath, String srcPath, final UploadFileCallBack<String> uploadFileCallBack){

// 初始化 TransferConfig
		TransferConfig transferConfig = new TransferConfig.Builder().build();

//初始化 TransferManager
		TransferManager transferManager = new TransferManager(cosXml, transferConfig);

//上传文件
		COSXMLUploadTask cosxmlUploadTask = transferManager.upload(bucket, cosPath, srcPath, null);



//设置上传进度回调
			cosxmlUploadTask.setCosXmlProgressListener(new CosXmlProgressListener() {
				@Override
				public void onProgress(long complete, long target) {
//					float progress = 1.0f * complete / target * 100;
					if (uploadFileCallBack !=null){
						uploadFileCallBack.progress(complete,target);
					}
				}
			});
//设置返回结果回调
		cosxmlUploadTask.setCosXmlResultListener(new CosXmlResultListener() {
			@Override
			public void onSuccess(CosXmlRequest request, CosXmlResult result) {
				COSXMLUploadTask.COSXMLUploadTaskResult cOSXMLUploadTaskResult = (COSXMLUploadTask.COSXMLUploadTaskResult)result;
				if (uploadFileCallBack !=null){
					uploadFileCallBack.succeed(result.accessUrl);
				}
			}

			@Override
			public void onFail(CosXmlRequest request, CosXmlClientException exception, CosXmlServiceException serviceException) {
				if (uploadFileCallBack !=null){
					uploadFileCallBack.onFail(exception.errorMessage);
				}
			}
		});
//设置任务状态回调, 可以查看任务过程
		cosxmlUploadTask.setTransferStateListener(new TransferStateListener() {
			@Override
			public void onStateChanged(TransferState state) {
				Log.d("TEST", "Task state:" + state.name());
			}
		});

		return cosxmlUploadTask;
	}
}
