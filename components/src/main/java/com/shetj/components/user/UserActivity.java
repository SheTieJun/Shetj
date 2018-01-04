package com.shetj.components.user;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.shetj.components.R;
import com.shetj.components.component.DaggerUserComponent;
import com.shetj.components.db.User;
import com.shetj.components.module.UserModule;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class UserActivity extends AppCompatActivity implements LifecycleOwner {

	private LifecycleRegistry mLifecycleRegistry;

	@Inject
	UserModel mUserModel;

	@Inject
	UserRepository userRepository;

	private FloatingActionButton mFab;

	 PublishSubject<List<User>>  publishSubject;

	private int i;

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

		publishSubject.observeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread())
						.subscribe(new Consumer<List<User>>() {
			@Override
			public void accept(List<User> users) throws Exception {
				Toast.makeText(UserActivity.this,"xxxxx"+users.size()+"user",Toast.LENGTH_LONG).show();
			}
		});

		publishSubject.onNext(userRepository.getUsers());

		mUserModel.getUser().observe(this, new Observer<User>() {
			@Override
			public void onChanged(@Nullable User user) {
				Toast.makeText(UserActivity.this,user.name,Toast.LENGTH_LONG).show();
			}
		});

		mUserModel.getUsers().observe(this,users -> {
			assert users != null;
			Toast.makeText(UserActivity.this,users.size()+"user",Toast.LENGTH_LONG).show();
		});

		mFab.setOnClickListener(view -> {
			mUserModel.getUser().setValue(new User(i++,"shetj2"+i,"http://baidi.com"));
		});
	}

	private void initView() {

		mLifecycleRegistry = new LifecycleRegistry(this);
		mLifecycleRegistry.markState(Lifecycle.State.CREATED);
		this.getLifecycle().addObserver(new UserObserver());

		mFab = findViewById(R.id.fab);
	}

	@NonNull
	@Override
	public Lifecycle getLifecycle() {
		return mLifecycleRegistry;

	}

	@Override
	protected void onStop() {
		super.onStop();
		mLifecycleRegistry.markState(Lifecycle.State.STARTED);
	}
}
