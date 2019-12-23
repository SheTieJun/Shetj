package com.shetj.diyalbume.pipiti.media

import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri

import java.util.concurrent.TimeUnit

import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import me.shetj.base.tools.json.EmptyUtils
import timber.log.Timber

/**
 * **@author：** shetj<br></br>
 * **@createTime：** 2018/10/23 0023<br></br>
 * **@email：** 375105540@qq.com<br></br>
 * **@describe** MediaPlayerUtils 音乐播放<br></br>
 *
 * ** 播放 [MediaPlayerUtils.playOrStop]}**<br></br>
 * ** 暂停  [MediaPlayerUtils.pause] <br></br>
 * ** 暂停  [MediaPlayerUtils.stopPlay] ()} <br></br>
 * <br></br>
 **** */
class MediaPlayerUtils : LifecycleListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnSeekCompleteListener {
    private var mCompositeDisposable: CompositeDisposable? = null
    private var mediaPlayer: MediaPlayer? = null
    private var listener: PlayerListener? = null
    /**
     * 获取当前播放的url
     * @return currentUrl
     */
    private var currentUrl = ""
    private var progressSubject: PublishSubject<Int>? = null
    private var timeDisposable: Disposable? = null

    /**
     * 是否暂停
     * @return
     */
    val isPause: Boolean
        get() = !(mediaPlayer != null && mediaPlayer!!.isPlaying)

    init {
        initMedia()
    }

    /**
     * 重新播放url
     * @param url
     * @param listener
     */
    private fun play(url: String, listener: PlayerListener?) {
        if (null != listener) {
            this.listener = listener
        }
        this.currentUrl = url
        if (null == mediaPlayer) {
            initMedia()
        }
        try {
            mediaPlayer!!.reset()
            mediaPlayer!!.setDataSource(Uri.parse(url).toString())
            mediaPlayer!!.prepareAsync()
            //监听
            mediaPlayer!!.setOnPreparedListener(this)
            mediaPlayer!!.setOnErrorListener(this)
            mediaPlayer!!.setOnCompletionListener(this)
            mediaPlayer!!.setOnSeekCompleteListener(this)
            //是否循环
            mediaPlayer!!.isLooping = listener!!.isLoop

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * 通过url 比较 进行播放 还是暂停操作
     * @param url 播放的url
     * @param listener 监听变化
     */
    fun playOrStop(url: String, listener: PlayerListener?) {
        Timber.i("url = %s", url)
        //判断是否是当前播放的url
        if (url == currentUrl && mediaPlayer != null) {
            if (listener != null) {
                this.listener = listener
            }
            if (mediaPlayer!!.isPlaying) {
                pause()
                this.listener!!.onPause()
            } else {
                resume()
                this.listener!!.onResume()
            }
        } else {
            //直接播放
            play(url, listener)
        }
    }

    /**
     * 修改是否循环
     * @param isLoop
     */
    fun changeLoop(isLoop: Boolean) {
        if (mediaPlayer != null) {
            mediaPlayer!!.isLooping = isLoop
        }
    }

    /**
     * 外部设置进度变化
     */
    fun progressChange(changeSize: Int) {
        if (mediaPlayer != null && EmptyUtils.isNotEmpty(currentUrl)) {
            mediaPlayer!!.seekTo(changeSize)
        }
    }


    /**
     * 清空播放信息
     */
    private fun release() {
        if (EmptyUtils.isNotEmpty(currentUrl)) {
            currentUrl = ""
        }
        //释放MediaPlay
        if (null != mediaPlayer) {
            mediaPlayer!!.release()
            mediaPlayer = null
        }
        unDispose()
    }

    /**
     * 开始计时
     */
    private fun startProgress() {
        if (timeDisposable == null) {
            addDispose(Flowable.interval(0, 500, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.newThread())
                    .subscribe { progressSubject!!.onNext(mediaPlayer!!.currentPosition) })
        }
    }

    /**
     * 停止计时
     */
    private fun stopProgress() {
        if (timeDisposable != null && !timeDisposable!!.isDisposed) {
            timeDisposable!!.dispose()
            timeDisposable = null
        }
    }

    /**
     * 暂停，并且停止计时
     */
    fun pause() {
        if (mediaPlayer != null && mediaPlayer!!.isPlaying
                && EmptyUtils.isNotEmpty(currentUrl)) {
            mediaPlayer!!.pause()
            listener!!.onPause()
            stopProgress()
        }
    }

    /**
     * 恢复，并且开始计时
     */
    fun resume() {
        if (mediaPlayer != null && !mediaPlayer!!.isPlaying
                && EmptyUtils.isNotEmpty(currentUrl)) {
            mediaPlayer!!.start()
            listener!!.onResume()
            startProgress()
        }
    }


    fun stopPlay() {
        if (null != mediaPlayer) {
            mediaPlayer!!.stop()
            listener!!.onStop()
            startProgress()
            release()
        }
    }

    /**
     * 设置媒体
     */
    private fun initMedia() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer()

            if (null == listener) {
                listener = SimPlayerListener()
            }

            mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
            progressSubject = PublishSubject.create()
            //设置进度控制
            val disposable = progressSubject!!
                    .observeOn(AndroidSchedulers.mainThread())
                    .throttleFirst(1, TimeUnit.MILLISECONDS)
                    .subscribe { aLong -> listener!!.onProgress(aLong!!, mediaPlayer!!.duration) }
            addDispose(disposable)
        }
    }

    override fun onStart() {}

    override fun onStop() {
        stopPlay()
    }

    override fun onResume() {
        resume()
    }

    override fun onPause() {
        pause()
    }

    override fun onDestroy() {
        Timber.i("MediaPlayerUtils onDestroy")
        release()
    }

    override fun onPrepared(mp: MediaPlayer) {
        Timber.i("MediaPlayerUtils onPrepared")
        mp.start()
        startProgress()
        listener!!.onStart(currentUrl)
    }


    override fun onError(mp: MediaPlayer, what: Int, extra: Int): Boolean {
        Timber.i("what = %d extra = %d", what, extra)
        listener!!.onError(Throwable(String.format("what = %d extra = %d", what, extra)))
        release()
        return true
    }

    override fun onCompletion(mp: MediaPlayer) {
        Timber.i("MediaPlayerUtils onCompletion")
        if (!listener!!.isNext(this)) {
            listener!!.onCompletion()
            stopProgress()
            release()
        }
    }

    /**
     * 将 [Disposable] 添加到 [CompositeDisposable] 中统一管理
     * 可在 {onDestroy() 中使用 [.unDispose] 停止正在执行的 RxJava 任务,避免内存泄漏
     *
     * @param disposable
     */
    private fun addDispose(disposable: Disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable!!.add(disposable)
    }

    /**
     * 停止集合中正在执行的 RxJava 任务
     */
    private fun unDispose() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable!!.clear()
        }
    }

    override fun onSeekComplete(mp: MediaPlayer) {
        if (null != mediaPlayer) {
            progressSubject!!.onNext(mediaPlayer!!.currentPosition)
        }
    }
}
