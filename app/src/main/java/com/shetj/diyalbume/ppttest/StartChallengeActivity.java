package com.shetj.diyalbume.ppttest;

import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;

import com.shetj.diyalbume.R;

import me.shetj.base.base.BaseActivity;

public class StartChallengeActivity extends BaseActivity {

	private RecyclerView mIRecyclerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_challenge);
		initView();
		initData();
	}

	@Override
	protected void initView() {

		mIRecyclerView = (RecyclerView) findViewById(R.id.iRecyclerView);


	}

	@Override
	protected void initData() {

	}
}
