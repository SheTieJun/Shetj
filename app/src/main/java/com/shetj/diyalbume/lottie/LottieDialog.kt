package com.shetj.diyalbume.lottie

import android.app.Activity
import android.app.Dialog
import androidx.annotation.Keep
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout

import com.shetj.diyalbume.R


@Keep
object LottieDialog {

    private var mLoadingDialog: Dialog? = null

    fun showLoading(context: Activity, cancelable: Boolean): Dialog {
        if (null != mLoadingDialog) {
            mLoadingDialog!!.cancel()
        }
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_loading_lottie_json, null)
        mLoadingDialog = Dialog(context, R.style.CustomProgressDialog)
        mLoadingDialog!!.setCancelable(cancelable)
        mLoadingDialog!!.setCanceledOnTouchOutside(false)
        mLoadingDialog!!.setContentView(view, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT))
        mLoadingDialog!!.show()
        return mLoadingDialog as Dialog
    }

    fun hideLoading() {
        if (null != mLoadingDialog) {
            mLoadingDialog!!.cancel()
        }
    }

}
