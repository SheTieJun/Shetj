

1、创建对象

```
java  new Man();
kotlin  Man()
```

2、Android Studio报UNEXPECTED TOP-LEVEL EXCEPTION错误解决办法
```
dexOptions {
    javaMaxHeapSize "4g"
}
}
```