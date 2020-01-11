package me.shetj.dlna.listener

import androidx.annotation.IntRange
import org.fourthline.cling.model.action.ActionInvocation

interface DLNAControlCallback {
    fun onSuccess(invocation: ActionInvocation<*>?)
    fun onReceived(invocation: ActionInvocation<*>?, vararg extra: Any?)
    fun onFailure(invocation: ActionInvocation<*>?,
                  @IntRange(from = ERROR_CODE_NO_ERROR.toLong(), to = ERROR_CODE_BIND_SCREEN_RECORDER_SERVICE_ERROR.toLong()) errorCode: Int,
                  errorMsg: String?)

    companion object {
        const val ERROR_CODE_NO_ERROR = 0
        const val ERROR_CODE_RE_PLAY = 1
        const val ERROR_CODE_RE_PAUSE = 2
        const val ERROR_CODE_RE_STOP = 3
        const val ERROR_CODE_DLNA_ERROR = 4
        const val ERROR_CODE_SERVICE_ERROR = 5
        const val ERROR_CODE_NOT_READY = 6
        const val ERROR_CODE_BIND_SCREEN_RECORDER_SERVICE_ERROR = 7
    }
}