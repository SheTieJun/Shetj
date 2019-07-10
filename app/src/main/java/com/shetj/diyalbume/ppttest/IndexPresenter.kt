package com.shetj.diyalbume.ppttest

import java.util.ArrayList

import me.shetj.base.base.BasePresenter
import me.shetj.base.base.IView

/**
 * **@packageName：** com.shetj.diyalbume.ppttest<br></br>
 * **@author：** shetj<br></br>
 * **@createTime：** 2019/1/11 0011<br></br>
 * **@company：**<br></br>
 * **@email：** 375105540@qq.com<br></br>
 * **@describe**<br></br>
 */
class IndexPresenter(view: IView) : BasePresenter<IndexModel>(view) {
    init {
        model = IndexModel()
    }

    fun initData() {
        val listIndex = ArrayList<ItemIndex>()
        val list = ArrayList<ItemIndex>()
        val itemIndex = model!!.createItem("1")
        for (i in 0..4) {
            list.add(model!!.createItem("1"))
        }
        itemIndex.itemIndex = list
        val itemIndex2 = model!!.createItem("2")
        itemIndex2.itemIndex = list
        val itemIndex3 = model!!.createItem("3")
        itemIndex3.itemIndex = list
        val itemIndex4 = model!!.createItem("4")
        itemIndex4.itemIndex = list

        listIndex.add(itemIndex)
        listIndex.add(itemIndex2)
        listIndex.add(itemIndex3)
        listIndex.add(itemIndex4)
        view.updateView(getMessage(1, listIndex))
    }
}
