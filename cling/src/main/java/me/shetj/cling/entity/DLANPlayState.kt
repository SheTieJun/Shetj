package me.shetj.cling.entity



object DLANPlayState {
    /** 播放状态  */
    const val PLAY = 1
    /** 暂停状态  */
    const val PAUSE = 2
    /** 停止状态  */
    const val STOP = 3
    /** 转菊花状态  */
    const val BUFFER = 4
    /** 投放失败  */
    const val ERROR = 5
    // 以下不算设备状态, 只是常量
    /** 主动轮询获取播放进度(在远程设备不支持播放进度回传时使用)  */
    const val GET_POSITION_POLING = 6
    /** 远程设备播放进度回传  */
    const val POSITION_CALLBACK = 7
    /** 投屏端播放完成  */
    const val PLAY_COMPLETE = 8
}