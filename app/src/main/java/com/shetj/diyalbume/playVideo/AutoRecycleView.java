package com.shetj.diyalbume.playVideo;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shetj.diyalbume.R;

import java.util.List;

import me.shetj.base.tools.app.LogUtil;
import me.shetj.base.tools.app.TimeUtil;

/**
 * <b>@packageName：</b> com.shetj.diyalbume.playVideo<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2017/12/12<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */

public class AutoRecycleView extends BaseQuickAdapter<String ,BaseViewHolder> {

	public int playPostin = -1;

	public  int oldPosition = -1;

	public int i = 0;
	public AutoRecycleView(@Nullable List<String> data) {
		super(R.layout.item_recycle_string,data);
	}

	@Override
	protected void convert(BaseViewHolder helper, String item) {
		if(helper.getLayoutPosition() == playPostin ){
			helper.setText(R.id.tv_string,"播放"+ TimeUtil.getYMDHMSTime());
		}else {
			helper.setText(R.id.tv_string,item + TimeUtil.getYMDHMSTime());
		}
	}

	public void setPlay(int i) {
		if (playPostin != i) {
			playPostin = i;
			if (oldPosition != -1) {
				notifyItemChanged(oldPosition);
			}
			oldPosition = playPostin;
			notifyItemChanged(playPostin);
		}


	}

	public void isStop(int firstVisibleItemPosition,int lastVisibleItemPosition) {
		LogUtil.show(firstVisibleItemPosition+"////"+lastVisibleItemPosition);
		if (firstVisibleItemPosition > playPostin
						||lastVisibleItemPosition < playPostin){
			LogUtil.show((i++)+"停止播放"+playPostin);
			if (oldPosition != -1) {
				notifyItemChanged(oldPosition);
			}
			oldPosition = -1;
			setPlay(-1);
		}
	}
}
