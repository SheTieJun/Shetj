package me.shetj.record.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.jakewharton.rxbinding2.view.RxView;

import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import me.shetj.base.tools.app.ArmsUtils;
import me.shetj.base.tools.json.EmptyUtils;
import me.shetj.record.R;
import me.shetj.record.adapter.RecordAdapter;
import me.shetj.record.bean.Record;
import me.shetj.record.bean.RecordDbUtils;

public class RecordListActivity extends AppCompatActivity {

	private RecyclerView mIRecyclerView;
	private RecordAdapter recordAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record_list);
		initView();
		initData();
	}

	private void initData() {
		List<Record> allRecord = RecordDbUtils.getInstance().getAllRecord();
		recordAdapter.setNewData(allRecord);
	}

	private void initView() {
		mIRecyclerView = findViewById(R.id.IRecyclerView);
		ArmsUtils.configRecycleView(mIRecyclerView, new LinearLayoutManager(this));
		recordAdapter = new RecordAdapter(new ArrayList<>());
		mIRecyclerView.setAdapter(recordAdapter);
		recordAdapter.setOnItemClickListener((adapter, view, position) -> {
			ArmsUtils.makeText("position = " +position);
		});
		recordAdapter.setHeaderAndEmpty(true);
		recordAdapter.setOnLoadMoreListener(() ->{

						},mIRecyclerView);
		View view = LayoutInflater.from(this).inflate(R.layout.empty_view, null);
		RxView.clicks(view.findViewById(R.id.cd_start_record))
						.subscribe(o -> startRecord());
		recordAdapter.setEmptyView(view);

	}

	private void startRecord() {
		RecordActivity.start(this);
	}

	@Subscriber(tag = "update",mode = ThreadMode.MAIN)
	public void update(String info){

	}



}
