package shetj.me.base.configs.tag

/**
 * **@packageName：** com.aycm.dsy.event<br></br>
 * **@author：** shetj<br></br>
 * **@createTime：** 2018/2/28<br></br>
 * **@company：**<br></br>
 * **@email：** 375105540@qq.com<br></br>
 * **@describe** shareP 的key<br></br>
 */

interface SPKey {
    companion object {
        val SAVE_TOKEN = "save_token"
        val SAVE_TOKEN_TIME = "save_token_time"
        val SAVE_USER = "save_userInfo"
        val SAVE_USER_PHONE = "save_user_phone"
        val SAVE_USER_NAME = "save_user_name"
        val SAVE_USER_ADDRESS = "save_user_address"
        val SAVE_QINIU_TOKEN = "save_qiniu_token"
    }
}
