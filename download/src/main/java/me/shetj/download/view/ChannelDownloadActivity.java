package me.shetj.download.view;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import me.shetj.download.R;
import me.shetj.download.adapter.ChannelClassItemAdapter;
import me.shetj.download.base.ClassInfo;

/**
 * 专栏进来的已下载界面
 */
public class ChannelDownloadActivity extends AppCompatActivity implements View.OnClickListener {

	private RecyclerView mIRecyclerView;
	private ChannelClassItemAdapter classItemAdapter;
	private Button mBtnDel;
	private boolean isDel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_channels);
		initView();
	}

	private void initView() {
		mIRecyclerView = findViewById(R.id.IRecyclerView);
		classItemAdapter = new ChannelClassItemAdapter(new ArrayList<ClassInfo>());
		mIRecyclerView.setAdapter(classItemAdapter);
		initDemo();
		mBtnDel = findViewById(R.id.btn_del);
		mBtnDel.setOnClickListener(this);
	}

	private void initDemo() {
		for (int i = 0; i < 5; i++) {
			classItemAdapter.addData(new ClassInfo());
		}
	}

	@Override
	public void onClick(View v) {
		int i = v.getId();
		if (i == R.id.btn_del){
			isDel = !isDel;
			classItemAdapter.setDelModel(isDel);
		}
	}
}
