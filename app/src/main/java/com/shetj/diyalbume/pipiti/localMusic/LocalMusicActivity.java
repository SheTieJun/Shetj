package com.shetj.diyalbume.pipiti.localMusic;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shetj.diyalbume.R;

import java.util.ArrayList;
import java.util.List;

import me.shetj.base.base.BaseActivity;
import me.shetj.base.base.BaseMessage;
import me.shetj.base.tools.app.ArmsUtils;
import me.shetj.base.tools.json.GsonKit;

/**
 * 本地音乐
 *
 * @author Administrator
 */
@Route(path = "/shetj/LocalMusicActivity")
public class LocalMusicActivity extends BaseActivity<LocalMusicPresenter> {

	private RecyclerView mIRecyclerView;
	private MusicSelectAdapter mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_local_music);
		initView();
		initData();
	}

	@Override
	protected void initView() {

		mIRecyclerView = (RecyclerView) findViewById(R.id.IRecyclerView);
		ArmsUtils.configRecycleView(mIRecyclerView,new LinearLayoutManager(this));
		mAdapter = new MusicSelectAdapter(new ArrayList<>());
		mAdapter.bindToRecyclerView(mIRecyclerView);
		mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				ArmsUtils.makeText(GsonKit.objectToJson(mAdapter.getItem(position)));
			}
		});
		mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
			@Override
			public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
				ArmsUtils.makeText(String.format("试听%s", mAdapter.getItem(position).name));
			}
		});
	}

	@Override
	protected void initData() {
		mPresenter = new LocalMusicPresenter(this);
		mPresenter.initMusic();
	}

	@Override
	public void updateView(BaseMessage message) {
		super.updateView(message);
		switch (message.type){
			case 1:
				mAdapter.setNewData((List<Music>) message.obj);
				break;
			default:
				break;
		}
	}
}