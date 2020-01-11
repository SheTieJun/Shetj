package com.shetj.diyalbume.ffmpeg

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shetj.diyalbume.R
import io.microshow.rxffmpeg.RxFFmpegInvoke

/**
 * 测试RxRxFFmpeg https://github.com/microshow/RxFFmpeg
 */
class RxFFmpegActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rx_ffmpeg)
        RxFFmpegInvoke.getInstance().setDebug(true);
    }
}
