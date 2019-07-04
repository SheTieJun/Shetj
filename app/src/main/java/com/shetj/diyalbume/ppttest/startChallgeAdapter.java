package com.shetj.diyalbume.ppttest;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shetj.diyalbume.R;

import java.util.List;

/**
 * <b>@packageName：</b> com.shetj.diyalbume.ppttest<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2018/1/31<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */

public class startChallgeAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

	public startChallgeAdapter(@Nullable List<String> data) {
		super(R.layout.item_recycle_img,data);
	}

	@Override
	protected void convert(BaseViewHolder helper, String item) {

	}
}
