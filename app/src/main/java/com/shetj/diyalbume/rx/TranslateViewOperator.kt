package com.shetj.diyalbume.rx

import android.view.View
import android.view.animation.Interpolator
import androidx.core.view.ViewCompat
import io.reactivex.ObservableOperator
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.util.concurrent.atomic.AtomicBoolean


class TranslateViewOperator (private val translationX:Float,
                             private val translationY:Float,
                             private val duration: Long,
                             private val interpolator:Interpolator)
    : ObservableOperator<View,View>{


   private val isOnCompleteCalled = AtomicBoolean(false)

    override fun apply(observer: Observer<in View>): Observer<in View> {
        return object : Observer<View> {
            override fun onNext(view: View) {
                if (mDisposable!!.isDisposed) return
                ViewCompat.animate(view)
                        .translationY(translationY)
                        .translationX(translationX)
                        .setDuration(duration)
                        .setInterpolator(interpolator)
                        .withEndAction {
                            if (!mDisposable!!.isDisposed){
                                observer.onNext(view)
                            }
                            if (isOnCompleteCalled.get()){
                                observer.onComplete()
                            }
                        }

            }

            private var mDisposable: Disposable? = null

            override fun onSubscribe(d: Disposable) {
                mDisposable = d
                observer.onSubscribe(d)
            }

            override fun onError(e: Throwable) {
                if (!mDisposable!!.isDisposed) observer.onError(e)
            }

            override fun onComplete() {
                isOnCompleteCalled.set(true)
                if (!mDisposable!!.isDisposed) observer.onComplete()
            }
        }
    }
}