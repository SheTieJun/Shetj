package com.shetj.diyalbume.utils

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.SparseArray
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.github.ielse.imagewatcher.ImageWatcher
import com.github.ielse.imagewatcher.ImageWatcherHelper

import java.util.ArrayList

/**
 * **@packageName：** shetj.me.base.utils<br></br>
 * **@author：** shetj<br></br>
 * **@createTime：** 2019/1/9 0009<br></br>
 * **@company：**<br></br>
 * **@email：** 375105540@qq.com<br></br>
 * **@describe**<br></br>
 */
class ImageWatcherUtils(activity: Activity) {
    private val iwHelper: ImageWatcherHelper

    init {
        iwHelper = ImageWatcherHelper.with(activity) { context, uri, loadCallback ->
            Glide.with(context).load(uri)
                    .into(object : SimpleTarget<Drawable>() {
                        override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                            loadCallback.onResourceReady(resource)
                        }
                    })
        }
    }

    fun showPic(imageView: ImageView, dataList: List<String>, position: Int) {
        val mapping = SparseArray<ImageView>()
        mapping.put(position, imageView)
        iwHelper.show(imageView, mapping, convert(dataList))
    }


    fun showPic(imageView: ImageView, dataList: List<String>, mapping: SparseArray<ImageView>) {
        iwHelper.show(imageView, mapping, convert(dataList))
    }

    fun convert(data: List<String>): List<Uri> {
        val list = ArrayList<Uri>()
        for (datum in data) {
            list.add(Uri.parse(datum))
        }
        return list
    }

    fun onBackPressed(): Boolean {
        return !iwHelper.handleBackPressed()
    }

}
