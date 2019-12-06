package com.shetj.components.user;

import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

import android.widget.TextView;
import android.widget.Toast;

import com.shetj.components.R;
import com.shetj.components.component.DaggerUserComponent;
import com.shetj.components.db.User;
import com.shetj.components.module.UserModule;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import me.shetj.base.tools.json.GsonKit;

public class UserActivity extends AppCompatActivity   {


	@Inject
	UserModel mUserModel;

	@Inject
	UserRepository userRepository;

	private FloatingActionButton mFab;

	PublishSubject<List<User>> publishSubject;

	private int i;

	private TextView mTvMsg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		DaggerUserComponent
						.builder()
						.userModule(new UserModule(this))
						.build()
						.inject(this);
		initView();
		initData();
	}


	private void initData() {
		publishSubject = PublishSubject.create();

		publishSubject
						.observeOn(Schedulers.io())
						.subscribeOn(AndroidSchedulers.mainThread())
						.subscribe(new Consumer<List<User>>() {
							@Override
							public void accept(List<User> users) {
								Toast.makeText(UserActivity.this, "xxxxx" + users.size() + "user", Toast.LENGTH_LONG).show();
							}
						});

		mUserModel.getUser().observe(this, user -> {
							Toast.makeText(UserActivity.this, user.name, Toast.LENGTH_LONG).show();
							mTvMsg.setText(GsonKit.objectToJson(user));
						}
		);

		//转换LiveData
		LiveData<String> map = Transformations.map(mUserModel.getUser(), new Function<User, String>() {
			@Override
			public String apply(User input) {
				return "\"xxxxx\" + users.size() + \"user\"";
			}
		});

		map.observe(this, new Observer<String>() {
			@Override
			public void onChanged(String s) {

			}
		});


		//添加其他的livedata变化监听
		//改变数值
		MediatorLiveData<String> liveData = new MediatorLiveData<String>();
		liveData.addSource(map, new Observer<String>() {
			@Override
			public void onChanged(String s) {
				liveData.setValue(s);
			}
		});

		liveData.observe(this, new Observer<String>() {
			@Override
			public void onChanged(String s) {
				//展示数据
			}
		});

		//转换新的liveData
		LiveData<String> stringLiveData = Transformations.switchMap(mUserModel.getUser(), new Function<User, LiveData<String>>() {
			@Override
			public LiveData<String> apply(User input) {
				if (input.id%2 == 0){
					return map;
				}
				return null;
			}
		});


		mFab.setOnClickListener(view -> {
			mUserModel.getUser().setValue(new User(i++, "shetj2" + i, "http://baidi.com"));
		});
		publishSubject.onNext(userRepository.getUsers());
	}

	private void getObserveLive() {
		new MutableLiveData<String>().observe(this, new Observer<String>() {
			@Override
			public void onChanged(String s) {

			}
		});
	}

	private void initView() {
	 	getLifecycle().addObserver(new UserObserver());

		mFab = findViewById(R.id.fab);
		mTvMsg = findViewById(R.id.tv_msg);
	}

}
