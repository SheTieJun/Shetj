package me.shetj.record.view;

import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;

import org.simple.eventbus.EventBus;

import me.shetj.base.tools.app.ArmsUtils;
import me.shetj.record.R;
import me.shetj.record.activity.RecordActivity;
import me.shetj.record.bean.Record;
import me.shetj.record.bean.RecordDbUtils;

/**
 * Project Name:android
 * Package Name:com.lizhiweike.widget.dialog
 * Created by lahm on 2018/8/29 12:05 .
 * <p>
 * Copyright (c) 2016—2017 https://www.lizhiweike.com all rights reserved.
 */
public class EasyBottomSheetDialog implements View.OnClickListener {
	private BottomSheetDialog easyBottomSheetDialog;
	private Record record;
	private Context context ;

	public EasyBottomSheetDialog(Context context, Record record) {
		this.record = record;
		this.context = context;
		this.easyBottomSheetDialog = buildBottomSheetDialog(context);
	}

	private BottomSheetDialog buildBottomSheetDialog(Context context) {
		BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
		View rootView = LayoutInflater.from(context).inflate(R.layout.dailog_record_case, null);
		bottomSheetDialog.setContentView(rootView);
		rootView.findViewById(R.id.tv_record).setOnClickListener(this);
		rootView.findViewById(R.id.tv_edit_name).setOnClickListener(this);
		rootView.findViewById(R.id.tv_del).setOnClickListener(this);
		rootView.findViewById(R.id.tv_cancel).setOnClickListener(this);

		//对于时间已经大于60 分钟的 不显示继续录制
		rootView.findViewById(R.id.tv_record).setVisibility(record.getAudioLength() > 3599 ? View.GONE:View.VISIBLE);
		return bottomSheetDialog;
	}


	public void showBottomSheet() {
		if (easyBottomSheetDialog != null) easyBottomSheetDialog.show();
	}

	public void dismissBottomSheet() {
		if (easyBottomSheetDialog != null) easyBottomSheetDialog.dismiss();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.tv_record:
				RecordActivity.start(context,record);
				dismissBottomSheet();
				break;
			case R.id.tv_del:
				RecordDbUtils.getInstance().del(record);
				EventBus.getDefault().post(new Record(),"update");
				dismissBottomSheet();
				break;
			case R.id.tv_cancel:
				dismissBottomSheet();
				break;
			case R.id.tv_edit_name:
				ArmsUtils.makeText("展示修改界面");
				dismissBottomSheet();
				break;
			default:
				break;
		}
	}
}
