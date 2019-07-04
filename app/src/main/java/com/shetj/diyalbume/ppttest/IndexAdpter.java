package com.shetj.diyalbume.ppttest;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shetj.diyalbume.R;

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
public class IndexAdpter extends BaseQuickAdapter<ItemIndex,BaseViewHolder> {
	public IndexAdpter(@Nullable List<ItemIndex> data) {
		super(R.layout.item_index_recycle,data);
	}

	@Override
	protected void convert(BaseViewHolder helper, ItemIndex item) {

		RecyclerView recyclerView = helper.getView(R.id.IRecyclerView);
		if (item.getType().equals("4")){
			GridLayoutManager manager = new GridLayoutManager(mContext, 2);
			ArmsUtils.configRecycleView(recyclerView,manager);
		}else {
			GridLayoutManager manager = new GridLayoutManager(mContext, 3);
			ArmsUtils.configRecycleView(recyclerView,manager);
		}

		switch (item.getType()){
			case "1":
				ImageAdpter imageAdpter = new ImageAdpter(item.getItemIndex());
				recyclerView.setAdapter(imageAdpter);
				break;
			case "2":
				ImageAdpter imageAdpter2 = new ImageAdpter(item.getItemIndex());
				recyclerView.setAdapter(imageAdpter2);
				break;
			case "3":
				UserAdpter userAdpter = new UserAdpter(item.getItemIndex());
				recyclerView.setAdapter(userAdpter);
				break;
			case "4":
				ImageAdpter imageAdpter4 = new ImageAdpter(item.getItemIndex());
				recyclerView.setAdapter(imageAdpter4);
				break;
			default:
				break;
		}

	}
}
