

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

5. 创建类

```
class Ablum(var describe : String ,
            var preview : String ,
            var ablumJson :String) {
}

```

6. 创建方法

```
   open fun toJson(): String? {
            return GsonKit.objectToJson(this)
        }
```

7. 字符拼接 $

```
 return "Album(describe='$describe', preview='$preview', albumInfoJson='$albumInfoJson')"
```

8. 实现接口

```
class Link(var type: Int,
           var content:String) :MultiItemEntity {
    val VIDEO = 1
    val URL = 2
    val SOUND = 3

    override fun getItemType(): Int {
    }
}
```
9. 反射
```
    implementation "org.jetbrains.kotlin:kotlin-reflect:$kotlin_version"
```