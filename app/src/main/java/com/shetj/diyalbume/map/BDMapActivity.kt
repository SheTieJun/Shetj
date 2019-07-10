package com.shetj.diyalbume.map

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.view.View
import android.widget.Button
import android.widget.TextView

import com.alibaba.android.arouter.facade.annotation.Route
import com.shetj.diyalbume.R

import org.simple.eventbus.Subscriber
import org.simple.eventbus.ThreadMode

import me.shetj.aspect.permission.MPermission
import me.shetj.base.base.BaseActivity
import me.shetj.base.base.BasePresenter
import me.shetj.base.tools.app.ArmsUtils
import me.shetj.base.tools.json.GsonKit
import me.shetj.bdmap.BDMapLocation
import me.shetj.bdmap.MapActivity
import me.shetj.bdmap.MapNaviUtils

import me.shetj.bdmap.BDMapLocation.SEND_POSITION

/**
 * 地图相关
 *
 * @author shetj
 */
@Route(path = "/shetj/BDMapActivity")
class BDMapActivity : BaseActivity<BasePresenter<*>>(), View.OnClickListener {

    private var mTvPosition: TextView? = null
    /**
     * 地图
     */
    private var mBtnMap: Button? = null
    /**
     * 我的位置
     */
    private var mBtnLocal: Button? = null
    /**
     * 打开第三方地图
     */
    private var mBtnOpenMap: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bdmap)
        initView()
        initData()
    }

    override fun initView() {

        mTvPosition = findViewById<View>(R.id.tv_position) as TextView
        mBtnMap = findViewById<View>(R.id.btn_map) as Button
        mBtnMap!!.setOnClickListener(this)
        mBtnLocal = findViewById<View>(R.id.btn_local) as Button
        mBtnLocal!!.setOnClickListener(this)
        mBtnOpenMap = findViewById<View>(R.id.btn_open_map) as Button
        mBtnOpenMap!!.setOnClickListener(this)
    }

    override fun initData() {
        getLocalPosition()
    }


    @MPermission(value = [Manifest.permission.ACCESS_FINE_LOCATION])
    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_map -> startActivity(Intent(this, MapActivity::class.java))
            R.id.btn_local -> getLocalPosition()
            R.id.btn_open_map -> {
                ArmsUtils.makeText("上海外滩")
                MapNaviUtils.showSelectMap(this, "上海外滩", 31.239666, 121.499809)
            }
            else -> {
            }
        }
    }

    private fun getLocalPosition() {
        BDMapLocation.getInstance(applicationContext).stop()
        BDMapLocation.getInstance(this).setOption(true)
        BDMapLocation.getInstance(this).start(BDMapLocation.SendTag.DEF)
    }


    @Subscriber(tag = SEND_POSITION, mode = ThreadMode.ASYNC)
    fun location(location: Message) {
        mTvPosition!!.text = GsonKit.objectToJson(location.obj)
    }

}
