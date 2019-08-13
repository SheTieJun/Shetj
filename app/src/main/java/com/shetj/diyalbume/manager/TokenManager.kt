package com.shetj.diyalbume.manager

import io.reactivex.Observable
import me.shetj.base.s
import me.shetj.base.tools.file.SPUtils
import me.shetj.base.tools.json.EmptyUtils

/**
 * Created by admin on 2017/10/16.
 */

class TokenManager private constructor() {


    val token: Observable<String>
        get() {
            val cacheToken = TokenLoader.instance.cacheToken
            return if (cacheToken.contains("token_fail")) {
                TokenLoader.instance.netTokenLocked
            } else {
                Observable.just(cacheToken)
            }
        }


    /**
     * Is login boolean.
     * @return the boolean
     */
    val isLogin: Boolean
        get() {
            val token = SPUtils.get(s.app.applicationContext, "PRE_CUSTOM_TOKEN", "") as String?
            return EmptyUtils.isNotEmpty(token!!)
        }


    fun setToken(token: String) {
        SPUtils.put(s.app.applicationContext, "PRE_CUSTOM_TOKEN", token)
    }

    companion object {

        private var instance: TokenManager? = null

        fun getInstance(): TokenManager {
            if (instance == null) {
                synchronized(TokenManager::class.java) {
                    if (instance == null) {
                        instance = TokenManager()
                    }
                }
            }
            return instance!!
        }
    }
}
