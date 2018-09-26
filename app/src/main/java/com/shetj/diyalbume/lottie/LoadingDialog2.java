package com.shetj.diyalbume.lottie;

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.Keep;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.shetj.diyalbume.R;


@Keep
public class LoadingDialog2 {

    private static Dialog mLoadingDialog;

    public static Dialog showLoading(Activity context, boolean cancelable){
        if (null != mLoadingDialog){
            mLoadingDialog.cancel();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading_lottie, null);
        mLoadingDialog = new Dialog(context, R.style.CustomProgressDialog);
        mLoadingDialog.setCancelable(cancelable);
        mLoadingDialog.setCanceledOnTouchOutside(false);
        mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        mLoadingDialog.show();
        return  mLoadingDialog;
    }

    public static void hideLoading(){
        if (null != mLoadingDialog){
            mLoadingDialog.cancel();
        }
    }

}
