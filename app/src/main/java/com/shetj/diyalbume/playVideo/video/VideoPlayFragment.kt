package com.shetj.diyalbume.playVideo.video

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shetj.diyalbume.R
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import kotlinx.android.synthetic.main.fragment_video_play.*
import me.shetj.base.base.BaseFragment
import me.shetj.base.base.BasePresenter
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * 1.掉用全屏没有成功，发现是没有       android:configChanges="orientation|screenSize|keyboardHidden"
 */
class VideoPlayFragment : BaseFragment<BasePresenter<*>>() {


    private var isPause: Boolean = false
    private  var orientationUtils: OrientationUtils ?=null
    private var param1: String? = null
    private var param2: String? = null

    private var isPlay = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_video_play, container, false)
    }

    override fun initEventAndData() {

        //外部辅助的旋转，帮助全屏
        orientationUtils = OrientationUtils(rxContext, videoView)
        //初始化不打开外部的旋转
        orientationUtils?.isEnable = false

        videoView.setUp("https://vod.lycheer.net/e22cd48bvodtransgzp1253442168/d6b59e205285890789389180692/v.f20.mp4",true,"")

        videoView.setIsTouchWiget(true)

        videoView.isRotateViewAuto = false
//        videoView.isShowFullAnimation = true
        videoView.isReleaseWhenLossAudio = true //焦点释放

        videoView.fullscreenButton.setOnClickListener {
//            //直接横屏
            orientationUtils?.resolveByClick()

            //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
            videoView.startWindowFullscreen(rxContext, true, true)
        }

        videoView.setVideoAllCallBack(object : GSYSampleCallBack(){
            override fun onQuitFullscreen(url: String?, vararg objects: Any?) {
                super.onQuitFullscreen(url, *objects)
                orientationUtils?.backToProtVideo()
            }

            override fun onPrepared(url: String?, vararg objects: Any?) {
                super.onPrepared(url, *objects)
                //开始播放了才能旋转和全屏
                orientationUtils?.isEnable = true
                isPlay = true
            }
        })


        videoView.setLockClickListener { view, lock ->
            //配合下方的onConfigurationChanged
            orientationUtils?.isEnable = !lock
        }

        videoView.startPlayLogic()
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
//        //如果旋转了就全屏
        if (isPlay && !isPause) {
            videoView.onConfigurationChanged(rxContext, newConfig, orientationUtils, true, true)
        }
    }

    override fun onPause() {
        super.onPause()
        isPause = true
    }

    override fun onResume() {
        super.onResume()
        isPause = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        videoView?.currentPlayer?.release()
        GSYVideoManager.releaseAllVideos()
        orientationUtils?.releaseListener()
    }


    fun onBackPressed(): Boolean {
        orientationUtils?.backToProtVideo()
        return !GSYVideoManager.backFromWindowFull(activity)
    }

}
