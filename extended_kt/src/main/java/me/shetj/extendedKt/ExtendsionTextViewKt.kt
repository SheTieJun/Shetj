package me.shetj.extendedKt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.util.concurrent.locks.Lock
import kotlin.concurrent.thread
import kotlin.concurrent.timer

/**
 *
 * <b>@packageName：</b> me.shetj.extended_kt<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2018/11/5 0005<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */

inline fun  ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}


inline fun <T> method(lock: Lock, body: () -> T): T {
    lock.lock()
    try {
        return body()
    } finally {
        lock.unlock()
    }
}
