package com.shetj.diyalbume.ppttest;

import androidx.annotation.Nullable;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shetj.diyalbume.R;
import com.shetj.diyalbume.miui.GlideApp;

import java.util.List;

/**
 * <b>@packageName：</b> com.shetj.diyalbume.ppttest<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2019/1/11 0011<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */
public class ImageAdpter extends BaseQuickAdapter<ItemIndex,BaseViewHolder> {
	public ImageAdpter(@Nullable List<ItemIndex> data) {
		super(R.layout.item_course_img,data);
	}

	@Override
	protected void convert(BaseViewHolder helper, ItemIndex item) {
		GlideApp.with(mContext).load(item.getImg()).into((ImageView) helper.getView(R.id.iv_image));
	}
}
