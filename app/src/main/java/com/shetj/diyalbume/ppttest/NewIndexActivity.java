package com.shetj.diyalbume.ppttest;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shetj.diyalbume.R;

import java.util.ArrayList;
import java.util.List;

import me.shetj.base.base.BaseActivity;
import me.shetj.base.base.BaseMessage;
import me.shetj.base.tools.app.ArmsUtils;

/**
 * @author shetj
 */
public class NewIndexActivity extends BaseActivity<IndexPresenter> {

	private RecyclerView mIRecyclerView;
	private IndexAdpter indexAdpter ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_index);
		ArmsUtils.statuInScreen(this,true);
		initView();
		initData();
	}

	@Override
	protected void initView() {
		mIRecyclerView = findViewById(R.id.IRecyclerView);
		ArmsUtils.configRecycleView(mIRecyclerView,new LinearLayoutManager(this));
	}

	@Override
	protected void initData() {
		mPresenter = new IndexPresenter(this);
		indexAdpter = new IndexAdpter(new ArrayList<>());
		mIRecyclerView.setAdapter(indexAdpter);

		mPresenter.initData();
		addHeadView();
	}

	private void addHeadView() {

	}


	@Override
	public void updateView(@NonNull BaseMessage message) {
		super.updateView(message);
		switch (message.type){
			case 1:
				indexAdpter.setNewData((List<ItemIndex>) message.obj);
				break;
			case 2:
				break;
			default:
				break;

		}
	}
}
