package me.shetj.cling.entity

import org.fourthline.cling.model.action.ActionInvocation
import org.fourthline.cling.model.message.UpnpResponse

/**
 * 说明：Cling 库中，控制操作返回结果
 * 作者：zhouzhan
 * 日期：17/7/4 10:50
 */
class ClingResponse : IResponse<ActionInvocation<*>?> {
    private var mActionInvocation: ActionInvocation<*>? = null
    private var operation: UpnpResponse? = null
    private var defaultMsg: String? = null

    /**
     * 控制操作成功 构造器
     *
     * @param actionInvocation  cling action 调用
     */
    constructor(actionInvocation: ActionInvocation<*>) {
        mActionInvocation = actionInvocation
    }

    /**
     * 控制操作失败 构造器
     *
     * @param actionInvocation  cling action 调用
     * @param operation     执行状态
     * @param defaultMsg    错误信息
     */
    constructor(actionInvocation: ActionInvocation<*>, operation: UpnpResponse, defaultMsg: String) {
        mActionInvocation = actionInvocation
        this.operation = operation
        this.defaultMsg = defaultMsg
    }

    override fun getResponse(): ActionInvocation<*> ?{
        return mActionInvocation
    }

    override fun setResponse(response: ActionInvocation<*>?) {
        mActionInvocation = response
    }
}