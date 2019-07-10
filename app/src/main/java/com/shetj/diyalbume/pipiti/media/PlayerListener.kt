package com.shetj.diyalbume.pipiti.media

/**
 * **@packageName：** com.shetj.diyalbume.pipiti.utils<br></br>
 * **@author：** shetj<br></br>
 * **@createTime：** 2018/10/24 0024<br></br>
 * **@company：**<br></br>
 * **@email：** 375105540@qq.com<br></br>
 * **@describe**<br></br>
 */
interface PlayerListener {

    /**
     * 是否循环
     * @return  true 是  false 结束
     */
    val isLoop: Boolean

    /**
     * 开始播放
     * @param url 播放路径
     */
    fun onStart(url: String)

    /**
     * 暂停
     */
    fun onPause()

    /**
     * 继续播放
     */
    fun onResume()

    /**
     * 停止释放
     */
    fun onStop()

    /**
     * 播放结束
     */
    fun onCompletion()

    /**
     * 错误
     * @param throwable
     */
    fun onError(throwable: Throwable)

    /**
     * 是否下一首
     * @param mp 音乐播放器
     * @return true 是  false 结束
     */
    fun isNext(mp: MediaPlayerUtils): Boolean

    /**
     * 进度条
     * @param current 当前播放位置
     * @param size 一共
     */
    fun onProgress(current: Int, size: Int)
}
