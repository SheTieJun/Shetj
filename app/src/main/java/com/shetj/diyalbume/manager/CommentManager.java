package com.shetj.diyalbume.manager;

import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * <b>@packageName：</b> com.shetj.diyalbume.manager<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2017/12/13<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */

class CommentManager {
	private static final CommentManager ourInstance = new CommentManager();

	static CommentManager getInstance() {
		return ourInstance;
	}

	private CommentManager() {
	}

	Map<String, Integer>  map = new HashMap<>();


	public void showNoRead(TextView textView){
		String key = textView.getTag().toString();
		boolean isExist = map.containsKey(key);
		if (!isExist){
			int noReadNumber = getNoReadNumber(key);
			textView.setVisibility(noReadNumber > 0 ? View.VISIBLE :View.GONE);
			textView.setText(noReadNumber);
		}else {
			textView.setText(map.get(key));
		}
	}

	private Integer getNoReadNumber(String key) {
		int number = Integer.parseInt(key);
		map.put(key, number);
		return number;
	}

	public void clearRead(TextView textView){
		textView.setVisibility(View.GONE);
		String key = textView.getTag().toString();
		boolean isExist = map.containsKey(key);
		if (isExist){
			clearRead(key);
			map.remove(key);
		}
	}

	private void clearRead(String key) {

	}


	public void addNoRead(String key){
		if (map.containsKey(key)){
			int noReadNumber = map.get(key) + 1;
			map.put(key,noReadNumber);
		}
	}


	public void onDestory(){
		map.clear();
	}

}
