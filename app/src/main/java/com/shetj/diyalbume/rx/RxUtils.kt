package com.shetj.diyalbume.rx

import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object RxUtils {

    fun <T> io_main() : ObservableTransformer<T,T> {
        return ObservableTransformer {
            it.subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onTerminateDetach()
        }
    }

}