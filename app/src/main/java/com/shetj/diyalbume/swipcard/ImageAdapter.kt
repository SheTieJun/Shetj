package com.shetj.diyalbume.swipcard

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.shetj.diyalbume.R

/**
 *
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2019/7/30<br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b>  <br>
 */
class ImageAdapter(data:ArrayList<String>) :BaseQuickAdapter<String,BaseViewHolder>(R.layout.item_detail,data){


    override fun convert(helper: BaseViewHolder, item: String?) {

    }

}