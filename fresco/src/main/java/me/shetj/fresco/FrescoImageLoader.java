package me.shetj.fresco;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.ProgressiveJpegConfig;
import com.facebook.imagepipeline.image.ImmutableQualityInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * <b>@packageName：</b> me.shetj.fresco<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2018/9/26 0026<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b> FrescoImageLoader 图片加载<br>
 *   <b>  远程图片	<b>	http://, https://	HttpURLConnection 或者参考 使用其他网络加载方案<br>
 * 本地文件	 <b>file://	FileInputStream<br>
 * Content 	<b>provider	content://	ContentResolver<br>
 * asset目录下的资源		<b>asset://	AssetManager<br>
 * res目录下的资源	<b>	res://	Resources.openRawResource<br>
 * Uri中指定图片数据		<b>data:mime/type;base64,	数据类型必须符合 rfc2397规定 (仅支持 UTF-8)<br>
 */
public class FrescoImageLoader implements ImageLoader {

	@Override
	public void load(@NonNull SimpleDraweeView mSimpleView, @NonNull String url) {
		load(mSimpleView, url, false);
	}

	@Override
	public void load(@NonNull SimpleDraweeView mSimpleView, @NonNull String url,  boolean hasProgress) {
		// 设置样式
		if (hasProgress) {
			GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(mSimpleView.getResources());
			GenericDraweeHierarchy hierarchy = builder.setProgressBarImage(new ProgressBarDrawable()).build();
			mSimpleView.setHierarchy(hierarchy);
		}
		loadGif(mSimpleView,url,true);
	}

	@Override
	public void loadProgressive(@NonNull SimpleDraweeView mSimpleView, @NonNull String url ){
		// 加载质量配置
		ProgressiveJpegConfig jpegConfig = new ProgressiveJpegConfig() {
			@Override
			public int getNextScanNumberToDecode(int scanNumber) {
				return scanNumber + 2;
			}

			@Override
			public QualityInfo getQualityInfo(int scanNumber) {
				boolean isGoodEnough = (scanNumber >= 5);

				return ImmutableQualityInfo.of(scanNumber, isGoodEnough, false);
			}
		};

		ImagePipelineConfig.newBuilder(mSimpleView.getContext()).setProgressiveJpegConfig(jpegConfig).build();
		ImageRequest request = ImageRequestBuilder
						.newBuilderWithSource(Uri.parse(url))
						.setLocalThumbnailPreviewsEnabled(true)
						.setProgressiveRenderingEnabled(true)
						.build();
		// 设置加载的控制
		mSimpleView.setController(Fresco.newDraweeControllerBuilder()
						.setImageRequest(request)
						.setTapToRetryEnabled(true)
						.setAutoPlayAnimations(true)
						.setOldController(mSimpleView.getController())
						.build());
	}

	@Override
	public void loadGif(@NonNull SimpleDraweeView simpleView, @NonNull String url, @NonNull boolean isAuto) {
		AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
						.setUri(Uri.parse(url))
						.setTapToRetryEnabled(true)
						.setAutoPlayAnimations(isAuto)
						.setOldController(simpleView.getController())
						.build();
		simpleView.setController(controller);
	}

	@Override
	public void  prefetchImage(@NonNull Context context ,@NonNull String url,@NonNull boolean isDiskCacheOrBitmapCache) {
		ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url)).build();
		ImagePipeline imagePipeline = Fresco.getImagePipeline();
		if (isDiskCacheOrBitmapCache){
			imagePipeline.prefetchToDiskCache(request, context);
		}else {
			imagePipeline.prefetchToBitmapCache(request, context);
		}
	}


	@Override
	public SimpleDraweeView getSimpleView(@NonNull Context context,@NonNull String url) {
		SimpleDraweeView simpleDraweeView = new SimpleDraweeView(context);
		ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
						.setLocalThumbnailPreviewsEnabled(true)
						.build();
		// 加载图片的控制
		PipelineDraweeController controller =
						(PipelineDraweeController) Fresco.newDraweeControllerBuilder()
										.setOldController(simpleDraweeView.getController())
										.setTapToRetryEnabled(true)
										.setAutoPlayAnimations(true)
										.setImageRequest(request)
										.build();
		// 加载图片
		simpleDraweeView.setController(controller);
		return simpleDraweeView;
	}

	@Override
	public void clearMemCache() {
		ImagePipeline imagePipeline = Fresco.getImagePipeline();
		imagePipeline.clearMemoryCaches();
		/*
		清理一个Uri的数据
		ImagePipeline imagePipeline = Fresco.getImagePipeline();
		Uri uri;
		imagePipeline.evictFromMemoryCache(uri);
			imagePipeline.evictFromDiskCache(uri);
		// combines above two lines
			imagePipeline.evictFromCache(uri);

		 */
	}

	@Override
	public void clearCacheFiles() {
		ImagePipeline imagePipeline = Fresco.getImagePipeline();
		imagePipeline.clearDiskCaches();
	}
}
