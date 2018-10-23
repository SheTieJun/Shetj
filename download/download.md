#### 下载APP 并且安装



```
     val apkName = DownloadService.getApkName("1.0.0", "app-toyo.apk")

     DownloadService.install(Utils.getApp(),"1.0.0",apkName,
                    "http://oss.qcshendeng.com/app-toyo.apk")
```