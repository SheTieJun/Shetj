package me.shetj.record.activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.reactivestreams.Publisher;
import org.simple.eventbus.EventBus;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.shetj.base.base.BaseActivity;
import me.shetj.base.tools.app.ArmsUtils;
import me.shetj.base.tools.app.HideUtil;
import me.shetj.base.tools.json.EmptyUtils;
import me.shetj.base.tools.json.GsonKit;
import me.shetj.base.tools.time.DateUtils1;
import me.shetj.record.R;
import me.shetj.record.RecordService;
import me.shetj.record.bean.Record;
import me.shetj.record.bean.RecordDbUtils;
import me.shetj.record.utils.CreateRecordUtils;
import me.shetj.record.utils.MpCallback;
import me.shetj.record.utils.RecordCallBack;
import me.shetj.record.utils.Util;

import static me.shetj.base.tools.time.DateUtils1.FORMAT_FULL_SN;

public class RecordActivity extends BaseActivity implements View.OnClickListener {

	private EditText mEditInfo;
	private ProgressBar mProgressBarRecord;
	private TextView mTvRecordTime;
	private TextView mTvReRecord;
	private TextView mTvSaveRecord;
	private ImageView mIvRecordState;
//	private CreateRecordUtils recordUtils;
	private TextView mTvStateMsg;
	private String content;
	private Record oldRecord;
	private RelativeLayout mRlRecordView;
	private boolean isHasChange = false;
	private RecordService myService;
	private boolean bindService;
	private RecordCallBack recordCallBack;
	private RecordService.Work work;
	public static void start(Context context) {
		context.startActivity(new Intent(context, RecordActivity.class));
	}

	public static void start(Context context, Record record) {
		Intent intent = new Intent(context, RecordActivity.class);
		intent.putExtra("oldRecord", GsonKit.objectToJson(record));
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record);
		HideUtil.init(this);
		initView();
		initData();
		new RxPermissions(this)
						.request(Manifest.permission.READ_EXTERNAL_STORAGE,
										Manifest.permission.WRITE_EXTERNAL_STORAGE,
										Manifest.permission.RECORD_AUDIO
						).subscribe(new Consumer<Boolean>() {
			@Override
			public void accept(Boolean aBoolean) {

			}
		});
	}

	@Override
	protected void initData() {
//		recordUtils = new CreateRecordUtils();
		bindService();
		String recordInfo = getIntent().getStringExtra("oldRecord");
		if (EmptyUtils.isNotEmpty(recordInfo)) {
			oldRecord = GsonKit.jsonToBean(recordInfo, Record.class);
			mEditInfo.setText(oldRecord.getAudioContent());
		}



		mIvRecordState.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (work!=null) {
					work.startWork(oldRecord);
				}
			}
		});

		 recordCallBack = new RecordCallBack() {
			@Override
			public void start() {
				isHasChange = true;
				mIvRecordState.setImageResource(R.mipmap.icon_record_pause_2);
				mTvSaveRecord.setVisibility(View.INVISIBLE);
				mTvReRecord.setVisibility(View.INVISIBLE);
				mTvStateMsg.setText("录音中");
			}

			@Override
			public void pause() {
				mIvRecordState.setImageResource(R.mipmap.icon_record_);
				mTvSaveRecord.setVisibility(View.VISIBLE);
				mTvReRecord.setVisibility(View.VISIBLE);
				mTvStateMsg.setText("已暂停，点击继续");
			}

			@Override
			public void onSuccess(String file, int time) {
				mTvSaveRecord.setVisibility(View.INVISIBLE);
				mTvReRecord.setVisibility(View.INVISIBLE);
				mTvStateMsg.setText("点击录音");
				if (EmptyUtils.isEmpty(oldRecord)) {
					Record record = new Record("1", file, DateUtils1.date2Str(new Date(), FORMAT_FULL_SN), time, content);
					RecordDbUtils.getInstance().save(record);
					EventBus.getDefault().post(record, "update");
					back();
				} else {
					saveRecord(file);
				}

			}

			@Override
			public void onProgress(int time) {
				mProgressBarRecord.setProgress(time);
				mTvRecordTime.setText(Util.formatSeconds2(time));
			}

			@Override
			public void onMaxProgress(int time) {
				mProgressBarRecord.setMax(time);
			}

			@Override
			public void onError(Exception e) {

			}
		};
	}


	//绑定service
	private void bindService() {
		Intent intent = new Intent(this, RecordService.class);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			startForegroundService(intent);
		}else {
			startService(intent);
		}
		bindService = bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
	}

	private ServiceConnection serviceConnection = new ServiceConnection() {




		@Override
		public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
			//获取绑定binder 强制转化为MyService.Work
			work = (RecordService.Work) iBinder;
			myService = work.getMyService();
			myService.registerCallBack(recordCallBack);
		}

		@Override
		public void onServiceDisconnected(ComponentName componentName) {
			if (myService != null) {
				myService.unRegisterCallBack(recordCallBack);
			}
		}
	};



	private void saveRecord(String file) {
		Flowable.just(file)
						.subscribeOn(Schedulers.io())
						.map(s -> getMergeFile(file))
						.flatMap(s -> getTimeFlowable(s))
						.observeOn(Schedulers.io())
						.subscribe(o -> {
							oldRecord.setAudioLength(o);
							if (EmptyUtils.isNotEmpty(oldRecord) && isHasChange) {
								RecordDbUtils.getInstance().update(oldRecord);
								EventBus.getDefault().post(oldRecord, "update");
							}
							back();
						});
	}

	private String getMergeFile(String file) {
		String newFileUrl = Util.heBingMp3(oldRecord.getAudio_url(), file);
		oldRecord.setAudio_url(newFileUrl);
		return  newFileUrl;
	}

	private Flowable<Integer> getTimeFlowable(String s) {
		return Flowable.create(emitter -> Util.showMPTime2(s, new MpCallback() {
			@Override
			public void onSuccess(int time) {
				emitter.onNext(time);
			}

			@Override
			public void onError(Exception e) {
					emitter.onError(e);
			}
		}), BackpressureStrategy.BUFFER);
	}

	@Override
	public void back() {
		super.back();
	}

	@Override
	protected void initView() {
		mEditInfo = findViewById(R.id.edit_info);
		mProgressBarRecord = findViewById(R.id.progressBar_record);
		mTvRecordTime = findViewById(R.id.tv_record_time);
		mTvReRecord = findViewById(R.id.tv_reRecord);
		mTvReRecord.setOnClickListener(this);
		mTvSaveRecord = findViewById(R.id.tv_save_record);
		mTvSaveRecord.setOnClickListener(this);
		mIvRecordState = findViewById(R.id.iv_record_state);
		mIvRecordState.setOnClickListener(this);
		mTvStateMsg = findViewById(R.id.tv_state_msg);
		mRlRecordView = findViewById(R.id.rl_record_view);
		setCanNotEditAndClick();

		RxTextView.afterTextChangeEvents(mEditInfo)
						.throttleFirst(500, TimeUnit.MILLISECONDS)
						.subscribe(textViewAfterTextChangeEvent -> {
							content = textViewAfterTextChangeEvent.editable().toString();
							if (EmptyUtils.isNotEmpty(oldRecord)) {
								oldRecord.setAudioContent(content);
							}
						});



	}

	public void setCanNotEditAndClick() {
		mEditInfo.setFocusable(false);
		mEditInfo.setFocusableInTouchMode(false);
		mEditInfo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				setCanEdit();
			}
		});
	}

	public void setCanEdit() {
		mEditInfo.setFocusable(true);
		mEditInfo.setFocusableInTouchMode(true);
		mEditInfo.setOnClickListener(null);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			default:
				break;
			case R.id.tv_reRecord:
				if (work!=null) {
					work.reRecord(oldRecord);
				}
				break;
			case R.id.tv_save_record:
				if (work!=null) {
					work.recordComplete( );
				}
				break;
			case R.id.iv_record_state:
				if (work!=null) {
					work.startWork(oldRecord);
				}
				break;
		}
	}


	@Override
	protected void onPause() {
		super.onPause();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unBindService();
	}

	public void unBindService() {
		if (bindService && serviceConnection != null) {
			unbindService(serviceConnection);
		}
	}
}
