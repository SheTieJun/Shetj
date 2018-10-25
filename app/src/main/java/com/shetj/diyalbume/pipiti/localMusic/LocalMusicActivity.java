package com.shetj.diyalbume.pipiti.localMusic;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jakewharton.rxbinding2.view.RxView;
import com.shetj.diyalbume.R;
import com.shetj.diyalbume.pipiti.media.MediaPlayerUtils;
import com.shetj.diyalbume.pipiti.media.SimPlayerListener;

import java.util.ArrayList;
import java.util.List;

import me.shetj.base.base.BaseActivity;
import me.shetj.base.base.BaseMessage;
import me.shetj.base.tools.app.ArmsUtils;
import me.shetj.base.tools.json.GsonKit;
import timber.log.Timber;

/**
 * 本地音乐
 *
 * @author Administrator
 */
@Route(path = "/shetj/LocalMusicActivity")
public class LocalMusicActivity extends BaseActivity<LocalMusicPresenter> {

	private RecyclerView mIRecyclerView;
	private MusicSelectAdapter mAdapter;
	private MediaPlayerUtils mediaUtils;
	private int currentPosition = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_local_music);
		ArmsUtils.statuInScreen(this,true);
		initView();
		initData();
	}

	@Override
	protected void initView() {
		TextView title = findViewById(R.id.toolbar_title);
		title.setText("本地音乐");
		RxView.clicks( findViewById(R.id.toolbar_back)).subscribe(o -> onBackPressed());
		mediaUtils = new MediaPlayerUtils();
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
				currentPosition = position;
				mediaUtils.playOrStop(mAdapter.getItem(position).url,new SimPlayerListener(){
					@Override
					public void onStart(String url) {
						super.onStart(url);
						ArmsUtils.makeText(String.format("试听%s", mAdapter.getItem(position).name));
					}

					@Override
					public void onPause() {
						super.onPause();
						ArmsUtils.makeText( "暂停" );
					}

					@Override
					public void onProgress(int current, int size) {
						super.onProgress(current, size);
						Timber.i(current+"/"+size);
					}

					@Override
					public boolean isNext(MediaPlayerUtils mp) {
						currentPosition++;
						if (currentPosition < mAdapter.getItemCount()) {
							mp.playOrStop(mAdapter.getItem(currentPosition).url, this);
							return true;
						}else {
							return false;
						}
					}

					@Override
					public void onCompletion() {
						super.onCompletion();
						ArmsUtils.makeText("播放结束"+mAdapter.getItem(currentPosition).url);
					}
				});
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

	@Override
	protected void onPause() {
		super.onPause();
		if (null != mediaUtils) {
			mediaUtils.onPause();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (null != mediaUtils) {
			mediaUtils.onResume();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (null != mediaUtils) {
			mediaUtils.onDestroy();
		}
	}
}
