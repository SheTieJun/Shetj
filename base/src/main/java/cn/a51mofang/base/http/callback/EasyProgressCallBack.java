package cn.a51mofang.base.http.callback;

import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.subsciber.IProgressDialog;


public class EasyProgressCallBack<T> extends ProgressDialogCallBack<T> {

	public EasyProgressCallBack(IProgressDialog progressDialog) {
		super(progressDialog);
	}

	public EasyProgressCallBack(IProgressDialog progressDialog, boolean isShowProgress, boolean isCancel) {
		super(progressDialog, isShowProgress, isCancel);
	}

	@Override
	public void onSuccess(T t) {

	}
}
