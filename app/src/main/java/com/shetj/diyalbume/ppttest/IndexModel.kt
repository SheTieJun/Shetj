package com.shetj.diyalbume.ppttest

import me.shetj.base.base.BaseModel

/**
 * **@packageName：** com.shetj.diyalbume.ppttest<br></br>
 * **@author：** shetj<br></br>
 * **@createTime：** 2019/1/11 0011<br></br>
 * **@company：**<br></br>
 * **@email：** 375105540@qq.com<br></br>
 * **@describe**<br></br>
 */
class IndexModel : BaseModel() {
    fun createItem(type: String): ItemIndex {
        return ItemIndex(type, "http://oss.tuyuing.com/TUYU/Avatar/2019-01-05/151546653814366.jpg", "content", "title")
    }
}
