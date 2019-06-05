package com.shetj.diyalbume.encrypt;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.shetj.diyalbume.R;

import me.shetj.base.base.BaseActivity;
import me.shetj.base.tools.json.DES;

@Route(path = "/shetj/EncryptActivity")
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
	/**
	 * shetiejun
	 */
	private EditText mEditJiemi;
	/**  */
	private TextView mText2;

	private Handler handler =new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_encrypt);
		initView();
	}

	@Override
	protected void initView() {

		mEditQuery = findViewById(R.id.edit_query);
		mText = findViewById(R.id.text);
		mBtnJiami = findViewById(R.id.btn_jiami);
		mBtnJiami.setOnClickListener(this);
		mBtnJiemi = findViewById(R.id.btn_jiemi);
		mBtnJiemi.setOnClickListener(this);
		mEditJiemi = findViewById(R.id.edit_jiemi);
		mText2 = findViewById(R.id.text2);
	}

	@Override
	protected void initData() {

	}

	/** 加密KEY */
	private static final byte[] KEY = "6;9Ku7;:84VG*B68".getBytes();

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			default:
				break;
			case R.id.btn_jiami:
				String jiamiwen = mEditQuery.getText().toString();
				String encrypt = new DES().encrypt(jiamiwen);
				mText.setText(" DES() = "+encrypt+"\n" );
				mEditJiemi.setText(encrypt);
				break;
			case R.id.btn_jiemi:
				String jiamiwen1 = mEditJiemi.getText().toString();
				String decrypt = new DES().decrypt(jiamiwen1);
				mText2.setText(" DES() = "+decrypt+"\n");
				break;
		}
	}
}
