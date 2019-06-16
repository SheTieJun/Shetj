package me.shetj.record.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import org.simple.eventbus.EventBus;

import java.io.File;
import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.autosize.utils.LogUtils;
import me.shetj.base.tools.app.ArmsUtils;
import me.shetj.base.tools.time.TimeUtil;
import me.shetj.record.R;
import me.shetj.record.RecordService;
import me.shetj.record.bean.Record;
import me.shetj.record.bean.RecordDbUtils;
import me.shetj.record.utils.ActionCallback;
import me.shetj.record.utils.CreateRecordUtils;
import me.shetj.record.utils.MainThreadEvent;
import me.shetj.record.utils.RecordCallBack;
import me.shetj.record.utils.Util;
import timber.log.Timber;

/**
 * 录制声音界面
 */
public class RecordPage implements View.OnClickListener {

	private final RelativeLayout root;
	private EditText mEditInfo;
	private ProgressBar mProgressBarRecord;
	private TextView mTvRecordTime;
	private TextView mTvReRecord;
	private TextView mTvSaveRecord;
	private ImageView mIvRecordState;
	private TextView mTvStateMsg;
	private Record oldRecord;
	private boolean isHasChange = false;
	private Scene scene;
	private AppCompatActivity context;
	private ActionCallback callback;
	private TextView mtvAllTime;
	private ScrollView mBunceSv;
	private boolean bindService;
	private RecordCallBack recordCallBack;
	private RecordService.Work work;
	private RecordService myService;
	private Intent intent;

	public RecordPage(AppCompatActivity context, ViewGroup mRoot, ActionCallback callback) {
		this.context = context;
		this.callback = callback;
		root = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.page_record, null);
		scene = new Scene(mRoot, root);
		initView(root);
		initData();
		bindService();
	}

	public Scene getScene() {
		return scene;
	}

	private void initView(View view) {
		mBunceSv = view.findViewById(R.id.scrollView);
		mEditInfo = buildEditTextView();
		mProgressBarRecord = view.findViewById(R.id.progressBar_record);
		mTvRecordTime = view.findViewById(R.id.tv_record_time);
		mTvReRecord = view.findViewById(R.id.tv_reRecord);
		mTvReRecord.setOnClickListener(this);
		mTvSaveRecord = view.findViewById(R.id.tv_save_record);
		mTvSaveRecord.setOnClickListener(this);
		mIvRecordState = view.findViewById(R.id.iv_record_state);
		mIvRecordState.setOnClickListener(this);
		mTvStateMsg = view.findViewById(R.id.tv_state_msg);
		mtvAllTime = view.findViewById(R.id.tv_all_time);
	}



	//绑定service
	private void bindService() {
		intent = new Intent(context, RecordService.class);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			context.startForegroundService(intent);
		}else {
			context.startService(intent);
		}
		bindService = context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
	}

	private ServiceConnection serviceConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
			//获取绑定binder 强制转化为MyService.Work
			work = (RecordService.Work) iBinder;
			work.setCallBack(recordCallBack);
		}

		@Override
		public void onServiceDisconnected(ComponentName componentName) {
		}
	};

	public void unBindService() {
		if (bindService && serviceConnection != null) {
			context.unbindService(serviceConnection);
		}
	}

	/**
	 * 2019年4月25日
	 * 动态创建 EditText 的原因是，再次进入录音界面时，调用了 editText.setText() 之后就无法选中和编辑了
	 * 所以每次都新建一次。
	 * BUG id：ID1002237
	 */
	private EditText buildEditTextView(){
		mBunceSv.removeAllViews();
		EditText editText = new EditText(this.context);
		ScrollView.LayoutParams params = new ScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		editText.setLayoutParams(params);
		editText.setMinHeight(ArmsUtils.dip2px(500));
		editText.setTextSize(16);
		editText.setGravity(Gravity.TOP);
		editText.setBackgroundColor(Color.WHITE);
		editText.setPadding(ArmsUtils.dip2px(30), ArmsUtils.dip2px(27), ArmsUtils.dip2px(30), ArmsUtils.dip2px(50));
		editText.setInputType(EditorInfo.TYPE_CLASS_TEXT| EditorInfo.TYPE_TEXT_FLAG_MULTI_LINE);
		editText.setHorizontallyScrolling(false);
		editText.setMaxLines(Integer.MAX_VALUE);
		try {
			// https://github.com/android/platform_frameworks_base/blob/kitkat-release/core/java/android/widget/TextView.java#L562-564
			Field cursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
			cursorDrawableRes.setAccessible(true);
		} catch (Exception ignored) {
		}
		mBunceSv.addView(editText);
		return editText;
	}

	protected void initData() {
		recordCallBack =  new RecordCallBack() {
			@Override
			public void start() {
				TransitionManager.beginDelayedTransition(root);
				isHasChange = true;
				mIvRecordState.setImageResource(R.mipmap.icon_record_pause_2);
				showSaveAndRe(View.INVISIBLE);
				mTvStateMsg.setText("录音中");

			}

			/**
			 * 如果要写呼吸灯，就在这里处理
			 * @param time
			 * @param volume
			 */
			@Override
			public void onRecording(int time, int volume) {
				Log.i("record","time = "+time +"\n" + "volume" + volume );
			}

			@Override
			public void pause() {
				TransitionManager.beginDelayedTransition(root);
				mIvRecordState.setImageResource(R.mipmap.icon_start_record);
				showSaveAndRe(View.VISIBLE);
				mTvStateMsg.setText("已暂停，点击继续");
			}

			@Override
			public void onSuccess(String file, int time) {
				if (new File(file).exists()) {
					TransitionManager.beginDelayedTransition(root);
					mIvRecordState.setImageResource(R.mipmap.icon_start_record);
					showSaveAndRe(View.INVISIBLE);
					mTvStateMsg.setText("点击录音");
					if (oldRecord == null) {
						saveRecord(file, time);
						callback.onEvent(1);
					} else {
						saveOldRecord(file, true);
					}
				}
			}

			@Override
			public void onProgress(int time) {
				mProgressBarRecord.setProgress(time);
				mTvRecordTime.setText(Util.formatSeconds3(time));
			}

			@Override
			public void onMaxProgress(int time) {
				mProgressBarRecord.setMax(time);
				mtvAllTime.setText(Util.formatSeconds3(time));
			}

			@Override
			public void onError(Exception e) {
				work.stop();
				mIvRecordState.setImageResource(R.mipmap.icon_start_record);
				setRecord(oldRecord);
			}

			@Override
			public void autoComplete(String file, int time) {
				if (new File(file).exists()) {
					TransitionManager.beginDelayedTransition(root);
					mIvRecordState.setImageResource(R.mipmap.icon_start_record);
					showSaveAndRe(View.INVISIBLE);
					mTvStateMsg.setText("录制完成");
					if (oldRecord == null) {
						saveRecord(file, time);
						showRecordNewDialog();
					} else {
						saveOldRecord(file, false);
					}
				}
			}

			@Override
			public void needPermission() {
				callback.onEvent(3);
			}
		};
	}

	/**
	 * 保持录音
	 *
	 * @param file     文件地址
	 * @param isFinish true 保持后切换界面，false 展示是否录制下一个界面
	 */
	private void saveOldRecord(String file, boolean isFinish) {
		Flowable.just(file)
						.subscribeOn(Schedulers.io())
						.map(s -> getMergeFile(file))
						.flatMap(s -> getMediaTime(s))
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(o -> {
							if (oldRecord != null && isHasChange) {
								oldRecord.setAudioContent(mEditInfo.getText().toString());
								oldRecord.setAudioLength(o);
								RecordDbUtils.getInstance().update(oldRecord);
								EventBus.getDefault().post(new MainThreadEvent<>(MainThreadEvent.RECORD_REFRESH_RECORD, oldRecord));
							}
							if (isFinish) {
								callback.onEvent(1);
							} else {
								showRecordNewDialog();
							}
						},e ->{
							callback.onEvent(1);
						});
	}

	/**
	 * 是否录制新内容
	 */
	private void showRecordNewDialog() {
		new AlertDialog.Builder(context)
						.setTitle("录音已保存")
						.setMessage("已成功录满60分钟，录音已保存。是否继续录制下一条？")
						.setNegativeButton("查看本条", (dialog, which) -> callback.onEvent(1))
						.setPositiveButton("录下一条", (dialog, which) -> {
							setRecord(null);
							ArmsUtils.makeText("上条录音已保存至“我的录音”");
						})
						.show();
	}

	//合并文件
	private String getMergeFile(String file) {
		String newFileUrl = Util.combineMp3(oldRecord.getAudio_url(), file);
		oldRecord.setAudio_url(newFileUrl);
		return newFileUrl;
	}

	//得到media的时间长度
	private Flowable<Integer> getMediaTime(String s) {
		return Flowable.create(emitter -> {
			int audioLength = Util.getAudioLength(context, s);
			emitter.onNext(audioLength);
		}, BackpressureStrategy.BUFFER);
	}


	/**
	 * 保存录音，并且通知修改
	 */
	private void saveRecord(String file, int time) {
		try {
			Record record = new Record("1", file, TimeUtil.getYMDHMSTime(),
							Util.getAudioLength(context, file), mEditInfo.getText().toString());
			RecordDbUtils.getInstance().save(record);
			EventBus.getDefault().post(new MainThreadEvent<>(MainThreadEvent.RECORD_REFRESH_MY, record));
		}catch (Exception e){
			Log.i("record",e.getMessage());
		}
	}

	/**
	 * 设置是否oldRecord,没有点击录音，有点击继续
	 */
	public void setRecord(Record record) {
		if (record != null) {
			oldRecord = record;
			work.setTime(oldRecord.getAudioLength());
			mEditInfo = buildEditTextView();
			mEditInfo.setText(oldRecord.getAudioContent());
			mTvStateMsg.setText("点击继续");
		} else {
			oldRecord = null;
			work.setTime(0);
			mEditInfo.setText("");
			mTvStateMsg.setText("点击录音");
		}
		showSaveAndRe(View.INVISIBLE);
	}

	/**
	 * 展示重新录制和保持
	 */
	private void showSaveAndRe(int invisible) {
		mTvSaveRecord.setVisibility(invisible);
		mTvReRecord.setVisibility(invisible);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			default:
				break;
			case R.id.tv_reRecord:
				showRerecordDialog();
				break;
			case R.id.tv_save_record:
				work.recordComplete();
				break;
			case R.id.iv_record_state:
				work.statOrPause();
				break;
		}
	}

	/**
	 * 展示重新录制
	 */
	private void showRerecordDialog() {
		new AlertDialog.Builder(context)
						.setTitle("重新录制")
						.setMessage("确定删除当前的录音，并重新录制吗？")
						.setNegativeButton("取消", (dialog, which) -> work.recordComplete())
						.setPositiveButton("重录", (dialog, which) -> {
							setRecord(oldRecord);
							work.reRecord(null);
						})
						.show();
	}

	/**
	 * 展示中途推出,
	 */
	protected void showTipDialog() {
		onPause();//先暂停
		new AlertDialog.Builder(context)
						.setTitle("温馨提示")
						.setMessage("确定要停止录音吗？")
						.setNegativeButton("停止录音", (dialog, which) -> work.recordComplete())
						.setPositiveButton("继续录音", (dialog, which) -> work.statOrPause())
						.show();
	}

	protected void onStop() {
		if (work != null) {
			//如果在正录音中，要提醒用户是否停止录音
			if (work.isRecording()) {
				showTipDialog();
			} else {
				if (work.hasRecord()) {
					//如果录制了，默认是完成录制
					work.recordComplete();
				} else {
					//如果没有，但是存在老的录音，保持一次文字
					String content = mEditInfo.getText().toString();
					if (oldRecord != null) {
						oldRecord.setAudioContent(content);
						RecordDbUtils.getInstance().update(oldRecord);
					}
					callback.onEvent(1);
				}
			}
		}
	}

	protected void onPause() {
		if (work != null) {
			work.pause();
		}
	}

	protected void onDestroy() {
		Timber.i("onDestroy");
		context.stopService(intent);
		unBindService();
	}

}
