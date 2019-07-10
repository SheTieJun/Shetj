package com.shetj.diyalbume.jobscheduler

import android.os.Bundle
import android.os.Message
import com.shetj.diyalbume.R
import kotlinx.android.synthetic.main.activity_job_scheduler.*
import me.shetj.base.base.BaseActivity

/**
 * jobScheduler 测试界面
 */
class JobSchedulerActivity : BaseActivity<JobSchedulerPresenter>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_scheduler)
        initView()
        initData()
        initEvent()
    }

    private fun initEvent() {
        btn_job.setOnClickListener {
            mPresenter?.startJob()
        }

        btn_cancel_job.setOnClickListener {
            mPresenter?.cancel()
        }

    }

    override fun initView() {

    }

    override fun initData() {
        mPresenter = JobSchedulerPresenter(this)
        mPresenter?.startService()
    }

    override fun updateView(message: Message) {
        super.updateView(message)
        when(message.arg1){
            1 ->{
                tv_msg.text = message.obj.toString()
            }
        }
    }

}
