package com.shetj.diyalbume;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;


/**
 * 可以用来转换
 * 也可以用来
 */
public class SQTransFormer implements ObservableTransformer<Integer, Integer> {


        @Override
        public ObservableSource<Integer> apply(Observable<Integer> upstream) {
            return upstream.map(new Function<Integer, Integer>() {
                @Override
                public Integer apply(Integer integer) throws Exception {
                    return integer * integer;
                }
            }).flatMap(new Function<Integer, ObservableSource<Integer>>() {
                @Override
                public ObservableSource<Integer> apply(Integer integer) throws Exception {
                    return Maybe.just(1).toObservable();
                }
            }).filter(new Predicate<Integer>() {
                @Override
                public boolean test(Integer integer) throws Exception {
                    return integer> 1;
                }
            });
        }
    }