package shetj.me.base.common

import me.shetj.base.tools.file.SPUtils
import me.shetj.base.tools.json.EmptyUtils
import org.xutils.x
import shetj.me.base.configs.tag.SPKey.Companion.SAVE_TOKEN


/**
 *
 * @author shetj
 * @date 2017/10/16
 */

class TokenManager private constructor() {


    var token: String
        get() {
            val token = SPUtils[x.app().applicationContext, SAVE_TOKEN, ""] as String
            return if (EmptyUtils.isEmpty(token)) {
                ""
            } else token

        }
        set(token) = SPUtils.put(x.app().applicationContext, SAVE_TOKEN, token)
    val isLogin: Boolean
        get() {
            val token = SPUtils[x.app().applicationContext, SAVE_TOKEN, ""] as String
            return EmptyUtils.isNotEmpty(token)
        }

    companion object {


        private var instance: TokenManager? = null

        fun getTokenManager(): TokenManager? {
            if (instance == null) {
                synchronized(TokenManager::class.java) {
                    if (instance == null) {
                        instance = TokenManager()
                    }
                }
            }
            return instance
        }
    }


}
