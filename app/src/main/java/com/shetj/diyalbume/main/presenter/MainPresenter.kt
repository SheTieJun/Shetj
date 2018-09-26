package com.shetj.diyalbume.main.presenter

import android.content.Intent
import com.shetj.diyalbume.R
import com.shetj.diyalbume.createAlbum.view.CreateActivity
import me.shetj.base.base.BaseModel
import me.shetj.base.base.BasePresenter
import me.shetj.base.base.IView
import me.shetj.update.DownloadNotification
import me.shetj.update.MessageNotification

/**
 *
 * <b>@packageName：</b> com.shetj.diyalbume.main.presenter<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2017/12/4<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */
class MainPresenter(view: IView) : BasePresenter<BaseModel>(view) {


    fun startCreateAlbum() {
        val intent = Intent(view.rxContext, CreateActivity::class.java )
         startActivity (intent)
    }

    fun showNotification() {

        DownloadNotification.notify(view.rxContext, R.drawable.example_picture,"DownloadNotification",
                "DownloadNotification",1)
        MessageNotification.notify(view.rxContext,"MessageNotificationXXX",2)

    }


}