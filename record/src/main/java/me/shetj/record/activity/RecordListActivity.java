package me.shetj.record.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jakewharton.rxbinding2.view.RxView;

import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import me.shetj.base.base.BaseActivity;
import me.shetj.base.tools.app.ArmsUtils;
import me.shetj.record.R;
import me.shetj.record.adapter.RecordAdapter;
import me.shetj.record.bean.Record;
import me.shetj.record.bean.RecordDbUtils;
import me.shetj.record.view.EasyBottomSheetDialog;

public class RecordListActivity extends BaseActivity implements View.OnClickListener {

	private RecyclerView mIRecyclerView;
	private RecordAdapter recordAdapter;
	private ImageView mIvRecordState;
	private RelativeLayout mRlRecordView;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record_list);
		initView();
		initData();
	}

	@Override
	protected void initData() {
		List<Record> allRecord = RecordDbUtils.getInstance().getAllRecord();
		recordAdapter.setNewData(allRecord);
		if (allRecord.size() > 0 ){
			mRlRecordView.setVisibility(View.VISIBLE);
		}else {
			mRlRecordView.setVisibility(View.GONE);
		}
	}

	@Override
	protected void initView() {
		mIRecyclerView = findViewById(R.id.IRecyclerView);
		mIvRecordState = findViewById(R.id.iv_record_state);
		mIvRecordState.setOnClickListener(this);
		mRlRecordView = findViewById(R.id.rl_record_view);


		ArmsUtils.configRecycleView(mIRecyclerView, new LinearLayoutManager(this));
		recordAdapter = new RecordAdapter(new ArrayList<>());
		recordAdapter.openLoadAnimation();
		mIRecyclerView.setAdapter(recordAdapter);
		recordAdapter.setOnItemClickListener((adapter, view, position) -> {
			recordAdapter.setPlayPosition(position);
		});
		recordAdapter.setOnItemChildClickListener((adapter, view, position) -> {
			switch (view.getId()){
				case R.id.iv_more:
					EasyBottomSheetDialog dialog = new EasyBottomSheetDialog(this,recordAdapter.getItem(position));
					dialog.showBottomSheet();
					break;
				default:
					break;
			}
		});
		View view = LayoutInflater.from(this).inflate(R.layout.empty_view, null);
		RxView.clicks(view.findViewById(R.id.cd_start_record))
						.subscribe(o -> startRecord());
		recordAdapter.setEmptyView(view);

	}

	private void startHasRecord(Record item) {
		RecordActivity.start(this, item);
	}

	private void startRecord() {
		RecordActivity.start(this);
	}

	@Subscriber(tag = "update", mode = ThreadMode.MAIN)
	public void update(Record info) {
		initData();
	}



	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			default:
				break;
			case R.id.iv_record_state:
				startRecord();
				break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (recordAdapter !=null){
			recordAdapter.onDestroy();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (recordAdapter !=null){
			recordAdapter.onPause();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (recordAdapter !=null){
			recordAdapter.onResume();
		}
	}
}
