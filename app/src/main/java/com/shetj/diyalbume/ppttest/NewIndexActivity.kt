package com.shetj.diyalbume.ppttest

import android.os.Bundle
import android.os.Message
import androidx.recyclerview.widget.RecyclerView
import com.shetj.diyalbume.R
import me.shetj.base.base.BaseActivity
import me.shetj.base.tools.app.ArmsUtils.Companion.statuInScreen
import java.util.*

/**
 * @author shetj
 */
class NewIndexActivity : BaseActivity<IndexPresenter>() {

    private var mIRecyclerView: RecyclerView? = null
    private var indexAdpter: IndexAdpter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_index)
        statuInScreen( true)
    }

    override fun initView() {
        mIRecyclerView = findViewById(R.id.IRecyclerView)
        mIRecyclerView?.scrollX = 1
    }

    override fun initData() {
        mPresenter = IndexPresenter(this)
        indexAdpter = IndexAdpter(ArrayList())
        mIRecyclerView!!.adapter = indexAdpter

        mPresenter!!.initData()
        addHeadView()
    }

    private fun addHeadView() {

    }


    override fun updateView(message: Message) {
        super.updateView(message)
        when (message.what) {
            1 -> indexAdpter!!.setNewData(message.obj as MutableList<ItemIndex>)
            2 -> {
            }
            else -> {
            }
        }
    }
}
