package me.shetj.umeng;

import android.app.Activity;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * <b>@packageName：</b> me.shetj.umeng<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2018/4/9 0009<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */
public class ShareUtils {
	public static void showWeb(Activity context, String title, String imgUrl, String url, String des){
		UMWeb web = new UMWeb(url);
		UMImage image = new UMImage(context, imgUrl);//网络图片
		web.setTitle(title);
		web.setDescription(des);
		web.setThumb(image);
		new ShareAction(context).withMedia(web)
						.setDisplayList(
//										SHARE_MEDIA.QQ
//										, SHARE_MEDIA.QZONE,
										 SHARE_MEDIA.WEIXIN
										, SHARE_MEDIA.WEIXIN_CIRCLE
						)
						.setCallback(new UMShareListener() {
							@Override
							public void onStart(SHARE_MEDIA share_media) {

							}

							@Override
							public void onResult(SHARE_MEDIA share_media) {

							}

							@Override
							public void onError(SHARE_MEDIA share_media, Throwable throwable) {

							}

							@Override
							public void onCancel(SHARE_MEDIA share_media) {

							}
						})
						.open();
	}

}
