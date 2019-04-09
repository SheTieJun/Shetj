package me.shetj.upload;

import android.content.Context;
import android.util.Log;

import com.tencent.cos.COSClient;
import com.tencent.cos.COSConfig;
import com.tencent.cos.common.COSEndPoint;
import com.tencent.cos.model.COSRequest;
import com.tencent.cos.model.COSResult;
import com.tencent.cos.model.PutObjectRequest;
import com.tencent.cos.model.PutObjectResult;
import com.tencent.cos.task.listener.IUploadTaskListener;

class COSClientUtils {
	private static COSClientUtils ourInstance = null;
	private final COSClient cos;


	static COSClientUtils getInstance(Context context) {
		if ( ourInstance == null ) {
			synchronized ( COSClientUtils.class ){
				if (ourInstance == null) {
					ourInstance = new COSClientUtils(context);
				}
			}
		}
		return ourInstance ;
	}

	private COSClientUtils(Context context) {
		String appid =  "1253442168";
		Context mConext = context.getApplicationContext();
		String peristenceId = "持久化Id";

//创建COSClientConfig对象，根据需要修改默认的配置参数
		COSConfig config = new COSConfig();
//如设置园区
		config.setEndPoint(COSEndPoint.COS_GZ);
		cos = new COSClient(mConext,appid,config,peristenceId);
	}

	public void upload(String bucket, final String cosPath, String srcPath, String sign, final UploadFileCallBack<String> uploadFileCallBack){
		PutObjectRequest putObjectRequest = new PutObjectRequest();
		putObjectRequest.setBucket(bucket);
		putObjectRequest.setCosPath(cosPath);
		putObjectRequest.setSrcPath(srcPath);
		putObjectRequest.setSign(sign);
		putObjectRequest.setListener(new  IUploadTaskListener(){
			@Override
			public void onSuccess(COSRequest cosRequest, COSResult cosResult) {

				PutObjectResult result = (PutObjectResult) cosResult;
				if(result != null){
					StringBuilder stringBuilder = new StringBuilder();
					stringBuilder.append(" 上传结果： ret=" + result.code + "; msg =" +result.msg + "\n");
					stringBuilder.append(" access_url= " + result.access_url == null ? "null" :result.access_url + "\n");
					stringBuilder.append(" resource_path= " + result.resource_path == null ? "null" :result.resource_path + "\n");
					stringBuilder.append(" url= " + result.url == null ? "null" :result.url);
					Log.w("TEST",stringBuilder.toString());
				}
				if (uploadFileCallBack !=null ) {
					uploadFileCallBack.succeed(result.url);
				}
			}

			@Override
			public void onFailed(COSRequest COSRequest, final COSResult cosResult) {
				Log.w("TEST","上传出错： ret =" +cosResult.code + "; msg =" + cosResult.msg);
				if (uploadFileCallBack !=null){
					uploadFileCallBack.onFail(cosResult.msg);
				}
			}

			@Override
			public void onProgress(COSRequest cosRequest, final long currentSize, final long totalSize) {
				float progress = (float)currentSize/totalSize;
				progress = progress *100;
				Log.w("TEST","进度：  " + (int)progress + "%");
				if (uploadFileCallBack !=null){
					uploadFileCallBack.progress(currentSize,totalSize);
				}
			}

			@Override
			public void onCancel(COSRequest cosRequest, COSResult cosResult) {

			}
		});
		PutObjectResult result = cos.putObject(putObjectRequest);
	}


}
