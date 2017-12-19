package com.shetj.diyalbume.test

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import cn.a51mofang.base.base.BaseActivity
import cn.a51mofang.base.tools.app.LogUtil
import com.shetj.diyalbume.R
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_background.*
import kotlinx.android.synthetic.main.content_background.*
import java.util.concurrent.TimeUnit


class BackgroundActivity : BaseActivity() {

    private lateinit var  mPublishSubject: PublishSubject<Double>
    private var mCompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_background)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        initView()
    }

    override fun initView() {
        mTvDownload.setOnClickListener{ _ ->
            startDownLoad()
        }
    }
    override fun initData() {


    }
    private fun startDownLoad() {

        Observable.create(ObservableOnSubscribe<Int> { e ->
            for (i in 0..99) {
                if (i % 20 == 0) {
                    try {
                        Thread.sleep(500) //模拟下载的操作。
                    } catch (exception: InterruptedException) {
                        if (!e.isDisposed) {
                            e.onError(exception)
                        }
                    }

                    e.onNext(i)
                }
            }
            e.onComplete()
        })

        Observable.create(ObservableOnSubscribe<Int> {

        })


        Observable.create<Int> {e ->
            for (i in 0..99){
                if ( i % 20 == 0){
                    try {
                        Thread.sleep(500)
                    }catch (ex:InterruptedException){
                        if (!e.isDisposed){
                            e.onError(ex)
                        }
                    }
                }
                e.onNext(i)
            }
            e.onComplete()
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(
                        { value -> mTvDownloadResult.text ="Current Progress=" + value },
                        { e -> showMessage(e.message!!) },{ showMessage("ok") })

        mPublishSubject = PublishSubject.create()

        mPublishSubject
                .buffer(3000, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe ({ vs->
                        var result = 0.0
                        if (vs.size > 0) {
                            for (i in vs){
                                result += i;
                            }
                            result /= vs.size
                        }
                        Log.d("BufferActivity", "更新平均温度：" + result)

                },{e ->

                    LogUtil.show(e.message!!)

                },{

                    LogUtil.show("完成")
                })
        
    }


    override fun onDestroy() {
        mCompositeDisposable.clear()
        super.onDestroy()

    }

}
