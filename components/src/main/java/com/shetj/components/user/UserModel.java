package com.shetj.components.user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.shetj.components.db.User;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;

/**
 * <b>@packageName：</b> com.shetj.components.user<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2018/1/2<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */

public class UserModel extends ViewModel {

	private UserRepository userRepository;

	@Inject
	public UserModel(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	private MutableLiveData<User> user;

	public LiveData<List<User>> getUsers() {

		return userRepository.getUser();
	}


	@NonNull
	public MutableLiveData<User> getUser(){
		if (user == null){
			user = new MutableLiveData<>();
			loadUser();
		}
		return user;
	}

	private void loadUser() {
		user.setValue(new User(1,"shetj","http://baidu.com"));
	}
}
