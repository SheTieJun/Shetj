package shetj.me.base.common

import android.content.Context
import android.widget.ImageView

/**
 *
 * <b>@packageName：</b> shetj.me.base.common<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2018/8/8 0008<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */
class GlideImageLoader : ImageLoader{
    companion object {
        val instance = GlideImageLoader()
    }

    override fun displayCirImage(context: Context, path: Any?, imageView: ImageView) {

    }

    override fun displayConImage(context: Context, path: Any?, imageView: ImageView) {

    }

    override fun displayImage(context: Context, path: Any?, imageView: ImageView) {

    }

    override fun displayUserImage(context: Context, path: Any?, imageView: ImageView) {

    }

}