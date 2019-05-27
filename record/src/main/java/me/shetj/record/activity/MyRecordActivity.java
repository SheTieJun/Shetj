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

	/**
	 * 个人中心进入录音界面，上传后不会关闭该界面
	 * @param context 上下文
	 * @param type “record” 为录音界面，“recordList” 为我的录音界面
	 */
	public static void start(Fragment context, String type, int liveroomId) {
		Intent intent = new Intent(context.getActivity(), MyRecordActivity.class);
		intent.putExtra(TYPE,type);
		intent.putExtra("liveroom_id", liveroomId);
		context.startActivityForResult(intent,REQUEST_FRAGMENT_RECORD_CODE);
	}


	/**
	 * 其他进入录音界面，上传后会关闭该界面
	 * @param context
	 */
	public static void startForResult(Activity context){
		Intent intent = new Intent(context, MyRecordActivity.class);
		intent.putExtra(TYPE,"recordList");
		intent.putExtra(NEED_CLOSE,true);
		context.startActivityForResult(intent,REQUEST_RECORD_CODE);
	}

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
	protected void initData() {
		mAudioManager = (AudioManager)getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mAudioManager != null && isRecord) {
			mAudioManager.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC,
							AudioManager.AUDIOFOCUS_GAIN);
		}
	}

	AudioManager.OnAudioFocusChangeListener afChangeListener = focusChange -> {
		switch (focusChange){
			case AudioManager.AUDIOFOCUS_LOSS:
				//长时间丢失焦点,当其他应用申请的焦点为AUDIOFOCUS_GAIN时，
				//会触发此回调事件，例如播放QQ音乐，网易云音乐等
				//通常需要暂停音乐播放，若没有暂停播放就会出现和其他音乐同时输出声音
				pauseAction();
				//释放焦点，该方法可根据需要来决定是否调用
				//若焦点释放掉之后，将不会再自动获得
				break;
			case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
				//短暂性丢失焦点，当其他应用申请AUDIOFOCUS_GAIN_TRANSIENT或AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE时，
				//会触发此回调事件，例如播放短视频，拨打电话等。
				//通常需要暂停音乐播放
				pauseAction();
				break;
			case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
				//短暂性丢失焦点并作降音处理
				pauseAction();
				break;
			case AudioManager.AUDIOFOCUS_GAIN:
				//当其他应用申请焦点之后又释放焦点会触发此回调
				//可重新播放音乐
				break;
		}
	};

	/**
	 * 暂停所有播放、录音动作
	 */
	private void pauseAction() {
		if (recordAction != null) {
			recordAction.onPause();
		}
		if (myRecordAction != null) {
			myRecordAction.onPause();
		}
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
		if (mAudioManager != null) {
			mAudioManager.abandonAudioFocus(afChangeListener);
			mAudioManager = null;
			afChangeListener =null;
		}

		RecordingNotification.INSTANCE.cancel(this);
		super.onDestroy();
	}

}
