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
	/**
	 * 分享链接
	 * @param context
	 * @param title
	 * @param imgUrl
	 * @param url
	 * @param des
	 * @param listener
	 */
	public  void showLink(Activity context, String title, String imgUrl, String url, String des,UMShareListener listener){
		UMWeb web = new UMWeb(url);
		UMImage image = new UMImage(context, imgUrl);
		web.setTitle(title);
		web.setDescription(des);
		web.setThumb(image);
		new ShareAction(context).withMedia(web)
						.setDisplayList(
										SHARE_MEDIA.QQ
										, SHARE_MEDIA.QZONE
										, SHARE_MEDIA.WEIXIN
										, SHARE_MEDIA.WEIXIN_CIRCLE
						)
						.setCallback(listener)
						.open();
	}

	/**
	 * 分享图片
	 * @param context
	 * @param imgUrl
	 * @param listener
	 */
	public  void showUrl(Activity context,String imgUrl,UMShareListener listener){
		UMImage image = new UMImage(context,imgUrl);
		//微信需要预览图
		image.setThumb(image);
		new ShareAction(context)
						.withMedia(image)
						.setDisplayList( SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE)
						.setCallback(listener)
						.open();
	}
}
