# 简单记录知识

**allowbackup的作用**

当为true时，可以通过adb备份数据

## AS打包输出

```text
libraryVariants.all { variant ->
    variant.outputs.all { output ->
        def outputFile = output.outputFile
        if (outputFile != null && outputFile.name.endsWith('.aar')) {
            def fileName = outputFile.name.replace(".aar", "_${defaultConfig.versionName}.aar")
            outputFileName = fileName
        }
    }
}



applicationVariants.all { variant ->
    variant.outputs.all { output ->
        def outputFile = output.outputFile
        if (outputFile != null && outputFile.name.endsWith('.apk')) {
            def fileName = outputFile.name.replace(".apk", "-${releaseTime()}-${defaultConfig.versionName}.apk")
            outputFileName = fileName
        }
    }
}
```

### 学习设计模式

### 学习绘制view \#\#\#

