package com.shetj.components.user;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.shetj.components.db.User;
import com.shetj.components.db.UserDao;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

/**
 * <b>@packageName：</b> com.shetj.components.user<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2018/1/2<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */

public class UserRepository {

	private UserDao userDao;

	@Singleton
	public UserRepository(UserDao userDao) {
		this.userDao = userDao;
	}

	public LiveData<List<User>>  getUser(){
		MutableLiveData<List<User>>  users = new MutableLiveData<>();
		List<User> all = new ArrayList<>();
		all.add(new User());
		users.setValue(all);
		return users;
	}
}
