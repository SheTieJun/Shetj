package me.shetj.download.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;

import me.shetj.download.R;
import me.shetj.download.adapter.ClassAdapter;
import me.shetj.download.base.ClassInfo;

/**
 * 专栏批量下载界面
 */
public class ChannelDownloadListActivity extends AppCompatActivity implements View.OnClickListener {

	private RecyclerView mIRecyclerView;
	private ClassAdapter classAdapter;

	public static void start(Context context) {
		context.startActivity(new Intent(context,ChannelDownloadListActivity.class));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_channel_list);
		initView();
	}


	private void initView() {
		mIRecyclerView = findViewById(R.id.IRecyclerView);
		classAdapter = new ClassAdapter(new ArrayList<ClassInfo>());
		mIRecyclerView.setAdapter(classAdapter);
		initDemo();

		classAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				setSelect(position);
			}
		});
	}

	private void setSelect(int position) {
		ClassInfo item = classAdapter.getItem(position);
		if (!item.isDownload) {
			item.isSelect = !item.isSelect;
			classAdapter.notifyItemChanged(position);
		}
	}

	private void initDemo() {
		for (int i = 0; i < 2; i++) {
			ClassInfo classInfo = new ClassInfo();
			classInfo.isDownload = true;
			classAdapter.addData(classInfo);
		}
		for (int i = 0; i < 5; i++) {
			classAdapter.addData(new ClassInfo());
		}
	}

	@Override
	public void onClick(View v) {
		int i = v.getId();
	}
}
