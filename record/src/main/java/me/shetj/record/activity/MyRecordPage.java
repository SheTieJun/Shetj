package me.shetj.record.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseViewHolder;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import me.shetj.base.tools.app.ArmsUtils;
import me.shetj.record.R;
import me.shetj.record.adapter.RecordAdapter;
import me.shetj.record.bean.Record;
import me.shetj.record.bean.RecordDbUtils;
import me.shetj.record.utils.ActionCallback;
import me.shetj.record.utils.MainThreadEvent;
import me.shetj.record.view.RecordBottomSheetDialog;

/**
 * 讲师工具
 * 我的录音界面
 */
public class MyRecordPage {

	private RelativeLayout root;
	private Activity context;
	private Scene scene;
	private RecyclerView mRecyclerView;
	private ImageView mIvRecordState;
	private RecordAdapter recordAdapter;
	private FrameLayout mRlRecordView;
	private ActionCallback callback;


	public MyRecordPage(Activity context, ViewGroup mRoot, ActionCallback callback){
		this.context = context;
		this.callback = callback;
		root = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.page_my_record, null);
		scene  =new Scene(mRoot,root);
		initView(root);
		initData();
	}

	/**
	 * 我的录音场景
	 * @return
	 */
	public Scene getScene() {
		return scene;
	}

	private void initData() {
		List<Record> allRecord = RecordDbUtils.getInstance().getAllRecord();
		recordAdapter.setNewData(allRecord);
		checkShow(allRecord);
	}

	/**
	 * 判断是不是存在录音
	 */
	private void checkShow(List<Record> allRecord) {
		TransitionManager.beginDelayedTransition(root);
		if (allRecord.size() > 0) {
			mRlRecordView.setVisibility(View.VISIBLE);
		} else {
			mRlRecordView.setVisibility(View.GONE);
		}
	}

	public void initView(View view) {
		EventBus.getDefault().register(this);
		//绑定view
		mRecyclerView = view.findViewById(R.id.recycler_view);
		mIvRecordState = view.findViewById(R.id.iv_record_state);
		mRlRecordView =  view.findViewById(R.id.rl_record_view);
		//设置界面
		recordAdapter = new RecordAdapter(new ArrayList<>());
		recordAdapter.openLoadAnimation();
		recordAdapter.bindToRecyclerView(mRecyclerView);
		//设置点击
		recordAdapter.setOnItemClickListener((adapter, view1, position) -> {
			recordAdapter.setPlayPosition(position);
		});
		recordAdapter.setOnItemChildClickListener((adapter, view1, position) -> {
			if (!recordAdapter.isUploading()) {
				switch (view1.getId()) {
					case R.id.tv_more:
						recordAdapter.onPause();
						RecordBottomSheetDialog dialog = new RecordBottomSheetDialog(context, position, recordAdapter.getItem(position),
										(BaseViewHolder) mRecyclerView.findViewHolderForAdapterPosition(position+adapter.getHeaderLayoutCount()), callback);
						dialog.showBottomSheet();
						break;
					default:
						break;
				}
			}
		});
		recordAdapter.setOnItemLongClickListener(((adapter, view1, position) -> {
			recordAdapter.setPlayPosition(position);
			recordAdapter.onPause();
			RecordBottomSheetDialog dialog = new RecordBottomSheetDialog(context, position, recordAdapter.getItem(position),
							(BaseViewHolder) mRecyclerView.findViewHolderForAdapterPosition(position+adapter.getHeaderLayoutCount()), callback);
			dialog.showBottomSheet();
			return true;
		}));

		//设置空界面
		View emptyView = LayoutInflater.from(context).inflate(R.layout.empty_view, null);
		recordAdapter.setEmptyView(emptyView);
		//空界面点击开启
		emptyView.findViewById(R.id.cd_start_record).setOnClickListener(v -> {
			if (!recordAdapter.isUploading()) {
				callback.onEvent(0);
			}
		});


		//添加一个head
		View headView = new View(context);
		headView.setLayoutParams(new  ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ArmsUtils.dip2px(35f)));
		recordAdapter.addHeaderView(headView);
		//去录音界面
		mIvRecordState.setOnClickListener(v -> {
			if (!recordAdapter.isUploading()) {
				recordAdapter.setPlayPosition(-1);
				callback.onEvent(0);
			}
		});

	}


	/**
	 * 得到当前选中的record
	 */
	public Record getCurRecord(){
		if (recordAdapter.getCurPosition() != -1){
			return recordAdapter.getItem(recordAdapter.getCurPosition());
		}
		return null;
	}


	@Subscriber(mode = ThreadMode.MAIN,tag = "1")
	public void refreshData(MainThreadEvent event) {
		switch (event.getType()) {
			case MainThreadEvent.RECORD_REFRESH_MY:
				//获取数据库中最后一个数据
				Record lastRecord = RecordDbUtils.getInstance().getLastRecord();
				if (null != lastRecord){
					recordAdapter.addData(0,lastRecord);
					recordAdapter.setPlayPosition(0);
					mRecyclerView.scrollToPosition(0);
					checkShow(recordAdapter.getData());
				}
				break;
			case MainThreadEvent.RECORD_REFRESH_DEL:
				//删除录音
				recordAdapter.setPlayPosition(-1);
				recordAdapter.remove((Integer) event.getContent());
				checkShow(recordAdapter.getData());
				break;
			case MainThreadEvent.RECORD_REFRESH_RECORD:
				//继续录制后，保存后刷新
				int i = recordAdapter.getData().indexOf(event.getContent());
				if (i != -1) {
					recordAdapter.notifyItemChanged(i+recordAdapter.getHeaderLayoutCount());
				}
				break;
			case MainThreadEvent.RECORD_POST_URL:
				//上传录音成功后，通知直接做其他操作
				String mp3Url = event.getContent().toString();
				//如果需要关闭说明是来之发布界面的上传视频
				if (context.getIntent().getBooleanExtra(MyRecordActivity.NEED_CLOSE,false)){
					Intent intent = new Intent();
					intent.putExtra(MyRecordActivity.POST_URL, mp3Url);
					context.setResult(Activity.RESULT_OK,intent);
					context.finish();//需要
				}
				break;
		}
	}


	public void onDestroy() {
		EventBus.getDefault().unregister(this);
		if (recordAdapter != null){
			recordAdapter.onDestroy();
		}
		callback = null;
		root = null;
	}

	public void onPause() {
		if (recordAdapter != null){
			recordAdapter.onPause();
		}
	}
}
