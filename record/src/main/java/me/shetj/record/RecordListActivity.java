package me.shetj.record;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;

import io.reactivex.functions.Consumer;
import me.shetj.base.tools.app.ArmsUtils;
import me.shetj.base.tools.json.EmptyUtils;
import me.shetj.record.adapter.RecordAdapter;

public class RecordListActivity extends AppCompatActivity {

	private RecyclerView mIRecyclerView;
	private RecordAdapter recordAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record_list);
		initView();
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

	}
}
