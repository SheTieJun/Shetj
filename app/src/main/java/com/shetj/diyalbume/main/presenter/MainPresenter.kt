package com.shetj.diyalbume.main.presenter

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import androidx.core.graphics.*
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.core.graphics.drawable.toIcon
import androidx.core.graphics.drawable.updateBounds
import com.afollestad.materialdialogs.MaterialDialog
import com.shetj.diyalbume.R
import com.shetj.diyalbume.createAlbum.view.CreateActivity
import com.shetj.diyalbume.nitification.MusicNotification
import me.shetj.base.base.BaseModel
import me.shetj.base.base.BasePresenter
import me.shetj.base.base.IView

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

//        DownloadNotification.notify(view.rxContext, R.drawable.example_picture,"DownloadNotification",
//                "DownloadNotification",1)
//        MessageNotification.notify(view.rxContext,"MessageNotificationXXX",2)
        MusicNotification.notify(view.rxContext.applicationContext,1)
    }

    fun Test(){

        view.rxContext.getPreferences(Context.MODE_PRIVATE).edit{
                putBoolean("key",true)
        }

        val bitmap = BitmapFactory.decodeResource(view.rxContext.resources, R.drawable.error)


        createBitmap(100,100,hasAlpha = true)

        val colorToDrawable = Color.RED.toDrawable()

        val InttoDrawable = Color.parseColor("#ff0000").toDrawable()

        val bitmapIcon = bitmap.toIcon()

        bitmap.scale(100,100)

        val i = bitmap[50, 50]

        val drawable = bitmap.toDrawable(view.rxContext.resources)

        drawable.toBitmap(100,100,Bitmap.Config.RGB_565)

        drawable.updateBounds()



    }

    fun ColorKt(){

        val colorInt = "##ff0000".toColorInt().apply {
            blue
            red
            green
            alpha
        }

        val toColor = colorInt.toColor().apply {
            component1()
        }

    }

    fun showIntroduce() {
        MaterialDialog(view.rxContext).show {
            title(text = "内容介绍")
            message(text="这是一个自己也学习与积累的项目，" +
                    "\n主要功能：单一功能模块化开发，方便以后开发是直接移植module 或者 使用aarinclude" +
                    "\n" +
                    "': app', app实验测试下面的功能都在这个module 实验\n" +
                    "\n" +
                    "':AIDLtest', 测试AIDL 的效果\n" +
                    "\n" +
                    "':base', 基础已经抽离出来\n" +
                    "\n" +
                    "':components', 谷歌的组件简单实验\n" +
                    "\n" +
                    "':router',阿里的路由Arouter 实验\n" +
                    "\n" +
                    "':packinter',移动的fiddler 网络抓包工具\n" +
                    "\n" +
                    "':jguang', 极光的封装\n" +
                    "\n" +
                    "':encrypt',加密\n" +
                    "\n" +
                    "':mlkittest',---机器学习实验（未开始）\n" +
                    "\n" +
                    "':transition', google 的transition 实验\n" +
                    "\n" +
                    "':aspectutils', 面向切面编程测试\n" +
                    "\n" +
                    "':room', ----（未开始）room 数据库ORM\n" +
                    "\n" +
                    "':tencent-x5', 腾讯X5内核的封装使用\n" +
                    "\n" +
                    "':alihotfix', 阿里的热修复\n" +
                    "\n" +
                    "':update', ----（未开始）一些AS自动生成代码测试效果\n" +
                    "\n" +
                    "':upload', 阿里和七牛的上传封装\n" +
                    "\n" +
                    "':simxutils', xutils 3 的抽离 抽离了db\n" +
                    "\n" +
                    "':download', 下载APP\n" +
                    "\n" +
                    "':fresco',图片加载fresco （一直使用的是glide 最近有一个外包对图片部分要求很高，测试效果）\n" +
                    "\n" +
                    "':pay', 支付微信和支付\n" +
                    "\n" +
                    "':bdmap', 百度地图模块---准备把地图部分还是干掉只留下定位\n" +
                    "\n" +
                    "':testmybase',无视 测试项目\n" +
                    "\n" +
                    "':umeng', 友盟的封装\n" +
                    "\n" +
                    "':compiler',无视\n" +
                    "\n" +
                    "':lib', 无视\n" +
                    "\n" +
                    "':share' 分享微信和新浪 只测试了 微信~\n" +
                    "\n" +
                    "':rxFingerPrinter' 指纹部分\n" +
                    "\n" +
                    "':qmuidemo' 腾讯的QMUI 还不错")

            positiveButton(text = "确定")
//            negativeButton(R.string.disagree) { dialog ->
//            }
        }

    }

    fun getModel(): Int {
        //?android:attr/textColorPrimary 这是一种通用型文本颜色。它在浅色主题背景下接近于黑色，在深色主题背景下接近于白色。该颜色包含一个停用状态。
        //?attr/colorControlNormal 一种通用图标颜色。该颜色包含一个停用状态。
        //主题背景属性 ?attr/colorSurface 和 ?attr/colorOnSurface）轻松获取合适的颜色

        //浅色 - MODE_NIGHT_NO
        //深色 - MODE_NIGHT_YES
        //由省电模式设置 - MODE_NIGHT_AUTO_BATTERY
        //系统默认 - MODE_NIGHT_FOLLOW_SYSTEM
        val defaultNightMode = AppCompatDelegate.getDefaultNightMode()

        if (defaultNightMode == AppCompatDelegate.MODE_NIGHT_NO){
            return AppCompatDelegate.MODE_NIGHT_YES
        }
        if (defaultNightMode == AppCompatDelegate.MODE_NIGHT_YES){
            return AppCompatDelegate.MODE_NIGHT_NO
        }
        return AppCompatDelegate.MODE_NIGHT_YES
    }


}