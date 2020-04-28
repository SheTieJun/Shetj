package com.shetj.diyalbume;

import io.reactivex.ObservableOperator;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import kotlin.collections.ArraysKt;
import kotlin.reflect.jvm.internal.impl.utils.CollectionsKt;

/**
 * 自定义操作符
 */
public class SQOperator implements ObservableOperator<Integer, Integer> {

        @Override
        public Observer<? super Integer> apply(Observer<? super Integer> observer) throws Exception {
            return new Observer<Integer>() {

                private Disposable mDisposable;

                @Override
                public void onSubscribe(Disposable d) {
                    mDisposable = d;
                    observer.onSubscribe(d);
                }

                @Override
                public void onNext(Integer integer) {
                    if (!mDisposable.isDisposed()) observer.onNext(integer * integer);
                }

                @Override
                public void onError(Throwable e) {
                    if (!mDisposable.isDisposed()) observer.onError(e);
                }

                @Override
                public void onComplete() {
                    if (!mDisposable.isDisposed()) observer.onComplete();
                }
            };
        }
    }