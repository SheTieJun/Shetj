# 更新说明

更新日志：

更新说明： 版本：1.0-&gt;1.0.1.0

```text
1、加入游客登录监听
public interface LoginListener {
/**
 * On tourist.
 */
void onTourist();
/**
 *On login.
 *@param phone the phone
 */
void onLogin(String phone);
/**
 *On logout.
 */
void onLogout();
}

2、退出时完全退出APP   
@Override
protected void onDestroy() {
    super.onDestroy();
    MFangHelper.getInstance(this).onDestroy();
}
```

更新说明： 版本：1.0.1.0-&gt;1.0.2.0

```text
支付回调修改

MFangHelper.getInstance(this).setPayCallBack(new MFangPayCallBack() {
    @Override
    public void paySuccess(String orderNumber) {

    }

    @Override
    public void payFail() {

    }
});

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    MFangHelper.getInstance(this).onActivityResult(this, requestCode, resultCode, data);
}

//log方法
MFangHelper.getInstance(this).enableLog(true);//打印log
```

