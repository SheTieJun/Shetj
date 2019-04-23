package me.shetj.download.view;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import me.shetj.base.base.BaseActivity;
import me.shetj.download.R;

/**
 * 下载
 */
public class DownloadActivity extends BaseActivity<DownloadPresenter> {

	private RecyclerView mIRecyclerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download_activty);
		initView();
		initData();
	}

	protected void initView() {
		mIRecyclerView = findViewById(R.id.IRecyclerView);
	}

	@Override
	protected void initData() {

	}
}
