package com.shetj.diyalbume.ppttest;

import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shetj.diyalbume.R;
import com.shetj.diyalbume.miui.GlideApp;

import java.util.List;

import me.shetj.base.tools.app.ArmsUtils;

/**
 * <b>@packageName：</b> com.shetj.diyalbume.ppttest<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2019/1/11 0011<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */
public class UserAdpter extends BaseQuickAdapter<ItemIndex,BaseViewHolder> {
	public UserAdpter(@Nullable List<ItemIndex> data) {
		super(R.layout.item_user_info,data);
	}

	@Override
	protected void convert(BaseViewHolder helper, ItemIndex item) {

		GlideApp.with(mContext).load(item.getImg()).into((ImageView) helper.getView(R.id.iv_image));
		helper.setText(R.id.tv_name,item.getContent());
	}
}
