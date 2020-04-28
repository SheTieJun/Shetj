package com.shetj.diyalbume.ppttest

import android.view.View
import android.widget.ImageView

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.shetj.diyalbume.R
import com.shetj.diyalbume.miui.GlideApp

/**
 * **@packageName：** com.shetj.diyalbume.ppttest<br></br>
 * **@author：** shetj<br></br>
 * **@createTime：** 2019/1/11 0011<br></br>
 * **@company：**<br></br>
 * **@email：** 375105540@qq.com<br></br>
 * **@describe**<br></br>
 */
class ImageAdpter(data: MutableList<ItemIndex>?) : BaseQuickAdapter<ItemIndex, BaseViewHolder>(R.layout.item_course_img, data) {

    override fun convert(helper: BaseViewHolder, item: ItemIndex) {
        GlideApp.with(context).load(item?.img).into(helper.getView<View>(R.id.iv_image) as ImageView)
    }
}
