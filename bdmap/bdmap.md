#### 使用定位
Application:
```
BMapManager.init(this);
```

```
/**
 *
 * @param SendTag 位置 还是地图（传递的参数有点点不同）
 * @param isOnly  是否只定一次
 */
 public void getLocation(BDMapLocation.SendTag SendTag,boolean isOnly) {
   BDMapLocation.getInstance(view.getRxContext()).setOption(isOnly);
   //开始定位
   BDMapLocation.getInstance(view.getRxContext()).start(SendTag);
 }
```