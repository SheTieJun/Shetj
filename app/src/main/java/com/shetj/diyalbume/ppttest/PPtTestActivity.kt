package com.shetj.diyalbume.ppttest

import android.os.Bundle
import android.support.design.widget.Snackbar
import com.shetj.diyalbume.R
import com.zhouyou.http.EasyHttp
import com.zhouyou.http.cache.model.CacheMode

import kotlinx.android.synthetic.main.activity_ppt_test.*
import kotlinx.android.synthetic.main.content_ppt_test.*
import me.shetj.base.base.BaseActivity
import me.shetj.base.http.callback.EasyCallBack
import me.shetj.base.tools.app.ArmsUtils

class PPtTestActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ppt_test)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        initView()
        initData()
    }


    override fun initView() {
        btn_comment.setOnClickListener({
            EasyHttp.get("http://ppt66.com:8020/ppt_api/api/v4/push/comPush?loginName=pp667613")
                    .baseUrl("http://baidu.com")
                    .cacheMode(CacheMode.NO_CACHE)
                    .execute<String>(object : EasyCallBack<String>(){
                        override fun onSuccess(o: String?) {
                            super.onSuccess(o)
                            ArmsUtils.longSnackbar(this@PPtTestActivity,o)
                        }
                    })
        })
        btn_msg.setOnClickListener({
            EasyHttp.get("http://ppt66.com:8020/ppt_api/api/v4/push/actPush?loginName=pp667613")
                    .baseUrl("http://baidu.com")
                    .cacheMode(CacheMode.NO_CACHE)
                    .execute<String>(object : EasyCallBack<String>(){
                        override fun onSuccess(o: String?) {
                            super.onSuccess(o)
                            ArmsUtils.longSnackbar(this@PPtTestActivity,o)
                        }
                    })
        })
        btn_message.setOnClickListener({
            EasyHttp.get("http://ppt66.com:8020/ppt_api/api/v4/push/feedPush?loginName=pp667613")
                    .baseUrl("http://baidu.com")
                    .cacheMode(CacheMode.NO_CACHE)
                    .execute<String>(object : EasyCallBack<String>(){
                        override fun onSuccess(o: String?) {
                            super.onSuccess(o)
                            ArmsUtils.longSnackbar(this@PPtTestActivity,o)
                        }
                    })
        })
    }

    override fun initData() {
    }
}
