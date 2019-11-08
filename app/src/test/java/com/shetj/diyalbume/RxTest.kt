package com.shetj.diyalbume

import com.shetj.diyalbume.rx.RxS
import io.reactivex.*
import me.shetj.base.tools.json.GsonKit
import org.junit.Test
import java.util.concurrent.TimeUnit

class RxTest {

    fun test(){
//        Single的SingleObserver中只有onSuccess、onError，并没有onComplete
        Single.just("test")
                .subscribe({

                },{

                })
//        Completable 只有 onComplete 和 onError 事件，同时 Completable 并没有map、flatMap等操作符，它的操作符比起 Observable/Flowable 要少得多。
        Completable.create {

        }.subscribe({

        },{

        })

        //创建
        Maybe.create<Int> {
            it.onSuccess(1)
        }.subscribe({

        },{},{})
        //
    }

    @Test
    fun testRepeat(){
        Flowable.just(1)
                .repeat(5)
                .subscribe {
                    print(it)
                }
    }


    @Test
    fun testCreate(){
        Observable.create {emitter: ObservableEmitter<String> ->
            emitter.onNext("testCreate")
            emitter.onNext("testCreate2")
        }
                .subscribe {
                    print(it)
                }


        RxS.checkMsg("xxxx").subscribe( {
            print(GsonKit.objectToJson(it))
        },{
            print(it)
        })
    }

    @Test
    fun testInterval(){
        Observable.interval(1L,TimeUnit.SECONDS)
                .map {
                    "testInterval"
                }
                .take(100)
                .subscribe {
                    println(it)
                }
    }


    @Test
    fun testRang(){
        Observable.range(0,10)
                .map {
                    it +10
                }
                .flatMap {
                    return@flatMap Observable.just(it)
                }
                .filter {
                    it>15
                }
                .buffer(10) //缓存起来
                .subscribe {
                    print(GsonKit.objectToJson(it))
                }
    }

    @Test
    fun  testStart(){
        //延迟操作
        Observable.timer(1,TimeUnit.SECONDS)
                .subscribe {
                    print(it)
                }
    }

    @Test
    fun testScan(){
        Observable.just(1,1,2,3,4,5)
                .distinct()//过滤相同的操作
                .scan { t1: Int, t2: Int ->
                    //累计值t1,t2是不断发射的值
                    Thread.sleep(200)
                    print("t1=$t1\n")
                    return@scan t1+t2
                }
                .subscribe {
                    print(GsonKit.objectToJson(it))
                }
    }





}