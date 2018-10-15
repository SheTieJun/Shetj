package shetj.me.base.common

import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog
import com.qcshendeng.toyo.common.bean.UserInfo
import me.shetj.base.base.SimBaseCallBack
import me.shetj.base.tools.file.SPUtils
import me.shetj.base.tools.json.EmptyUtils
import me.shetj.base.tools.json.GsonKit
import me.shetj.testmybase.R
import shetj.me.base.configs.tag.SPKey

/**
 * **@packageName：** com.aycm.dsy.common<br></br>
 * **@author：** shetj<br></br>
 * **@createTime：** 2018/3/2<br></br>
 * **@company：**<br></br>
 * **@email：** 375105540@qq.com<br></br>
 * **@describe**用户管理<br></br>
 */

class UserManager private constructor() {
    private val context = x.app().applicationContext
    private var custom: UserInfo? = null


    val isLoginNow: Boolean
        get() = TokenManager.getTokenManager()!!.isLogin

    val userInfo: UserInfo?
        get() {
            if (isLoginNow && custom == null) {
                val userInfo = SPUtils.get(context, SPKey.SAVE_USER, "") as String
                if (EmptyUtils.isNotEmpty(userInfo)) {
                    custom = GsonKit.jsonToBean(userInfo, UserInfo::class.java)
                }
            }
            return custom
        }


    val uid: String?
        get() = if (isLoginNow) {
            userInfo!!.uid
        } else {
            ""
        }

    val vip: String?
        get() = userInfo!!.is_vip

    fun isLogin(context: Context): Boolean {
        if (!isLoginNow) {
            val materialdialog = MaterialDialog.Builder(context)
                    .title("登录提示")
                    .content("您还没有登录,无法继续操作！")
                    .positiveText("取消")
                    .positiveColorRes(R.color.actionSheet_blue)
                    .negativeColorRes(R.color.actionSheet_blue)
                    .onPositive { dialog, which -> dialog.dismiss() }
                    .onNegative { dialog, which -> loginIn(context) }
                    .negativeText("确定").build()
            materialdialog.show()
            return false
        }
        return true
    }

    private fun loginIn(context: Context) {

    }

    fun loginOut() {
        custom = null
        TokenManager.getTokenManager()?.token = ""
        cleanUserInfo()
    }

    fun saveUserInfo(userInfo: String) {
        SPUtils.put(context, SPKey.SAVE_USER, userInfo)
    }

    fun saveUserInfo(userInfo: UserInfo, commonCallback: SimBaseCallBack<*>) {
        custom = userInfo
        TokenManager.getTokenManager()?.token = custom!!.token!!
        SPUtils.put(context, SPKey.SAVE_USER, GsonKit.objectToJson(userInfo)!!)
    }


    private fun cleanUserInfo() {
        custom = null
        TokenManager.getTokenManager()?.token = ""
        SPUtils.put(context, SPKey.SAVE_USER, "")
    }

    companion object {
        val instance = UserManager()
    }
}
