package com.shetj.diyalbume.utils;


import androidx.annotation.Nullable;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.shetj.diyalbume.R;
import com.shetj.diyalbume.base.SQuickAdapter;

import java.util.List;

/**
 * Project Name:LiZhiWeiKe
 * Package Name:com.lizhiweike.lecture.adapter
 * Created by tom on 2018/2/1 18:25 .
 * <p>
 * Copyright (c) 2016—2017 https://www.lizhiweike.com all rights reserved.
 */
public class TimeTypeListAdapter extends SQuickAdapter<TimeType, BaseViewHolder> {
    private int position = -1;

    public void setPosition(int targetPos) {
        //如果不相等，说明有变化
        if (position != targetPos){
            int old = position;
            this.position = targetPos;
            // -1 表示默认不做任何变化
            if (old != -1) {
                notifyItemChanged(old + getHeaderLayoutCount());
            }
            if(targetPos !=-1) {
                notifyItemChanged(targetPos + getHeaderLayoutCount());
            }
        }
    }

    public TimeTypeListAdapter(@Nullable List<TimeType> data ) {
        super(R.layout.item_time_type_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TimeType model) {
        if (model == null) return;
        helper.setText(R.id.name, model.getName());
        helper.setTextColor(R.id.name, position == helper.getAdapterPosition() - getHeaderLayoutCount() ?
                getColor(R.color.theme_blue_accent) : getColor(R.color.black));
    }

}
