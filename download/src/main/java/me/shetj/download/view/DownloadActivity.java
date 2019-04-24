package me.shetj.download.view;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import me.shetj.base.base.BaseActivity;
import me.shetj.download.R;
import me.shetj.download.adapter.DownloadAdapter;

/**
 * 下载
 */
public class DownloadActivity extends BaseActivity<DownloadPresenter> {

	private RecyclerView mIRecyclerView;
	private DownloadAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download_activty);
		initView();
		initData();
	}

	protected void initView() {
		mIRecyclerView = findViewById(R.id.IRecyclerView);
		adapter = new DownloadAdapter(new ArrayList());
		adapter.bindToRecyclerView(mIRecyclerView);

	}

	@Override
	protected void initData() {

	}
}
