package com.shetj.components.module;

import android.arch.persistence.room.Room;

import com.shetj.components.APP;
import com.shetj.components.db.UserDataBase;
import com.shetj.components.scope.ActivityScope;
import com.shetj.components.user.UserActivity;
import com.shetj.components.user.UserModel;
import com.shetj.components.user.UserRepository;

import org.xutils.x;

import javax.inject.Scope;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * <b>@packageName：</b> com.shetj.components.module<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2018/1/2<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */

@ActivityScope
@Module
public class UserModule {
	private UserActivity userActivity;

	public UserModule(UserActivity userActivity) {
		this.userActivity = userActivity;
	}

	@Provides
	public UserActivity getUserActivity(){
		return userActivity;
	}


	@Provides
	public UserModel  getUserModel(UserRepository userRepository ){
		return new UserModel(userRepository);
	}

	@Provides
	public UserRepository getUserRepository(){
		return new UserRepository(getDb().userDao());
	}

	public UserDataBase getDb() {
		return Room.databaseBuilder(x.app().getApplicationContext(),
						UserDataBase.class, "database-user").build();
	}
}
