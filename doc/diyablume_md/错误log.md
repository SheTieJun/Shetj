1. Binary XML file line #0: Error inflating class me.imid.swipebacklayout.lib.SwipeBackLayout

```
解决了，主activitycontainerlayout问题，改成linearlayout就好了
```

2. java.lang.VerifyError: Verifier rejected class com.shetj.diyalbume.MainActivity due to bad method java.lang.Object com.shetj.diyalbume.MainActivity.access$super(com.shetj.diyalbume.MainActivity, java.lang.String, java.lang.Object[]) (declaration of 'com.shetj.diyalbume.MainActivity' appears in /data/app/com.shetj.diyalbume-2/split_lib_slice_7_apk.apk)

![](829069-20170527153651654-1670126881.png)


2. Android 8.0 系统下，PushService 会引起崩溃？

>这个问题是 8.0 系统的权限收紧引起的。大家可以在 AVOSCloud.initialize 之前调用代码 PushService.setAutoWakeUp(false)，禁止掉 PushService 的自启动。这样能暂时规避这个问题。

>与此同时，我们预计在 11 月底发布对 Android 8.0 的全面适配。

