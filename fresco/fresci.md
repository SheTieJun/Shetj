#### 使用 https://www.fresco-cn.org/
```
<com.facebook.drawee.view.SimpleDraweeView
  android:id="@+id/my_image_view"
  android:layout_width="20dp"
  android:layout_height="20dp"
  fresco:fadeDuration="300"
  fresco:actualImageScaleType="focusCrop"
  fresco:placeholderImage="@color/wait_color"
  fresco:placeholderImageScaleType="fitCenter"
  fresco:failureImage="@drawable/error"
  fresco:failureImageScaleType="centerInside"
  fresco:retryImage="@drawable/retrying"
  fresco:retryImageScaleType="centerCrop"
  fresco:progressBarImage="@drawable/progress_bar"
  fresco:progressBarImageScaleType="centerInside"
  fresco:progressBarAutoRotateInterval="1000"
  fresco:backgroundImage="@color/blue"
  fresco:overlayImage="@drawable/watermark"
  fresco:pressedStateOverlayImage="@color/red"
  fresco:roundAsCircle="false"
  fresco:roundedCornerRadius="1dp"
  fresco:roundTopLeft="true"
  fresco:roundTopRight="false"
  fresco:roundBottomLeft="false"
  fresco:roundBottomRight="true"
  fresco:roundWithOverlayColor="@color/corner_color"
  fresco:roundingBorderWidth="2dp"
  fresco:roundingBorderColor="@color/border_color"
/>

actualImageScaleType                    加载完成的图片的缩放样式

fadeDuration                            由进度条和占位符图片渐变过渡到加载完成的图片所使用的时间间隔

failureImage                            加载失败所使用的图片

failureImageScaleType                   加载失败所使用的图片的缩放样式

placeholderImage                        占位符图片

placeholderImageScaleType               占位符图片的缩放样式

progressBarAutoRotateInterval           旋转进度条旋转1圈所需要的时间

progressBarImage                        旋转进度条所使用的图片

progressBarImageScaleType               旋转进度条所使用的图片的缩放样式

retryImage                              重试所使用的图片

retryImageScaleType                     重试所使用的图片的缩放样式

backgroundImage                         背景图片

overlayImage                            覆盖在加载完成后图片上的叠加图片

pressedStateOverlayImage                按压状态下的叠加图片

roundAsCircle                           是否将图片剪切为圆形

roundedCornerRadius                     圆角图片时候，圆角的半径大小

roundTopLeft                            左上角是否为圆角

roundTopRight                           右上角是否为圆角

roundBottomLeft                         左下角是否为圆角

roundBottomRight                        右下角是否为圆角

roundWithOverlayColor                   圆角或圆形图叠加的颜色，只能是颜色

roundingBorderWidth                     圆角或圆形图边框的宽度

roundingBorderColor                     圆角或圆形图边框的颜色

viewAspectRatio                         设置宽高比


```

#####  设置比例
```
<com.facebook.drawee.view.SimpleDraweeView
    android:id="@+id/my_image_view"
    android:layout_width="20dp"
    android:layout_height="wrap_content"
    fresco:viewAspectRatio="1.33"
    <!-- other attributes -->

    mSimpleDraweeView.setAspectRatio(1.33f);
```