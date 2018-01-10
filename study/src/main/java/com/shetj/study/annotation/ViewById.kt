package com.shetj.study.annotation

import java.lang.annotation.ElementType
import java.lang.annotation.RetentionPolicy

/**
 *
 * <b>@packageName：</b> com.shetj.study.annotation<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2018/1/9<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class ViewById(// 代表可以传值int类型  使用的时候：ViewById(R.id.xxx)
        val value: Int)

