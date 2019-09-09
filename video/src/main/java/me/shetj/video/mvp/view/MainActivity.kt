package me.shetj.video.mvp.view

import android.os.Bundle
import android.os.Message
import com.google.android.material.snackbar.Snackbar
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*
import me.shetj.base.base.BaseActivity
import me.shetj.video.R
import me.shetj.video.mvp.presenter.MainPresenter
import me.shetj.video.mvp.presenter.MainPresenter.Companion.INIT_VIDEO_INFO

class MainActivity : BaseActivity<MainPresenter>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->


        }
        initView()
        initData()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun initView() {


    }
    override fun initData() {
        mPresenter = MainPresenter(this)
        mPresenter?.initDate()
    }

    override fun updateView(message: Message) {
        super.updateView(message)
        when(message.what){
            INIT_VIDEO_INFO ->{

            }
        }
    }

}
