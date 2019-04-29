package me.shetj.download.view;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.util.ArrayList;

import me.shetj.base.base.BaseActivity;
import me.shetj.base.tools.time.TimeUtil;
import me.shetj.download.R;
import me.shetj.download.adapter.TaskItemAdapter;
import me.shetj.download.base.DownloadInfo;
import me.shetj.download.base.TasksManager;

/**
 * 下载
 */
public class DownloadActivity extends BaseActivity<DownloadPresenter> implements View.OnClickListener {

	private RecyclerView mIRecyclerView;
	private TaskItemAdapter adapter;
	private Button mFab;
	private Button mBtnDel;
	private boolean isDel = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download_activty);

		initView();
		initData();
	}

	protected void initView() {
		mIRecyclerView = findViewById(R.id.IRecyclerView);
		adapter = new TaskItemAdapter(new ArrayList<DownloadInfo>());
		adapter.bindToRecyclerView(mIRecyclerView);
		TasksManager.getImpl().onCreate();
		adapter.setNewData(TasksManager.getImpl().getAllTask());

		mFab = findViewById(R.id.btn_add);
		mFab.setOnClickListener(this);
		mBtnDel = (Button) findViewById(R.id.btn_del);
		mBtnDel.setOnClickListener(this);
	}

	@Override
	protected void initData() {
	}

	@Subscriber(tag = "refresh", mode = ThreadMode.MAIN)
	public void reresh(String refresh) {
		adapter.setNewData(TasksManager.getImpl().getAllTask());
	}

	@Override
	protected void onDestroy() {
		adapter = null;
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		int i = v.getId();
		if (i == R.id.btn_add) {
			DownloadInfo downloadInfo =
							TasksManager.getImpl().addTask("https://dldir1.qq.com/foxmail/work_weixin/wxwork_android_2.4.5.5571_100001.apk?" + TimeUtil.getYMDHMSTime());

			if (downloadInfo != null) {
				TasksManager.getImpl().startDownload(downloadInfo);
				adapter.notifyDataSetChanged();
				TasksManager.getImpl().getTypeList();
			}
		}if (i == R.id.btn_del){
			isDel = !isDel;
			adapter.setDelModel(isDel);
		}
	}
}
