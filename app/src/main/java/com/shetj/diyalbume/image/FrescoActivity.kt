package com.shetj.diyalbume.image

import android.os.Bundle
import android.widget.ImageView
import com.alibaba.android.arouter.facade.annotation.Route
import com.jakewharton.rxbinding2.view.RxView
import com.shetj.diyalbume.R
import com.shetj.diyalbume.R.id.*
import com.shetj.diyalbume.utils.ImageWatcherUtils
import kotlinx.android.synthetic.main.activity_fresco.*
import me.shetj.base.base.BaseActivity
import me.shetj.base.base.BasePresenter
import me.shetj.fresco.FrescoUtils
@Route(path = "/shetj/FrescoActivity")
class FrescoActivity : BaseActivity<BasePresenter<*>>() {

    private val imageLoader = FrescoUtils.getImageLoader()
    private lateinit var imageWatcherUtils: ImageWatcherUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fresco)
         imageWatcherUtils = ImageWatcherUtils(this)
        initView()
        initData()
    }
    override fun initView() {
        imageLoader.load(my_image_view,"http://oss.ppt66.com/2237/8/1530072334892幻灯片1(8).JPG")
        imageLoader.load(my_image_view2,"http://oss.ppt66.com/2237/8/1530072334892幻灯片1(8).JPG",true)
        imageLoader.loadProgressive(my_image_view3,"http://oss.ppt66.com/2237/8/1530171813217奔跑(15).JPG")
        imageLoader.load(my_image_view4,"http://n.sinaimg.cn/tech/transform/538/w239h299/20180929/eyBp-hhuhisn5141610.gif")

    }

    override fun initData() {
        val list = ArrayList<String>()
        list.add("http://oss.ppt66.com/2237/8/1530072334892幻灯片1(8).JPG")
        list.add("http://oss.ppt66.com/2237/8/1530072334892幻灯片1(8).JPG")
        list.add("http://oss.ppt66.com/2237/8/1530171813217奔跑(15).JPG")
        list.add("http://n.sinaimg.cn/tech/transform/538/w239h299/20180929/eyBp-hhuhisn5141610.gif")
        RxView.clicks(my_image_view).subscribe {
            imageWatcherUtils.showPic(my_image_view,list,0)
        }
        RxView.clicks(my_image_view2).subscribe {
            imageWatcherUtils.showPic(my_image_view2,list,1)
        }
        RxView.clicks(my_image_view3).subscribe {
            imageWatcherUtils.showPic(my_image_view3,list,2)
        }
        RxView.clicks(my_image_view4).subscribe {
            imageWatcherUtils.showPic(my_image_view4 ,list,3)
        }

    }

    override fun onBackPressed() {
        if(imageWatcherUtils.onBackPressed()){
            super.onBackPressed()
        }
    }
}
