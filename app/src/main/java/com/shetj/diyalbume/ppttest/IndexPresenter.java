package com.shetj.diyalbume.ppttest;

import java.util.ArrayList;

import me.shetj.base.base.BasePresenter;
import me.shetj.base.base.IView;

/**
 * <b>@packageName：</b> com.shetj.diyalbume.ppttest<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2019/1/11 0011<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */
class IndexPresenter extends BasePresenter<IndexModel> {
	public IndexPresenter(IView view) {
		super(view);
		model =new  IndexModel();
	}

	public void initData() {
		ArrayList<ItemIndex> listIndex = new ArrayList<>();
		ArrayList<ItemIndex> list = new ArrayList<>();
		ItemIndex itemIndex = model.createItem("1");
		for (int i = 0;i <5;i++) {
			list.add(model.createItem("1"));
		}
		itemIndex.setItemIndex(list);
		ItemIndex itemIndex2 = model.createItem("2");
		itemIndex2.setItemIndex(list);
		ItemIndex itemIndex3 = model.createItem("3");
		itemIndex3.setItemIndex(list);
		ItemIndex itemIndex4 = model.createItem("4");
		itemIndex4.setItemIndex(list);

		listIndex.add(itemIndex);
		listIndex.add(itemIndex2);
		listIndex.add(itemIndex3);
		listIndex.add(itemIndex4);
		view.updateView(getMessage(1,listIndex));
	}
}
