package com.shetj.diyalbume.miui;

import android.widget.ImageView;


import com.bumptech.glide.request.target.Target;
import com.shetj.diyalbume.R;


import cn.jiguang.imui.commons.ImageLoader;
import me.shetj.base.s;

/**
 * <b>@packageName：</b> com.shetj.diyalbume.miui<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2017/12/25<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */

public class MyImageLoader implements ImageLoader {

	@Override
	public void loadAvatarImage(ImageView avatarImageView, String string) {
		// You can use other image load libraries.
		if (string.contains("R.drawable")) {
			Integer resId = s.getApp().getApplicationContext().getResources().getIdentifier(string.replace("R.drawable.", ""),
							"drawable", s.getApp().getApplicationContext().getPackageName());
			avatarImageView.setImageResource(resId);
		} else {
			GlideApp.with(avatarImageView.getContext())
							.load(string)
							.placeholder(R.drawable.aurora_headicon_default)
							.into(avatarImageView);
		}
	}

	@Override
	public void loadImage(ImageView imageView, String string) {
		// You can use other image load libraries.
		GlideApp.with(imageView.getContext())
						.load(string)
						.fitCenter()
						.placeholder(R.drawable.aurora_picture_not_found)
						.override(400, Target.SIZE_ORIGINAL)
						.into(imageView);
	}
}
