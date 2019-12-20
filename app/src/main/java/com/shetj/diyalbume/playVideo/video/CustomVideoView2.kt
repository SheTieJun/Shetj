package com.shetj.diyalbume.playVideo.video

import android.content.Context
import android.media.AudioManager
import android.os.Handler
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.shetj.diyalbume.R
import com.shuyu.gsyvideoplayer.utils.Debuger
import com.shuyu.gsyvideoplayer.utils.NetworkUtils
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYVideoView
import moe.codeest.enviews.ENDownloadView
import java.util.*

class CustomVideoView2 :StandardGSYVideoPlayer{

    private var mLlSpeedList: LinearLayout?=null
    private var mLlVideoList: LinearLayout? =null
    private var speedImage :ImageView ?=null //变数
    private var videoList :TextView ?=null //视频源

    private var listener: VideoStatusListener? = null

    private var mUrlList: List<SwitchVideoModel> = ArrayList()
    //数据源
    private var mSourcePosition = 0
    private var refererHeader: HashMap<String, String>? = null

    private var tvMsg: TextView? = null

    private var mRecyclerViewVideo : RecyclerView? =null
    private var mIRecyclerView : RecyclerView? =null


    constructor(context: Context?) : super(context)
    constructor(context: Context?, fullFlag: Boolean?) : super(context, fullFlag)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)


    override fun getLayoutId(): Int {
        return R.layout.video_custom_layout
    }

    /**
     * 显示wifi确定框，如需要自定义继承重写即可
     */
    override fun showWifiDialog() {
        if (!NetworkUtils.isAvailable(mContext)) {
            startPlayLogic()
            return
        }
    }

    /**
     * 全屏时将对应处理参数逻辑赋给全屏播放器
     *
     * @param context
     * @param actionBar
     * @param statusBar
     * @return
     */

    override fun startWindowFullscreen(context: Context?, actionBar: Boolean, statusBar: Boolean): GSYBaseVideoPlayer {
        val sampleVideo =   super.startWindowFullscreen(context, actionBar, statusBar) as CustomVideoView2
        sampleVideo.mSourcePosition = mSourcePosition
        sampleVideo.mCurrentPosition = mCurrentPosition
        sampleVideo.mUrlList = mUrlList
        sampleVideo.seekOnStart = mSeekOnStart
        sampleVideo.speed = mSpeed
        sampleVideo.mOriginUrl = mOriginUrl
        sampleVideo.listener = listener
        sampleVideo.isAutoFullWithSize = true
        if (mUrlList.isNotEmpty()) {
            sampleVideo.videoList?.text = mUrlList[mSourcePosition].name
        }
        return sampleVideo
    }

    /**
     * 推出全屏时将对应处理参数逻辑返回给非播放器
     * @param oldF
     * @param vp
     * @param gsyVideoPlayer
     */
    override fun resolveNormalVideoShow(oldF: View?, vp: ViewGroup?, gsyVideoPlayer: GSYVideoPlayer?) {
        super.resolveNormalVideoShow(oldF, vp, gsyVideoPlayer)
        if (gsyVideoPlayer != null) {
            val sampleVideo =   gsyVideoPlayer as CustomVideoView2
            mSourcePosition = sampleVideo.mSourcePosition
            mCurrentPosition =  sampleVideo.mCurrentPosition
            mOriginUrl =  sampleVideo.mOriginUrl
            mSeekOnStart = sampleVideo.mSeekOnStart
            if (mUrlList.isNotEmpty()) {
                videoList?.text = mUrlList[mSourcePosition].name
            }
            showSpeedImage()
        }
    }

    override fun getEnlargeImageRes(): Int {
        return R.drawable.ic_media_fullscreen
    }


    override fun getShrinkImageRes(): Int {
        return R.drawable.ic_exit_media_fullscreen
    }


    /**
     * 更新开始按钮
     */
    override fun updateStartImage() {
        if (mStartButton is ImageView) {
            val imageView = mStartButton as ImageView
            when (mCurrentState) {
                GSYVideoView.CURRENT_STATE_PLAYING -> imageView.setImageResource(R.drawable.ic_media_pause)
                GSYVideoView.CURRENT_STATE_ERROR -> imageView.setImageResource(R.drawable.ic_media_play)
                else -> imageView.setImageResource(R.drawable.ic_media_play)
            }
        }
    }


    override fun init(context: Context?) {
        super.init(context)
        speedImage = findViewById(R.id.iv_speed_small)
        videoList = findViewById(R.id.tv_video_list)
    }

    override fun setIfCurrentIsFullscreen(ifCurrentIsFullscreen: Boolean) {
        super.setIfCurrentIsFullscreen(ifCurrentIsFullscreen)
        showSpeedImage()
    }

    /**
     * 展示不同的speed图片
     */
    private fun showSpeedImage() {
        when (currentPlayer.speed) {
            1.0f -> speedImage?.setImageResource(R.drawable.video_player_1_0_speed)
        }
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

    override fun onClick(v: View?) {
        super.onClick(v)
        when(v?.id){
            R.id.iv_speed_small ->{

            }
            R.id.tv_video_list ->{

            }
            R.id.thumb ->{
                changeUiToNormal()
            }
        }
    }


    public override fun hideAllWidget() {
        super.hideAllWidget()
    }

    /**
     * 获取当前播放进度
     */
    override fun getCurrentPositionWhenPlaying(): Int {
        var position = 0
        if (mCurrentState == GSYVideoView.CURRENT_STATE_PLAYING || mCurrentState == GSYVideoView.CURRENT_STATE_PAUSE
                || currentPlayer.currentState == GSYVideoView.CURRENT_STATE_PLAYING_BUFFERING_START) {
            try {
                position = gsyVideoManager.currentPosition.toInt()
            } catch (e: Exception) {
                e.printStackTrace()
                return position
            }

        }
        return if (position == 0 && mCurrentPosition > 0) {
            mCurrentPosition.toInt()
        } else position
    }


    fun getCurrentPosition(): Int {
        return currentPositionWhenPlaying
    }

    fun setListener(listener: VideoStatusListener) {
        this.listener = listener
    }


    fun setUrlList(mUrlList: List<SwitchVideoModel>, refererHeader: HashMap<String, String>, tvVideoList: TextView) {
        this.mUrlList = mUrlList
        this.refererHeader = refererHeader
        for (switchVideoModel in mUrlList) {
            if (switchVideoModel.name != null && switchVideoModel.name == "流畅") {
                tvVideoList.text = switchVideoModel.name
                mSourcePosition = mUrlList.indexOf(switchVideoModel)
                setUp(switchVideoModel.url, false, null, refererHeader, "")
                return
            }
        }
        setUp(mUrlList[mSourcePosition].url, false, null, refererHeader, "")
        tvVideoList.text = mUrlList[mSourcePosition].name
    }


    fun setUrlList(url: String, mUrlList: List<SwitchVideoModel>, refererHeader: HashMap<String, String>, title: String, tvVideoList: TextView) {
        this.mUrlList = mUrlList
        this.refererHeader = refererHeader
        for (switchVideoModel in mUrlList) {
            if (switchVideoModel.url == url) {
                tvVideoList.text = switchVideoModel.name
                mSourcePosition = mUrlList.indexOf(switchVideoModel)
                setUp(switchVideoModel.url, false, null, refererHeader, title)
                return
            }
        }
        setUp(mUrlList[mSourcePosition].url, false, null, refererHeader, title)
        tvVideoList.text = mUrlList[mSourcePosition].name
    }

    override fun changeUiToNormal() {
        Debuger.printfLog("changeUiToNormal")

        setViewShowState(mTopContainer, View.VISIBLE)
        setViewShowState(mBottomContainer, View.VISIBLE)
        setViewShowState(mStartButton, View.VISIBLE)
        setViewShowState(mLoadingProgressBar, View.INVISIBLE)
        setViewShowState(mThumbImageViewLayout, View.VISIBLE)
        setViewShowState(mBottomProgressBar, View.INVISIBLE)
        setViewShowState(mLockScreen, if (mIfCurrentIsFullscreen && mNeedLockFull) View.VISIBLE else View.GONE)

        updateStartImage()
        if (mLoadingProgressBar is ENDownloadView) {
            (mLoadingProgressBar as ENDownloadView).reset()
        }
    }

    override fun onSeekComplete() {
        super.onSeekComplete()
        if (listener != null) {
            listener!!.onSeekComplete()
        }
    }

    fun showMsg(msg: String) {
        tvMsg!!.visibility = View.VISIBLE
        tvMsg!!.text = msg
    }

    fun showMsg(result: SpannableStringBuilder) {
        tvMsg!!.visibility = View.VISIBLE
        tvMsg!!.text = result
    }

    fun showMsg(result: SpannableStringBuilder, hideAfter: Long) {
        var hideAfter = hideAfter
        tvMsg!!.visibility = View.VISIBLE
        tvMsg!!.text = result
        if (hideAfter <= 0) {
            hideAfter = 700
        }
        tvMsg!!.postDelayed({ tvMsg!!.visibility = View.GONE }, hideAfter)
    }


    fun showMsg(msg: String, delay: Long) {
        var delay = delay
        tvMsg!!.visibility = View.VISIBLE
        tvMsg!!.text = msg
        if (delay < 0) delay = 700
        tvMsg!!.postDelayed({ tvMsg!!.visibility = View.GONE }, delay)
    }

    fun hideMsg() {
        tvMsg!!.visibility = View.GONE
    }


    override fun onError(what: Int, extra: Int) {
        if (NetworkUtils.isAvailable(context)){
            super.onError(what, extra)
        }
        if (listener != null) {
            listener!!.onError(what, extra)
        }
    }

    override fun onPrepared() {
        super.onPrepared()
        listener?.onPrepared()
        hideMsg()
    }


    fun isPlaying(): Boolean {
        return currentPlayer.currentState == GSYVideoView.CURRENT_STATE_PLAYING || currentPlayer.currentState == GSYVideoView.CURRENT_STATE_PLAYING_BUFFERING_START
    }

    fun start() {
        if (mCurrentState == GSYVideoView.CURRENT_STATE_PAUSE) {
            currentPlayer.onVideoResume(false)
        } else {
            currentPlayer.startPlayLogic()
        }
    }

    /**
     * 恢复暂停状态
     *
     * @param seek 是否产生seek动作
     */
    override fun onVideoResume(seek: Boolean) {
        mPauseBeforePrepared = false
        if (mCurrentState == GSYVideoView.CURRENT_STATE_PAUSE) {
            try {
                if (mCurrentPosition >= 0 && gsyVideoManager != null) {
                    if (seek) {
                        gsyVideoManager.seekTo(mCurrentPosition)
                    }
                    gsyVideoManager.start()
                    setStateAndUi(GSYVideoView.CURRENT_STATE_PLAYING)
                    if (mAudioManager != null && !mReleaseWhenLossAudio) {
                        mAudioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)
                    }
                    mCurrentPosition = 0
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }


    fun pause() {
        currentPlayer.onVideoPause()
    }

    fun setSpeedChange(curSpeedRate: Float, isFirst: Boolean) {
        if (isInPlayingState) {
            setSpeedPlaying(curSpeedRate, false)
        } else {
            speed = curSpeedRate
        }
        // 首次加载不显示 toast
        if (!isFirst) {
            val result = StringFormatUtil.getSpannableStringBuilder(mContext, "已切换到 $curSpeedRate 倍速播放", curSpeedRate.toString())
            if (result == null) {
                showMsg("已切换到 $curSpeedRate 倍速播放", 700)
            } else {
                showMsg(result, 700)
            }
        }

    }

    override fun onAutoCompletion() {
        super.onAutoCompletion()
        listener?.onCompletion()
    }

    fun getCurrentUrl(): String {
        return mOriginUrl
    }

    /**
     * 全屏切换清晰度
     */
    fun changeUrl(url: String, tvVideoList: TextView): Boolean {
        if (!mHadPlay) {
            Toast.makeText(context, "您还没有开始播放~请开始播放后切换清晰度", Toast.LENGTH_LONG).show()
            return false
        }
        for (switchVideoModel in mUrlList) {
            val name = switchVideoModel.name
            if (url == mOriginUrl) {
                Toast.makeText(context, "已经是 $name", Toast.LENGTH_LONG).show()
                return false
            }
            if (switchVideoModel.url == url) {
                if (mCurrentState == GSYVideoPlayer.CURRENT_STATE_PLAYING
                        || mCurrentState == GSYVideoPlayer.CURRENT_STATE_PAUSE || mCurrentState == GSYVideoPlayer.CURRENT_STATE_NORMAL) {
                    tvVideoList.text = name
                    val result = StringFormatUtil.getSpannableStringBuilder(mContext, "正在切换为" + name + "清晰度", name)
                    if (result != null) {
                        showMsg(result)
                    } else {
                        showMsg("正在切换为" + name + "清晰度")
                    }
                    mSourcePosition = mUrlList.indexOf(switchVideoModel)
                    onVideoPause()
                    gsyVideoManager.releaseMediaPlayer()
                    cancelProgressTimer()
                    hideAllWidget()
                    Handler().postDelayed({
                        setUp(url, mCache, mCachePath, refererHeader, mTitle)
                        seekOnStart = mCurrentPosition
                        startPlayLogic()
                        cancelProgressTimer()
                        hideAllWidget()
                    }, 100)
                    return true
                }
            }
        }
        return false
    }

    /**
     * 小屏幕切换清晰度
     */
    fun changeUrl(mSwitchSize: TextView) {
        if (!mHadPlay) {
            Toast.makeText(context, "您还没有开始播放~请开始播放后切换清晰度", Toast.LENGTH_LONG).show()
            return
        }
        if (mUrlList.size == 1) {
            Toast.makeText(context, "该视频暂无其他清晰度可切换", Toast.LENGTH_LONG).show()
            return
        }
        val position = (mSourcePosition + 1) % mUrlList.size
        if (mCurrentState == GSYVideoPlayer.CURRENT_STATE_PLAYING || mCurrentState == GSYVideoPlayer.CURRENT_STATE_PAUSE) {
            val url = mUrlList[position].url
            val name = mUrlList[position].name
            mSwitchSize.text = name
            val result = StringFormatUtil.getSpannableStringBuilder(mContext, "正在切换为" + name + "清晰度", name)
            if (result != null) {
                showMsg(result)
            } else {
                showMsg("正在切换为" + name + "清晰度")
            }
            onVideoPause()
            gsyVideoManager.releaseMediaPlayer()
            cancelProgressTimer()
            hideAllWidget()
            Handler().postDelayed({
                setUp(url, mCache, mCachePath, refererHeader, mTitle)
                seekOnStart = mCurrentPosition
                startPlayLogic()
                cancelProgressTimer()
                hideAllWidget()
            }, 500)
            mSourcePosition = position
        }
    }

    interface VideoStatusListener {
        fun onPrepared()
        fun onError(i: Int, i1: Int): Boolean
        fun onSeekComplete()
        fun onCompletion()
        fun speedChange(speed: Float)
        fun showShare()
    }


}