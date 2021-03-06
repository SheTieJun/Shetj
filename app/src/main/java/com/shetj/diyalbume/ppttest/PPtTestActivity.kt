package com.shetj.diyalbume.ppttest

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding3.view.clicks
import com.shetj.diyalbume.R
import com.zhouyou.http.EasyHttp
import com.zhouyou.http.cache.model.CacheMode
import com.zhouyou.http.callback.SimpleCallBack
import com.zhouyou.http.exception.ApiException
import kotlinx.android.synthetic.main.activity_ppt_test.*
import kotlinx.android.synthetic.main.content_ppt_test.*
import me.shetj.base.base.BaseActivity
import me.shetj.base.base.BasePresenter
import me.shetj.base.tools.app.ArmsUtils

@Route(path = "/shetj/PPtTestActivity")
class PPtTestActivity : BaseActivity<BasePresenter<*>>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ppt_test)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }


    override fun initView() {
        btn_comment.setOnClickListener {
            EasyHttp.get("http://ppt66.com:8020/ppt_api/api/v4/push/comPush?loginName=pp667613")
                    .baseUrl("http://baidu.com")
                    .cacheMode(CacheMode.NO_CACHE)
                    .execute(object : SimpleCallBack<String>(){
                        override fun onError(e: ApiException?) {
                        }

                        override fun onSuccess(o: String?) {
                        }
                    })
        }
        btn_msg.setOnClickListener {
            EasyHttp.get("http://ppt66.com:8020/ppt_api/api/v4/push/actPush?loginName=pp667613")
                    .baseUrl("http://baidu.com")
                    .cacheMode(CacheMode.NO_CACHE)
                    .execute<String>(object : SimpleCallBack<String>(){
                        override fun onError(e: ApiException?) {
                        }

                        override fun onSuccess(o: String?) {
                        }
                    })
        }
        btn_message.setOnClickListener {
            EasyHttp.get("http://ppt66.com:8020/ppt_api/api/v4/push/feedPush?loginName=pp667613")
                    .baseUrl("http://baidu.com")
                    .cacheMode(CacheMode.NO_CACHE)
                    .execute<String>(object : SimpleCallBack<String>(){
                        override fun onError(e: ApiException?) {
                        }

                        override fun onSuccess(o: String?) {
                        }
                    })
        }

        btn_new_index.clicks().subscribe {
            ArmsUtils.startActivity(this,NewIndexActivity::class.java)
        }
    }

    override fun initData() {
    }
}
