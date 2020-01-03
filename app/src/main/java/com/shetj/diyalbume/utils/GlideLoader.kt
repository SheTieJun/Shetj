package com.shetj.diyalbume.utils

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.shetj.diyalbume.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber


/**
 * [Glide] 的一些方法在APP用到封装
 */
object GlideLoader {


    private var oldRGB: Int = -1558440
    private val colorMap: HashMap<String, Int> = HashMap(8)

    /**
     * 高斯模糊
     */
    fun blurImage(context: Context, @DrawableRes bgUrl: Int, imageView: ImageView) {
        Glide.with(context)
                .asBitmap()
                .load(bgUrl)
                .apply(
                        RequestOptions()
                                .placeholder(R.color.white)
                                .error(R.color.white)
                )
//                .apply(RequestOptions.bitmapTransform(MultiTransformation(CenterCrop(), BlurTransformation(23, 5))))
                .apply(RequestOptions.bitmapTransform(MultiTransformation(CenterCrop())))
                .into(imageView)
    }

    /**
     * DominantSwatch	最突出的颜色
     * VibrantSwatch	有活力的颜色
     * DarkVibrantSwatch	有活力的暗色
     * LightVibrantSwatch	有活力的亮色
     * MutedSwatch	柔和的颜色
     * DarkMutedSwatch	柔和的暗色
     * LightMutedSwatch	柔和的亮色
     * */
    fun blurImagePalette(context: Context, bgUrl: String, imageView: ImageView) {
        imageView.tag = bgUrl
        //先看是否存在
        val bgRGB = colorMap[bgUrl]
        if (bgRGB != null && imageView.tag == bgUrl) {
            beginAnimation(imageView, bgRGB)
            oldRGB = bgRGB
            return
        }
        //mutedSwatch 柔和色
        Glide.with(context).asBitmap().load(bgUrl).listener(object : RequestListener<Bitmap> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                return false
            }

            @SuppressLint("CheckResult")
            override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                Observable.just(resource)
                        .observeOn(Schedulers.computation())
                        .map {
                            val palette = createPaletteSync(it)
                            var rgb = palette.vibrantSwatch?.rgb

                            if (rgb == null) {
                                rgb = palette.swatches[palette.swatches.size / 2].rgb
                            }
                            rgb
                        }
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            it?.let {
                                val rgb = getRGB(bgUrl, it)
                                if (imageView.tag == bgUrl) {
                                    beginAnimation(imageView, rgb)
                                    oldRGB = rgb
                                }
                            }
                        }, {
                            Timber.e(it)
                        })
                return true
            }

        }).submit()
    }

    private fun beginAnimation(imageView: ImageView, bgRGB: Int) {
        val colorAnimator = ObjectAnimator.ofInt(imageView, "backgroundColor", oldRGB, bgRGB)
        colorAnimator.duration = 450
        colorAnimator.interpolator = AccelerateDecelerateInterpolator()
        colorAnimator.setEvaluator(ArgbEvaluator())
        colorAnimator.start()
    }

    private fun getRGB(bgUrl: String, it: Int): Int {
        val rgb = colorMap[bgUrl]
        if (rgb == null) {
            colorMap[bgUrl] = it
        }
        return it
    }

    private fun createPaletteSync(bitmap: Bitmap): Palette = Palette.from(bitmap).generate()


}