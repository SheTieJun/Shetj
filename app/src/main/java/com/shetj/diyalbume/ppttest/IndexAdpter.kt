package com.shetj.diyalbume.ppttest

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.shetj.diyalbume.R

import me.shetj.base.tools.app.ArmsUtils

/**
 * **@packageName：** com.shetj.diyalbume.ppttest<br></br>
 * **@author：** shetj<br></br>
 * **@createTime：** 2019/1/11 0011<br></br>
 * **@company：**<br></br>
 * **@email：** 375105540@qq.com<br></br>
 * **@describe**<br></br>
 */
class IndexAdpter(data: List<ItemIndex>?) : BaseQuickAdapter<ItemIndex, BaseViewHolder>(R.layout.item_index_recycle, data) {

    override fun convert(helper: BaseViewHolder, item: ItemIndex) {

        val recyclerView = helper.getView<RecyclerView>(R.id.IRecyclerView)
        if (item.type == "4") {
            val manager = GridLayoutManager(mContext, 2)
            ArmsUtils.configRecycleView(recyclerView, manager)
        } else {
            val manager = GridLayoutManager(mContext, 3)
            ArmsUtils.configRecycleView(recyclerView, manager)
        }

        when (item.type) {
            "1" -> {
                val imageAdpter = ImageAdpter(item.itemIndex)
                recyclerView.adapter = imageAdpter
            }
            "2" -> {
                val imageAdpter2 = ImageAdpter(item.itemIndex)
                recyclerView.adapter = imageAdpter2
            }
            "3" -> {
                val userAdpter = UserAdpter(item.itemIndex)
                recyclerView.adapter = userAdpter
            }
            "4" -> {
                val imageAdpter4 = ImageAdpter(item.itemIndex)
                recyclerView.adapter = imageAdpter4
            }
            else -> {
            }
        }

    }
}
