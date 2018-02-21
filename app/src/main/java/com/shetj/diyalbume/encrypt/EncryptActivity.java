package com.shetj.diyalbume.encrypt;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.shetj.diyalbume.R;

import me.shetj.base.base.BaseActivity;

public class EncryptActivity extends BaseActivity implements View.OnClickListener {

	/**
	 * shetiejun
	 */
	private EditText mEditQuery;
	/**  */
	private TextView mText;
	/**
	 * 加密
	 */
	private Button mBtnJiami;
	/**
	 * 解密
	 */
	private Button mBtnJiemi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_encrypt);
		initView();
	}

	@Override
	protected void initView() {

		mEditQuery = (EditText) findViewById(R.id.edit_query);
		mText = (TextView) findViewById(R.id.text);
		mBtnJiami = (Button) findViewById(R.id.btn_jiami);
		mBtnJiami.setOnClickListener(this);
		mBtnJiemi = (Button) findViewById(R.id.btn_jiemi);
		mBtnJiemi.setOnClickListener(this);
	}

	@Override
	protected void initData() {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			default:
				break;
			case R.id.btn_jiami:

				break;
			case R.id.btn_jiemi:
				break;
		}
	}
}
