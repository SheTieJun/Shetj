package me.shetj.fresco;


import android.content.Context;
import android.graphics.drawable.Animatable;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * C远程图片	<b>	http://, https://	HttpURLConnection 或者参考 使用其他网络加载方案<br>
 * 本地文件	 <b>file://	FileInputStream<br>
 * Content 	<b>provider	content://	ContentResolver<br>
 * asset目录下的资源		<b>asset://	AssetManager<br>
 * res目录下的资源	<b>	res://	Resources.openRawResource<br>
 * Uri中指定图片数据		<b>data:mime/type;base64,	数据类型必须符合 rfc2397规定 (仅支持 UTF-8)<br>
 * @author shetj
 */
public interface ImageLoader {

    /**
     * 加载图片
     * @param simpleView 容器
     * @param url url地址
     */
    void load(SimpleDraweeView simpleView, String url);
    /**
     * 加载图片
     * @param simpleView 容器
     * @param url url地址
     * @param hasProgress 是否展示进度
     */
    void load(SimpleDraweeView simpleView, String url,boolean hasProgress);

    /**
     * 渐进式展示图片
     * @param mSimpleView 容器
     * @param url url地址
     */
    void loadProgressive(SimpleDraweeView mSimpleView, String url );

    /**
     * 加载GIf
     * @param simpleView
     * @param url
     * @param isAuto
     */
    Animatable loadGif(SimpleDraweeView simpleView, String url , boolean isAuto );


    /**
     * 预加载图片
     * @param url
     * @param isDiskCacheOrBitmapCache 是否是Disk 或者 内存
     */
    void prefetchImage(Context context , String url, boolean isDiskCacheOrBitmapCache);
    /**
     * 获取view
     * @param context
     * @return
     */
    SimpleDraweeView getSimpleView(Context context,String url);

    void clearMemCache();

    void clearCacheFiles();
}
