package com.shetj.diyalbume.pipiti.media

/**
 * **@packageName：** com.shetj.diyalbume.pipiti.utils<br></br>
 * **@author：** shetj<br></br>
 * **@createTime：** 2018/10/24 0024<br></br>
 * **@company：**<br></br>
 * **@email：** 375105540@qq.com<br></br>
 * **@describe**<br></br>
 */
open class SimPlayerListener : PlayerListener {

    override val isLoop: Boolean
        get() = false

    override fun onStart(url: String) {

    }

    override fun onPause() {

    }

    override fun onResume() {

    }

    override fun onStop() {

    }

    override fun onCompletion() {

    }

    override fun onError(throwable: Throwable) {

    }

    override fun isNext(mp: MediaPlayerUtils): Boolean {

        return false
    }

    override fun onProgress(current: Int, size: Int) {}


}
