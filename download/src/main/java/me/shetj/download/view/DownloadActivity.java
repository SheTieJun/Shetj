package me.shetj.download.view;

import android.app.Notification;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.liulishuo.filedownloader.FileDownloader;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import me.shetj.base.base.BaseActivity;
import me.shetj.base.tools.time.TimeUtil;
import me.shetj.download.DownloadNotification;
import me.shetj.download.R;
import me.shetj.download.adapter.TaskItemAdapter;
import me.shetj.download.base.DownloadInfo;
import me.shetj.download.base.TasksManager;

/**
 * 下载
 */
public class DownloadActivity extends BaseActivity<DownloadPresenter> implements View.OnClickListener {

	private RecyclerView mIRecyclerView;
	//	private DownloadAdapter adapter;
	private TaskItemAdapter adapter;
	private Button mFab;
	private Notification notification;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download_activty);
		initView();
		initData();
	}

	protected void initView() {
		mIRecyclerView = findViewById(R.id.IRecyclerView);
//		adapter = new DownloadAdapter(new ArrayList());
//		adapter.bindToRecyclerView(mIRecyclerView);

		adapter = new TaskItemAdapter(new ArrayList<DownloadInfo>());
		adapter.bindToRecyclerView(mIRecyclerView);

		TasksManager.getImpl().onCreate(new WeakReference<RxAppCompatActivity>(this));

		adapter.setNewData(TasksManager.getImpl().getAllTask());
		mFab = findViewById(R.id.btn_add);
		mFab.setOnClickListener(this);
	}

	@Override
	protected void initData() {
		notification = DownloadNotification.notify(this, R.drawable.icon_loading, "下载中",
						"下载...", 100);
		FileDownloader.getImpl().startForeground(100, notification);
	}

	@Subscriber(tag = "refresh", mode = ThreadMode.MAIN)
	public void reresh(String refresh) {
		adapter.notifyDataSetChanged();
	}

	@Override
	protected void onDestroy() {
		TasksManager.getImpl().onDestroy();
		adapter = null;
//		FileDownloader.getImpl().pauseAll();
		FileDownloader.getImpl().stopForeground(true);
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		int i = v.getId();
		if (i == R.id.btn_add) {
			DownloadInfo downloadInfo =
							TasksManager.getImpl().addTask("https://dldir1.qq.com/foxmail/work_weixin/wxwork_android_2.4.5.5571_100001.apk");
			if (downloadInfo !=null) {
				TasksManager.getImpl().startDownload(downloadInfo);
				adapter.notifyDataSetChanged();
			}
		} else {

		}
	}
}
