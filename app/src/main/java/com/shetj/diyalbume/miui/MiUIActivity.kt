package com.shetj.diyalbume.miui

import android.os.Bundle
import android.util.Log
import cn.jiguang.imui.commons.models.IMessage
import cn.jiguang.imui.messages.MsgListAdapter
import cn.jiguang.imui.messages.ptr.PtrDefaultHeader
import cn.jiguang.imui.messages.ptr.PtrHandler
import cn.jiguang.imui.utils.DisplayUtil
import me.shetj.base.base.BaseActivity
import com.shetj.diyalbume.R
import kotlinx.android.synthetic.main.activity_mi_ui.*
import kotlinx.android.synthetic.main.content_mi_ui.*
import java.util.ArrayList


class MiUIActivity : BaseActivity() {


   lateinit var mPresenter : MiUIPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mi_ui)
        setSupportActionBar(toolbar)
        mPresenter =  MiUIPresenter(this)


        initView()
        initData()
    }

    override fun initData() {
        mPresenter.loadNextPage()

    }


    override fun initView() {
        val header = PtrDefaultHeader(this)
        header.setPadding(0, DisplayUtil.dp2px(this, 15f), 0, DisplayUtil.dp2px(this, 10f))
        header.setPtrFrameLayout(pull_to_refresh_layout)
        pull_to_refresh_layout.setLoadingMinTime(1000)
        pull_to_refresh_layout.setDurationToCloseHeader(1500)
        pull_to_refresh_layout.headerView = header
        pull_to_refresh_layout.addPtrUIHandler(header)
        // 如果设置为 true，下拉刷新时，内容固定，只有 Header 变化
        pull_to_refresh_layout.isPinContent = true
        pull_to_refresh_layout.setPtrHandler(PtrHandler {
            Log.i("MessageListActivity", "Loading next page")
            // 加载完历史消息后调用
            pull_to_refresh_layout.refreshComplete()
        })

        //showReceiverDisplayName 及 showSenderDisplayName 为 1 或者 0. 1 表示展示昵称，0 为不展示。也可以在代码中设置：
        msg_list.setShowSenderDisplayName(1)
        msg_list.setShowReceiverDisplayName(1)

    }


    fun showMsgList(info: ArrayList<MyMessage>) {
        val holdersConfig = MsgListAdapter.HoldersConfig()
        val mAdapter = MsgListAdapter<IMessage>("0", holdersConfig, MyImageLoader())
        msg_list.setAdapter(mAdapter)
        val message = MyMessage("Hello World", IMessage.MessageType.RECEIVE_TEXT.ordinal)
        message.setUserInfo(DefaultUser("0", "Deadpool", "R.drawable.deadpool"))
        mAdapter.addToStart(message, true)
        val eventMsg = MyMessage("haha", IMessage.MessageType.EVENT.ordinal)
        mAdapter.addToStart(eventMsg, true)
        mAdapter.addToEnd(info as List<IMessage>?)
    }
}
