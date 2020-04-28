package com.shetj.diyalbume.utils

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.io.File
import java.util.concurrent.TimeUnit

class FIleReader {

    fun readFile(filePah :String): CompositeDisposable {
        val mCompositeDisposable = CompositeDisposable()
        var HasMore = false
        val info = StringBuilder()
//        val observable = Observable.create<String> { ob ->
//            File(filePah).inputStream().buffered().apply {
//                val tempByte = ByteArray(1024)
//                val read = read(tempByte)
//                if (read != -1){
//                    ob.onNext(String(tempByte))
//                }
//                close()
//            }
//            ob.onComplete()

//            File(filePah).inputStream().bufferedReader().apply {
//                forEachLine {
//                    ob.onNext(it)
//                }
//                close()
//            }
//            ob.onComplete()
//        }

//        mCompositeDisposable.add(Observable.zip(Observable.interval(33, TimeUnit.MILLISECONDS),
//                observable, BiFunction<Long,String,String> { _, s -> s
//        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe ({
//                    Timber.i(it)
//                },{e-> Timber.i("e:${e.message}")}))

            mCompositeDisposable.add(Flowable.interval(5,100,TimeUnit.MILLISECONDS)
                    .takeWhile {
                        HasMore
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        Timber.i(info.toString())
                        info.clear()
                    })
            mCompositeDisposable.add(Schedulers.io().scheduleDirect {
                HasMore = true
                File(filePah).inputStream().bufferedReader().forEachLine {
                    Thread.sleep(4)
                    info.append(it)
                }
                HasMore = false
            })

        return mCompositeDisposable
    }
}