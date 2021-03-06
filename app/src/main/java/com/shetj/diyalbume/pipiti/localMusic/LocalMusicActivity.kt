package com.shetj.diyalbume.pipiti.localMusic

import android.os.Bundle
import android.os.Message
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.shetj.diyalbume.R
import com.shetj.diyalbume.pipiti.media.MediaPlayerUtils
import com.shetj.diyalbume.pipiti.media.SimPlayerListener
import me.shetj.base.base.BaseActivity
import me.shetj.base.tools.app.ArmsUtils
import me.shetj.base.tools.app.ArmsUtils.Companion.statuInScreen
import me.shetj.base.tools.json.GsonKit
import timber.log.Timber
import java.util.*

/**
 * 本地音乐
 *
 * @author Administrator
 */
@Route(path = "/shetj/LocalMusicActivity")
class LocalMusicActivity : BaseActivity<LocalMusicPresenter>() {

    private var mIRecyclerView: RecyclerView? = null
    private var mAdapter: MusicSelectAdapter? = null
    private var mediaUtils: MediaPlayerUtils? = null
    private var currentPosition = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_local_music)
        statuInScreen( true)
    }

    override fun initView() {
        val title = findViewById<TextView>(R.id.toolbar_title)
        title.text = "本地音乐"
        findViewById<View>(R.id.toolbar_back).setOnClickListener { onBackPressed() }
        mediaUtils = MediaPlayerUtils()
        mIRecyclerView = findViewById<View>(R.id.IRecyclerView) as RecyclerView
        mAdapter = MusicSelectAdapter(ArrayList())
        mIRecyclerView?.adapter = mAdapter
        mAdapter!!.setOnItemClickListener { _, _, position -> ArmsUtils.makeText(GsonKit.objectToJson(mAdapter!!.getItem(position)!!)!!) }
        mAdapter!!.setOnItemChildClickListener { _, _, position ->
            currentPosition = position
            mAdapter!!.getItem(position)!!.url?.let {
                mediaUtils!!.playOrStop(it, object : SimPlayerListener() {
                    override fun onStart(url: String) {
                        super.onStart(url)
                        ArmsUtils.makeText(String.format("试听%s", mAdapter!!.getItem(position)!!.name))
                    }

                    override fun onPause() {
                        super.onPause()
                        ArmsUtils.makeText("暂停")
                    }

                    override fun onProgress(current: Int, size: Int) {
                        super.onProgress(current, size)
                        Timber.i("$current/$size")
                    }

                    override fun isNext(mp: MediaPlayerUtils): Boolean {
                        currentPosition++
                        return if (currentPosition < mAdapter!!.itemCount) {
                            mAdapter!!.getItem(currentPosition)!!.url?.let { it1 -> mp.playOrStop(it1, this) }
                            true
                        } else {
                            false
                        }
                    }

                    override fun onCompletion() {
                        super.onCompletion()
                        ArmsUtils.makeText("播放结束" + mAdapter!!.getItem(currentPosition)!!.url)
                    }
                })
            }
        }
    }

    override fun initData() {
        mPresenter = LocalMusicPresenter(this)
        mPresenter!!.initMusic()
    }

    override fun updateView(message: Message) {
        super.updateView(message)
        when (message.what) {
            1 -> mAdapter!!.setNewData(message.obj as MutableList<Music>)
            else -> {
            }
        }
        message.recycle()//释放
    }

    override fun onPause() {
        super.onPause()
        if (null != mediaUtils) {
            mediaUtils!!.onPause()
        }
    }

    override fun onResume() {
        super.onResume()
        if (null != mediaUtils) {
            mediaUtils!!.onResume()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (null != mediaUtils) {
            mediaUtils!!.onDestroy()
        }
    }
}
