

1. 创建对象

```
java  new Man();
kotlin  Man()
```

2. Android Studio报UNEXPECTED TOP-LEVEL EXCEPTION错误解决办法
```
dexOptions {
    javaMaxHeapSize "4g"
}
}
```

3. View 相关

```
toolbarTitle.text = "主页"
findViewById<TextView>(R.id.toolbar_title)
```

4. 变量相关
```
val 不可变
var 可变的
用val或var声明变量，指明它们是不可变的或可变的
```