package com.shetj.diyalbume

import android.content.Context
import androidx.annotation.Keep

import com.taobao.sophix.PatchStatus
import com.taobao.sophix.SophixApplication
import com.taobao.sophix.SophixEntry
import com.taobao.sophix.SophixManager
import com.taobao.sophix.listener.PatchLoadStatusListener

import timber.log.Timber

class SophixStubApplication : SophixApplication() {
    // 此处SophixEntry应指定真正的Application，并且保证RealApplicationStub类名不被混淆。
    @Keep
    @SophixEntry(App::class)
    internal class RealApplicationStub

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        //         如果需要使用MultiDex，需要在此处调用。
        initSophix()
    }

    private fun initSophix() {
        var appVersion = "0.0.0"
        try {
            appVersion = this.packageManager
                    .getPackageInfo(this.packageName, 0)
                    .versionName
        } catch (e: Exception) {
        }

        val instance = SophixManager.getInstance()
        instance.setContext(this)
                .setAppVersion(appVersion)
                .setSecretMetaData("25070458", "57886dc7c9aa8e86747b324999f7ec8d", null)
                .setEnableDebug(true)
                .setEnableFullLog()
                .setPatchLoadStatusStub { mode, code, info, handlePatchVersion ->
                    if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                        Timber.i("sophix load patch success!")
                    } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                        // 如果需要在后台重启，建议此处用SharePreference保存状态。
                        Timber.i("sophix preload patch success. restart app to make effect.")
                    }
                }.initialize()
    }
}