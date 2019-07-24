package com.shetj.diyalbume.playVideo.video

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.shetj.diyalbume.R
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer

/**
 *
 */
class CustomVideoView :StandardGSYVideoPlayer{

    private var speedImage :ImageView ?=null //变数
    private var videoList :TextView ?=null //视频源

    constructor(context: Context?) : super(context)
    constructor(context: Context?, fullFlag: Boolean?) : super(context, fullFlag)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)


    override fun getLayoutId(): Int {
        if (mIfCurrentIsFullscreen) {
            return R.layout.video_custom_layout
        }else{
            return R.layout.video_custom_layout
        }
    }

    override fun touchSurfaceMoveFullLogic(absDeltaX: Float, absDeltaY: Float) {
        super.touchSurfaceMoveFullLogic(absDeltaX, absDeltaY)
    }


    override fun getEnlargeImageRes(): Int {
        return R.drawable.ic_media_fullscreen
    }


    override fun getShrinkImageRes(): Int {
        return R.drawable.ic_exit_media_fullscreen
    }

    override fun init(context: Context?) {
        super.init(context)
        speedImage = findViewById(R.id.iv_speed_small)
        videoList = findViewById(R.id.tv_video_list)
        videoList?.setOnClickListener(this)
        speedImage?.setOnClickListener(this)
    }


    /**
     * 判断播放状态来确定调用哪个接口
     * 不确定两个接口是相同
     */
    override fun setSpeed(speed: Float) {
        if (isInPlayingState) {
            super.setSpeedPlaying(speed, false)
        } else {
            super.setSpeed(speed)
        }
    }

    override fun getVolumeLayoutId(): Int {
        return super.getVolumeLayoutId()
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when(v?.id){
            R.id.iv_speed_small ->{

            }
            R.id.tv_video_list ->{

            }
        }
    }

}