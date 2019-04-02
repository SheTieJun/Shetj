package me.shetj.record.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;
import me.shetj.base.tools.json.GsonKit;
import me.shetj.record.R;
import me.shetj.record.bean.Record;
import me.shetj.record.bean.RecordDbUtils;
import me.shetj.record.utils.CreateRecordUtils;
import me.shetj.record.utils.RecordCallBack;
import me.shetj.record.utils.Util;

public class RecordActivity extends AppCompatActivity implements View.OnClickListener {

	private EditText mEditInfo;
	private ProgressBar mProgressBarRecord;
	private TextView mTvRecordTime;
	private TextView mTvReRecord;
	private TextView mTvSaveRecord;
	private ImageView mIvRecordState;
	private CreateRecordUtils recordUtils;
	/**
	 * 已暂停，点击继续
	 */
	private TextView mTvStateMsg;

	public static void start(Context context) {
		context.startActivity(new Intent(context, RecordActivity.class));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record);
		initView();
		initData();
		new RxPermissions(this)
						.request(Manifest.permission.READ_EXTERNAL_STORAGE,
										Manifest.permission.WRITE_EXTERNAL_STORAGE,
										Manifest.permission.RECORD_AUDIO
						).subscribe(new Consumer<Boolean>() {
			@Override
			public void accept(Boolean aBoolean) throws Exception {

			}
		});
	}

	private void initData() {
		recordUtils = new CreateRecordUtils(mIvRecordState, new RecordCallBack() {
			@Override
			public void start() {
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
				Record record = new Record("1",file,"录音",time);
				Log.i("record", GsonKit.objectToJson(record));
				RecordDbUtils.getInstance().save(record);
			}

			@Override
			public void onProgress(int time) {
				mProgressBarRecord.setProgress(time);
				mTvRecordTime.setText(Util.formatSeconds2(time));
			}
		});
	}

	private void initView() {
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
		setCanNotEditAndClick();
	}

	public void setCanNotEditAndClick() {
		mEditInfo.setFocusable(false);
		mEditInfo.setFocusableInTouchMode(false);
		mEditInfo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setCanEdit();
			}
		});
	}

	public void setCanEdit() {
		mEditInfo.setFocusable(true);
		mEditInfo.setFocusableInTouchMode(true);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			default:
				break;
			case R.id.tv_reRecord:
				recordUtils.reRecord();
				break;
			case R.id.tv_save_record:
				recordUtils.recordComplete();
				break;
			case R.id.iv_record_state:
				recordUtils.statOrPause();
				break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		recordUtils.clear();
	}
}
