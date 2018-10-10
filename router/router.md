####  路由
https://github.com/alibaba/ARouter/

https://github.com/alibaba/ARouter/blob/master/README_CN.md

##### 使用
```
  //阿里路由
   classpath "com.alibaba:arouter-register:1.0.2"
```
```
    apply plugin: 'com.alibaba.arouter'
```

```
    api 'com.alibaba:arouter-api:1.4.0'
    annotationProcessor  'com.alibaba:arouter-compiler:1.2.1'
```

```
// 在支持路由的页面上添加注解(必选)
// 这里的路径需要注意的是至少需要有两级，/xx/xx
@Route(path = "/test/activity")
public class YourActivity extend Activity {
    ...
}
```
```
/ 构建标准的路由请求
ARouter.getInstance().build("/home/main").navigation();

// 构建标准的路由请求，并指定分组
ARouter.getInstance().build("/home/main", "ap").navigation();

// 构建标准的路由请求，通过Uri直接解析
Uri uri;
ARouter.getInstance().build(uri).navigation();

// 构建标准的路由请求，startActivityForResult
// navigation的第一个参数必须是Activity，第二个参数则是RequestCode
ARouter.getInstance().build("/home/main", "ap").navigation(this, 5);

// 直接传递Bundle
Bundle params = new Bundle();
ARouter.getInstance()
	.build("/home/main")
	.with(params)
	.navigation();

// 指定Flag
ARouter.getInstance()
	.build("/home/main")
	.withFlags();
	.navigation();

// 获取Fragment
Fragment fragment = (Fragment) ARouter.getInstance().build("/test/fragment").navigation();

// 对象传递
ARouter.getInstance()
	.withObject("key", new TestObj("Jack", "Rose"))
	.navigation();

// 觉得接口不够多，可以直接拿出Bundle赋值
ARouter.getInstance()
	    .build("/home/main")
	    .getExtra();

// 转场动画(常规方式)
ARouter.getInstance()
    .build("/test/activity2")
    .withTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom)
    .navigation(this);

// 转场动画(API16+)
ActivityOptionsCompat compat = ActivityOptionsCompat.
    makeScaleUpAnimation(v, v.getWidth() / 2, v.getHeight() / 2, 0, 0);

// ps. makeSceneTransitionAnimation 使用共享元素的时候，需要在navigation方法中传入当前Activity

ARouter.getInstance()
	.build("/test/activity2")
	.withOptionsCompat(compat)
	.navigation();

// 使用绿色通道(跳过所有的拦截器)
ARouter.getInstance().build("/home/main").greenChannel().navigation();

// 使用自己的日志工具打印日志
ARouter.setLogger();

// 使用自己提供的线程池
ARouter.setExecutor();
```
```
在使用路由的base中使用
ARouter.getInstance().inject(this);
```



#### 生成路由文档

```
android {
    defaultConfig {
        ...
        javaCompileOptions {
            annotationProcessorOptions {
                arguments = [AROUTER_MODULE_NAME: project.getName(), AROUTER_GENERATE_DOC: "enable"]
            }
        }
    }
}
```

#####
```
shetj://me.shetj.router
```


```
          TestSerializable testSerializable = new TestSerializable("Titanic", 555);
                TestParcelable testParcelable = new TestParcelable("jack", 666);
                TestObj testObj = new TestObj("Rose", 777);
                List<TestObj> objList = new ArrayList<>();
                objList.add(testObj);

                Map<String, List<TestObj>> map = new HashMap<>();
                map.put("testMap", objList);

                ARouter.getInstance().build("/test/activity1")
                        .withString("name", "老王")
                        .withInt("age", 18)
                        .withBoolean("boy", true)
                        .withLong("high", 180)
                        .withString("url", "https://a.b.c")
                        .withSerializable("ser", testSerializable)
                        .withParcelable("pac", testParcelable)
                        .withObject("obj", testObj)
                        .withObject("objList", objList)
                        .withObject("map", map)
                        .navigation();
```