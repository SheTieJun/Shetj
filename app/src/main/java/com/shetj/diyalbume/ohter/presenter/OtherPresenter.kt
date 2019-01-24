package com.shetj.diyalbume.ohter.presenter

import android.content.Intent
import com.shetj.diyalbume.ohter.model.OtherModel
import me.shetj.base.base.BasePresenter
import me.shetj.base.base.IView
import timber.log.Timber

class OtherPresenter(view :IView) : BasePresenter<OtherModel>(view) {
    fun onFragmentResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Timber.i("requestCode = $requestCode;resultCode = $resultCode")
        if (data != null) {
            Timber.i(data.getStringExtra("BaseQMUIFragment"))
        }
    }

}
