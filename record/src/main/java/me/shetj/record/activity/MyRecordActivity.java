package me.shetj.record.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.widget.FrameLayout;

import com.tbruyelle.rxpermissions2.RxPermissions;

import me.shetj.base.base.BaseActivity;
import me.shetj.base.tools.app.ArmsUtils;
import me.shetj.record.R;
import me.shetj.record.RecordingNotification;
import me.shetj.record.bean.Record;
import me.shetj.record.utils.ActionCallback;


/**
 * 讲师工具
 * 我的录音界面
 */
public class MyRecordActivity extends BaseActivity implements ActionCallback {

	public final static int REQUEST_RECORD_CODE = 0x009;
	public final static int REQUEST_FRAGMENT_RECORD_CODE = 0x0010;
	public final static String TYPE = "type";
	public final static String NEED_CLOSE = "needClose";//完成上传后关闭界面 默认不关闭
	public final static String POST_URL ="postUrl";//声音

	private FrameLayout mFrameLayout;
	private MyRecordPage myRecordAction;
	private RecordPage recordAction;
	private boolean isRecord = false;
	private Transition recordTransition;
	private Transition myRecordTransition;
	private AudioManager mAudioManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_record);
		ArmsUtils.statuInScreen(this,true);
		canRecord();
		//展示界面
		initView();
		initData();
	}


	@Override
	protected void initView() {


		mFrameLayout = findViewById(R.id.frameLayout);
		myRecordAction =new MyRecordPage(this,mFrameLayout,this);
		recordAction = new RecordPage(this,mFrameLayout,this);
		//设置录音界面的动画
		recordTransition = TransitionInflater.from(this).inflateTransition(R.transition.record_page_slide);
		myRecordTransition = TransitionInflater.from(this).inflateTransition(R.transition.my_record_page_slide);
			isRecord = false;
			TransitionManager.go(myRecordAction.getScene(),myRecordTransition);
	}

	@Override
	protected void initData() {

	}


	@Override
	public void onEvent(int message) {
		switch (message){
			case 0:
				setTitle(R.string.record);
				TransitionManager.go(recordAction.getScene(),recordTransition);
				recordAction.setRecord(null);
				isRecord = true;
				break;
			case 1:
				setTitle( R.string.my_record);
				recordAction.setRecord(null);
				TransitionManager.go(myRecordAction.getScene(),myRecordTransition);
				isRecord = false;
				break;
			case 2://存在
				setTitle(R.string.record);
				Record curRecord = myRecordAction.getCurRecord();
				recordAction.setRecord(curRecord);
				TransitionManager.go(recordAction.getScene(),recordTransition);
				isRecord = true;
				break;
			case 3://获取权限
				canRecord( );
				break;
		}
	}

	private void canRecord() {
		new RxPermissions(this)
						.request(Manifest.permission.READ_EXTERNAL_STORAGE,
										Manifest.permission.WRITE_EXTERNAL_STORAGE,
										Manifest.permission.RECORD_AUDIO
						).subscribe(aBoolean -> {

						});
	}

	@Override
	public void onBackPressed() {
		if (isRecord){
			setTitle(R.string.my_record);
			recordAction.onStop();
		}else {
			super.onBackPressed();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		if (recordAction !=null){
			recordAction.onDestroy();
		}
		if (myRecordAction !=null){
			myRecordAction.onDestroy();
		}
		RecordingNotification.INSTANCE.cancel(this);
		super.onDestroy();
	}

}
