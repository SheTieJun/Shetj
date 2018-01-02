package com.shetj.components.component;

import com.shetj.components.module.UserModule;
import com.shetj.components.user.UserActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * <b>@packageName：</b> com.shetj.components.component<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2018/1/2<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */


@Singleton
@Component(modules = UserModule.class)
public interface UserComponent {
	void inject(UserActivity userActivity);
}
