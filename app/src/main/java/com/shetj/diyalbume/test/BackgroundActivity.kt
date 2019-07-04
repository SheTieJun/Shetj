package com.shetj.diyalbume.test

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Route
import com.shetj.diyalbume.R
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_background.*
import kotlinx.android.synthetic.main.content_background.*
import me.shetj.base.base.BaseActivity
import me.shetj.base.base.BasePresenter
import me.shetj.base.tools.app.ArmsUtils
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

@Route(path = "/shetj/BackgroundActivity")
class BackgroundActivity : BaseActivity<BasePresenter<*>>() {

    private lateinit var  mPublishSubject: PublishSubject<Double>
    private lateinit var  mPublishSubjectS: PublishSubject<String>
    private var mCompositeDisposable = CompositeDisposable()

    private val TAG = "xxxxxxx"
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
                    } catch (exception : InterruptedException) {
                        if (!e.isDisposed) {
                            e.onError(exception)
                        }
                    }

                    e.onNext(i)
                }
            }
            e.onComplete()
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
                    Timber.i(e.message!!)
                },{

                    Timber.i("完成")
                })

        mPublishSubjectS = PublishSubject.create<String>()

        mPublishSubjectS.debounce(200,TimeUnit.MILLISECONDS)
                .filter { s -> s.isNotEmpty()  }
                .switchMap { s -> getSearchInfo(s) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { s -> ArmsUtils.longSnackbar(this,s) }

        startSearch("s")

        startNet()

        startPolling()

        startNet2()

        startRetryError()

        startConcat()

        startConcatEager()

    }

    private fun startConcatEager() {
        var listOb = ArrayList<Observable<ArrayList<String>>>()

        listOb.add(getOld(500))
        listOb.add(getNew(1000))

    }

    private fun startConcat() {

        Observable.concat(getOld(500).subscribeOn(Schedulers.io()),
                getNew(1000).subscribeOn(Schedulers.io()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    list ->
                    Timber.i(list.size.toString())

                },{e ->
                    Timber.i(e.message)
                },{
                    Timber.i("ok")
                })
    }

    private fun getNew(simulateTime : Long): Observable<ArrayList<String>>  {
        return Observable.create {ob ->
            try {
                Log.d(TAG, "开始加载网络数据")
                Thread.sleep(simulateTime)
                var results = ArrayList<String>()
                (1..10).mapTo(results) { "网络New"+"序号=" + it }
                ob.onNext(results)
                ob.onComplete()
                Log.d(TAG, "结束加载网络数据")
            } catch ( e:InterruptedException) {
                if (!ob.isDisposed) {
                    ob.onError(e)
                }
            }
        }

    }

    private fun getOld(simulateTime : Long): Observable<ArrayList<String>>  {
        return Observable.create {ob ->
            try {
                Timber.d( "开始加载网络数据")
                Thread.sleep(simulateTime)
                var results = ArrayList<String>()
                (1..10).mapTo(results) { "网络old"+"序号=" + it }
                ob.onNext(results)
                ob.onComplete()
                Timber.d( "结束加载网络数据")
            } catch ( e:InterruptedException) {
                if (!ob.isDisposed) {
                    ob.onError(e)
                }
            }
        }
    }

    private fun startRetryError() {

        var number = 0
        Observable.create<String> {e->
            doWork()
            if (number < 5){

                e.onError(Throwable("wait"))
            }else {
                e.onNext("ok")
                e.onComplete()
            }
        }.retryWhen(Function { t -> return@Function t.flatMap { t1 ->
            ArmsUtils.makeText(t1.message)
            if (number < 5){
                number++
                return@flatMap  Observable.timer(1000, TimeUnit.MILLISECONDS)

            }else{
                return@flatMap  Observable.error<String>(Throwable("wait"))
            }
        } }).subscribe({value->
            Log.d(TAG, "DisposableObserver onNext=" + value)
        },
                { e->
                    Log.d(TAG, "DisposableObserver onError=" + e)
                },
                {
                    Log.d(TAG, "DisposableObserver onComplete")
                })

    }

    private fun startNet2() {


    }

    private fun startPolling(){
        Observable.intervalRange(0, 5, 0, 3000, TimeUnit.MILLISECONDS)
                .take(5)
                .doOnNext {
                    doWork()
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                }

        var i = 0
        Observable.just(0L).doOnComplete {
            doWork()
        }.repeatWhen (Function { ob ->

            return@Function ob.flatMap{
                if ( i <=  4) {
                    return@flatMap Observable.timer((3000 + i * 1000).toLong(), TimeUnit.MILLISECONDS)
                }
                return@flatMap Observable.error<String>(Throwable("Polling work finished"))
            }

        })
    }

    private fun doWork() {
        val workTime = Math.random()*500 +500
        try {
            Log.d("xx", "doWork start,  threadId=" + Thread.currentThread().id);
            Thread.sleep(workTime.toLong())
            Log.d("xx", "doWork finished");
        } catch (e:InterruptedException ) {
            e.printStackTrace()
        }

    }

    private fun startNet() {
        Observable.just("page").subscribeOn(Schedulers.io())
                .flatMap { s ->
                    val s1= startApi(s)
                    val s2 = startApi(s)
                    return@flatMap Observable.zip(
                            s1,
                            s2,
                            BiFunction<String,String,ArrayList<String>> { ss1, ss2 ->
                                val arrayList = ArrayList<String>()
                                arrayList.add(ss1)
                                arrayList.add(ss2)
                                return@BiFunction arrayList
                            }
                    )
                }

    }

    private fun startApi( page :String) :Observable<String> {
        val build = Retrofit.Builder().baseUrl("http://baidu.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(Test2API::class.java)

        return build.getInfo(page)

    }

    private fun startSearch(s: String) {
        mPublishSubjectS.onNext(s)

    }

    private fun getSearchInfo(s: String): ObservableSource<String>? {

        return Observable.create {
            e ->
            e.onNext(s+"s1")
        }
    }


    override fun onDestroy() {
        mCompositeDisposable.clear()
        super.onDestroy()

    }

}
