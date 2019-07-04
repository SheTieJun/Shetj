package me.shetj.base.upload;

import androidx.annotation.Keep;

@Keep
public interface UploadFileCallBack<T> {
		void progress(int size, int allSize);
    void succeed(T file);
		void onFail();
}