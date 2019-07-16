package com.shetj.diyalbume

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import me.shetj.base.tools.json.GsonKit
import org.junit.Test
import java.util.concurrent.TimeUnit

class RxTest {
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
    }

    @Test
    fun testInterval(){
        Observable.interval(1L,TimeUnit.SECONDS)
                .map {
                    "testInterval"
                }
                .take(100)
                .subscribe {
                    print(it)
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
                .buffer(10) //缓存起来
                .subscribe {
                    print(GsonKit.objectToJson(it))
                }
    }

    @Test
    fun  testStart(){
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
                    print("t1=$t1\n")
                    return@scan t1+t2
                }
                .subscribe {
                    print(GsonKit.objectToJson(it))
                }
    }


}