# Kotlin与Java语音变化

1. 创建对象

```text
java  new Man();
kotlin  Man()
```

1. Android Studio报UNEXPECTED TOP-LEVEL EXCEPTION错误解决办法

   ```text
   dexOptions {
    javaMaxHeapSize "4g"
   }
   }
   ```

2. View 相关

```text
toolbarTitle.text = "主页"
findViewById<TextView>(R.id.toolbar_title)
```

1. 变量相关

   ```text
   val 不可变
   var 可变的
   用val或var声明变量，指明它们是不可变的或可变的
   ```

2. 创建类

```text
class Ablum(var describe : String ,
            var preview : String ,
            var ablumJson :String) {
}
```

1. 创建方法

```text
   open fun toJson(): String? {
            return GsonKit.objectToJson(this)
        }
```

1. 字符拼接 $

```text
 return "Album(describe='$describe', preview='$preview', albumInfoJson='$albumInfoJson')"
```

1. 实现接口

```text
class Link(var type: Int,
           var content:String) :MultiItemEntity {
    val VIDEO = 1
    val URL = 2
    val SOUND = 3

    override fun getItemType(): Int {
    }
}
```

1. 反射

   ```text
    implementation "org.jetbrains.kotlin:kotlin-reflect:$kotlin_version"
   ```

