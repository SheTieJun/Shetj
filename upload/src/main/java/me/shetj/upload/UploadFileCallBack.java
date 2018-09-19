package me.shetj.upload;

import android.support.annotation.Keep;

@Keep
public interface UploadFileCallBack<T> {
		void progress(int size, int allSize);
    void succeed(T file);
		void onFail(String msg);
}