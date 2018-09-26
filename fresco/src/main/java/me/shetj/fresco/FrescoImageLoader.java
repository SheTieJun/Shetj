package me.shetj.fresco;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;

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
 * <b>@describe</b>FrescoImageLoader 图片加载<br>
 */
public class FrescoImageLoader implements ImageLoader {

	@Override
	public void load(SimpleDraweeView mSimpleView, String url) {
		load(mSimpleView,url,false);
	}

	@Override
	public void load(SimpleDraweeView mSimpleView, String url, boolean hasProgress) {
		// 设置样式
		if (hasProgress) {
			GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(mSimpleView.getResources());
			GenericDraweeHierarchy hierarchy = builder.setProgressBarImage(new ProgressBarDrawable()).build();
			if (FrescoUtils.PlaceholderImage !=0 ) {
				hierarchy.setPlaceholderImage(FrescoUtils.PlaceholderImage);
			}
			mSimpleView.setHierarchy(hierarchy);
		}
		// 加载图片
		mSimpleView.setImageURI(Uri.parse(url));
	}

	@Override
	public void loadProgressive(SimpleDraweeView mSimpleView, String url ){
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
		ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
						.setLocalThumbnailPreviewsEnabled(true)
						.setProgressiveRenderingEnabled(true).build();
		// 设置加载的控制
		mSimpleView.setController(Fresco.newDraweeControllerBuilder()
						.setImageRequest(request)
						.setTapToRetryEnabled(true)
						.setOldController(mSimpleView.getController())
						.build());
	}

	@Override
	public Animatable loadGif(SimpleDraweeView simpleView, String url, boolean isAuto) {
		AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
						.setUri(Uri.parse(url))
						.setAutoPlayAnimations(isAuto)
						.setOldController(simpleView.getController())
						.build();
		simpleView.setController(controller);
		return controller.getAnimatable();
	}

	@Override
	public void  prefetchImage(Context context ,String url, boolean isDiskCacheOrBitmapCache) {
		ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url)).build();
		ImagePipeline imagePipeline = Fresco.getImagePipeline();
		if (isDiskCacheOrBitmapCache){
			imagePipeline.prefetchToDiskCache(request, context);
		}else {
			imagePipeline.prefetchToBitmapCache(request, context);
		}
	}


	@Override
	public SimpleDraweeView getSimpleView(Context context, String url) {
		SimpleDraweeView simpleDraweeView = new SimpleDraweeView(context);
		ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
						.setLocalThumbnailPreviewsEnabled(true)
						.build();
		// 加载图片的控制
		PipelineDraweeController controller =
						(PipelineDraweeController) Fresco.newDraweeControllerBuilder()
						.setOldController(simpleDraweeView.getController())
						.setImageRequest(request)
						.build();
		// 加载图片
		simpleDraweeView.setController(controller);
		return simpleDraweeView;
	}

	@Override
	public void clearMemCache() {

	}

	@Override
	public void clearCacheFiles() {

	}
}
