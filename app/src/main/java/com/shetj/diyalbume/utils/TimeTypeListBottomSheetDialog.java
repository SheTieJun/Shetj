package com.shetj.diyalbume.utils;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.shetj.diyalbume.R;

/**
 * Project Name:LiZhiWeiKe
 * Package Name:com.lizhiweike.channel.mAdapter
 * Created by tom on 2017/10/24 14:56 .
 * <p>
 * Copyright (c) 2016—2017 https://www.lizhiweike.com all rights reserved.
 */
public class TimeTypeListBottomSheetDialog
        implements OnItemClickListener {
    private int position = -1;
    private BottomSheetDialog bottomSheetDialog;
    private Activity context;
    private  OnItemClickListener onItemClickListener;
    private TimeTypeListAdapter mAdapter;

    public TimeTypeListBottomSheetDialog(Activity context, int position) {
        this.context = context;
        this.position = position;
        this.bottomSheetDialog = buildBottomSheetDialog();
    }

    private BottomSheetDialog buildBottomSheetDialog() {
        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(context);
        View rootView = LayoutInflater.from(context).inflate(R.layout.dialog_time_type_list, null);
        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view);
        mAdapter = new TimeTypeListAdapter(TimeType.getTimeTypeList());
        mAdapter.setPosition(position);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            mAdapter.setPosition(position);
            TimeTypeListBottomSheetDialog.this.onItemClick(adapter,view,position);
        });
        recyclerView.setAdapter(mAdapter);
        if (position != - 1){
            recyclerView.scrollToPosition(position);
        }
        View lectureClose = rootView.findViewById(R.id.cancel);
        lectureClose.setOnClickListener(v -> dismissBottomSheet());
        mBottomSheetDialog.setContentView(rootView);
        return mBottomSheetDialog;
    }



    public void showBottomSheet() {
        if (bottomSheetDialog != null && !bottomSheetDialog.isShowing()) bottomSheetDialog.show();
    }

    public void dismissBottomSheet() {
        if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) bottomSheetDialog.dismiss();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (onItemClickListener != null) onItemClickListener.onItemClick(adapter, view, position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
