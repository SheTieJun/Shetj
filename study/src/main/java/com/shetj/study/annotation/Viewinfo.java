package com.shetj.study.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <b>@packageName：</b> com.shetj.study.annotation<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2018/1/9<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Viewinfo {
	int value();
}
