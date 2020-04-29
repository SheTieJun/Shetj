# 魔方裂变APP - Android  \#\#

### 版本控制 \#\#\#

[SVN](http://172.35.2.247:999/svn/app_android)

#### 开发环境 \#\#\#\#

```text
    classpath 'com.android.tools.build:gradle:3.0.0-beta1'
distributionUrl=https\://services.gradle.org/distributions/gradle-4.1-rc-1-all.zip
Android Studio 3.0
compileSdkVersion 26
buildToolsVersion "26.0.2"
```

#### 使用框架介绍  \#\#\#\#

注：使用他人框架 要求尽量在封装一层

1. 事件总线 [androideventbus](https://github.com/hehonghui/AndroidEventBus) 
2. 数据库 [GreenDao 3.0](https://github.com/greenrobot/greenDAO) 或者 [xUtils3（Db](https://github.com/wyouflf/xUtils3)）
3. [BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper/)  
4. 图片加载 [Glide](https://github.com/bumptech/glide)
5. webView  [AgentWeb](https://github.com/Justson/AgentWeb)
6. Json解析  [Gson](https://github.com/google/gson)
7. 6.0权限管理 [PermissionGen](https://github.com/lovedise/PermissionGen)
8. 侧滑退出 [swipebacklayout](https://github.com/qmdx/SwipeBackLayout)
9. 视频缓存管理 [AndroidVideoCache](https://github.com/danikula/AndroidVideoCache)
10. 通用的popupwindow [BasePopup](https://github.com/razerdp/BasePopup)
11. 网络请求 -----目前未确定（[xUtils3（http](https://github.com/wyouflf/xUtils3)）或者[Retrofit2+RxJava2](https://github.com/zhou-you/RxEasyHttp) ）
12. Dialog工具类 [DialogUtil](https://github.com/hss01248/DialogUtil)
13. Android内存泄漏检测工具 [LeakCanary](https://github.com/square/leakcanary)
14. 多状态视图 [MultipleStatusView](https://github.com/qyxxjd/MultipleStatusView)
15. 点赞+1效果 [GoodView](https://github.com/venshine/GoodView)
16. 屏幕适配  [AndroidAutoLayout](https://github.com/hongyangAndroid/AndroidAutoLayout)
17. 图片浏览 [ZoomPreviewPicture](https://github.com/yangchaojiang/ZoomPreviewPicture)
18. 仿照微信录制视频[JCameraView](https://github.com/CJT2325/CameraView)
19. 九图展示[NineGridImageView](https://jaeger.itscoder.com/android/2016/03/06/nine-grid-iamge-view-libaray.html)
20. Adding Components to your Project [Android Architecture Components](https://developer.android.google.cn/topic/libraries/architecture/index.html)
21. 二维码
22. 下载管理 rxdownload3
23. [Lottie for Android](https://github.com/airbnb/lottie-android#lottie-for-android-ios-react-native-and-web)
24. 待+++++++

#### 第三方平台 \#\#\#\#

1. 推送 [极光推送](https://docs.jiguang.cn/jpush/guideline/intro/)
2. 分享 [极光分享](https://docs.jiguang.cn/jshare/guideline/intro/)
3. 云存储 [阿里云OSS](https://help.aliyun.com/document_detail/32042.html?spm=5176.87240.400427.45.sHMUpt)
4. 统计分析  目前用的[极光统计](https://docs.jiguang.cn/janalytics/guideline/intro/) 建议改成[友盟统计](http://mobile.umeng.com/analytics)---原因极光不太熟悉，而且极光比较成熟了
5. 通讯验证码 [阿里大于](https://dayu.aliyun.com/product/sms?spm=a3142.7791109.0.0.1d6d73c4oGtKxk)----后台负责
6. 录制视频 [Android短视频SDK](https://help.aliyun.com/document_detail/53421.html?spm=5176.doc54832.6.688.5hqB17)  
7. 即时通讯  [融云SDK](http://www.rongcloud.cn/docs/) 
8. 待+++++++

#### 魔方裂变AS项目结构 \#\#\#\#

项目名：MoFang

1. **app**  主module: 魔方裂APP
2. **aliyun**  阿里云服务相关（OSS 、视频录制）
3. **jguang**  极光服务相关（推送、分享、统计） ----- 统计是否换成友盟
4. **paylib**  支付相关（微信支付、支付宝支付）
5. **base**    各种基类、已经工具（Http、Db、View等）
6. **videorecord**  视频录制（目前未使用，为防止阿里云短视频SDK问题（如收费））
7. 待+++++++

## License

```text
魔方裂变 shetj
```

编写时间 10/12/2017 11:04:00 AM

更新 10/13/2017 4:35:06 PM

更新 10/27/2017 4:22:01 PM 加入图片浏览、防微信录制视频、九图

