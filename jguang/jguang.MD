```
    <!-- Required -->
    <permission
        android:name="${applicationId}.permission.JPUSH_MESSAGE"
        android:protectionLevel="signature"/>
```

```
    <!-- 微信平台结束 -->
        <!-- JPush configuration start -->
        <receiver
            android:name=".receiver.JiPushReceiver"
            android:enabled="true"
            android:exported="false">
            <intent-filter>
                <action android:name="cn.jpush.android.intent.REGISTRATION"/> <!-- Required  用户注册SDK的intent -->
                <action android:name="cn.jpush.android.intent.MESSAGE_RECEIVED"/> <!-- Required  用户接收SDK消息的intent -->
                <action android:name="cn.jpush.android.intent.NOTIFICATION_RECEIVED"/> <!-- Required  用户接收SDK通知栏信息的intent -->
                <action android:name="cn.jpush.android.intent.NOTIFICATION_OPENED"/> <!-- Required  用户打开自定义通知栏的intent -->
                <action android:name="cn.jpush.android.intent.ACTION_RICHPUSH_CALLBACK"/> <!-- Optional 用户接受Rich Push Javascript 回调函数的intent -->
                <action android:name="cn.jpush.android.intent.CONNECTION"/> <!-- 接收网络变化 连接/断开 since 1.6.3 -->
                <category android:name="${applicationId}"/>
            </intent-filter>
        </receiver> <!-- User defined 用户自定义的广播接收器 -->
        <receiver
            android:name=".receiver.MyJPushMessageReceiver"
            android:permission="com.qcshendeng.toyo.permission.JPUSH_MESSAGE">
            <intent-filter>
                <action android:name="cn.jpush.android.intent.RECEIVE_MESSAGE"/>

                <category android:name="${applicationId}"/>
            </intent-filter>
        </receiver>
```