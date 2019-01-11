package com.shetj.diyalbume.ppttest;

import java.util.ArrayList;

/**
 * <b>@packageName：</b> com.shetj.diyalbume.ppttest<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2019/1/11 0011<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */
class ItemIndex {


	private String type;
	private String img;
	private	String content;
	private	String title;

	private	ArrayList<ItemIndex> ItemIndex;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ArrayList<com.shetj.diyalbume.ppttest.ItemIndex> getItemIndex() {
		return ItemIndex;
	}

	public void setItemIndex(ArrayList<com.shetj.diyalbume.ppttest.ItemIndex> itemIndex) {
		ItemIndex = itemIndex;
	}

	public ItemIndex(String type, String img, String content, String title) {
		this.type = type;
		this.img = img;
		this.content = content;
		this.title = title;
	}
}
