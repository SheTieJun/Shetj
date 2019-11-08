package com.shetj.diyalbume.rx

import android.annotation.SuppressLint
import android.view.View
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import androidx.core.view.ViewCompat
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

object RxUtils {

    fun <T> Observable<T>.io_main() : ObservableTransformer<T,T> {
        return ObservableTransformer {
            it.subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onTerminateDetach()
        }
    }

    fun <T> Flowable<T>.io_main() :FlowableTransformer<T,T>{
        return FlowableTransformer {
            it.subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onTerminateDetach()
        }
    }


    fun Observable<View>.Transi( translationX:Float,
                                 translationY:Float,
                                 duration: Long,
                                 interpolator: Interpolator):Observable<View>{
        return  lift(TranslateViewOperator(translationX, translationY, duration, interpolator))
    }

    @SuppressLint("CheckResult")
    fun  View.start(){

        val timer  = Observable.interval(0,500,TimeUnit.MILLISECONDS)

        val viewObservable = Observable.just(this)
                .doOnNext {
                    ViewCompat.animate(it).cancel()
                }

        Observable.zip(viewObservable,timer, BiFunction< View, Long,View> {t1,_ -> t1 })
                .Transi(100f,100f,1000,LinearInterpolator())
                .subscribe ()
    }
}