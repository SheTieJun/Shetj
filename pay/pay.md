#### 使用
来自
https://github.com/Javen205/JPay
去掉了部分，修改了lib
##### 阿里
阿里支付使用的是AlipaySDK_No_UTDID.zip
不会出现与支付宝的包出现UTDID冲突

##### 微信
```
    implementation 'com.tencent.mm.opensdk:wechat-sdk-android-with-mta:5.1.4'
```
app :中创建 WXPayEntryActivity

如下
```
       <!-- 微信支付 -->

        <activity-alias
            android:name=".wxapi.WXPayEntryActivity"
            android:exported="true"
            android:targetActivity="me.shetj.base.pay.weixin.WXPayEntryActivity"/>
```