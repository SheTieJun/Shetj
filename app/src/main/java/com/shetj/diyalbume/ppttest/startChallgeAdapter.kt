package com.shetj.diyalbume.ppttest

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.shetj.diyalbume.R

/**
 * **@packageName：** com.shetj.diyalbume.ppttest<br></br>
 * **@author：** shetj<br></br>
 * **@createTime：** 2018/1/31<br></br>
 * **@company：**<br></br>
 * **@email：** 375105540@qq.com<br></br>
 * **@describe**<br></br>
 */

class startChallgeAdapter(data: List<String>?) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_recycle_img, data) {

    override fun convert(helper: BaseViewHolder, item: String) {

    }
}
