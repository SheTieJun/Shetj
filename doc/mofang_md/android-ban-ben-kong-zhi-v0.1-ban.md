# Android 版本控制V0.1版

## Android 版本命名规则-版本控制 \#\#

```text
 versionCode 1 //版本code
 versionName "1.0.0.0" //版本名称
```

### 版本命名规则 \#\#\#

**versionCode**

版本号表示app发行次数，以及版本更新次数

* 只有每次正式发布APP  **versionCode+1** ，否则保持不变;。

**versionName**

版本名的形式是major.minor.maintenance.build \(级别从高到底\)

* 大版本更新（如一期，二期），发布版本，**major+1** ，低于**major** 全部置0，同时**versionCode+1**
* 如果只进行小功能功能、模块添加，发布版本，**minor+1**，低于**minor** 全部置0，同时**versionCode+1**
* 因为BUG更新的发布版本 **maintenance+1**，低于**maintenance** 全部置0，同时**versionCode+1**
* 内部测试版本只更改 **build+1**，同时**versionCode** 保存不变

### 规则 \#\#\#

#### versionCode：

此值必须是递增变大的整数，为了比较是否需要更新。

常见的有两种命名方式：

* 1、时间命名法，直接采用发布当前的日期，比如versioncode:20170527 
* 2、版本控制提交号，此方式便于回滚到对应版本的源代码。
* 注：魔方**versionCode**，代表主要代表发行次数，以及版本更新次数

#### versionName：  \#\#\#\#

此字段的版本名是用户可以看到的，常见的命名方式如下：

版本名的形式是major.minor.maintenance.build

* major 是主版本号，一般在软件有重大升级时增长 
* minor 是次版本号，一般在软件有新功能时增长 
* maintenance 是维护版本，一般在软件有主要的问题修复后增长 
* build 构建版本（测试版本一般会用到） 

介绍

* 正式版本：major.minor.maintenance—-1.0.0.0 
* 测试版本：major.minor.maintenance.build—-1.0.0.5
* 主版本号：功能模块有大的变动，比如增加多个模块或者整体架构发生变化。
* 次版本号：和主版本相对而言，次版本号的升级对应的只是局部的变动。但该局部的变动造成了程序和以前版本不能兼容，或者对该程序以前的协作关系产生了破坏，或者是功能上有大的改进或增强。

## SVN代码提交-版本控制 \#\#

1、如果多人开发，至少一天更新提交一次；单人开发至少一周一次，或者3天一次，放假前必须提交一次并且注明更改；

2、如果是功能提交，需要写明完成是什么功能；

3、如果修复BUG提交，需要注明注明BUG对应的issue Id;并注明 是Fix 或者 只是部分并没有修复

4、尽量不要一次多功能，多模块的代码提交。尽量单模块提交，单功能提交，方便出问题时查找比对对应代码。

### ----------待完善 \#\#\#

参考文档：

* [Android应用版本命名规范](http://blog.csdn.net/u012308094/article/details/72780302)
* [关于SVN代码提交粒度和频率的思考](https://www.cnblogs.com/zhanjindong/p/think-about-svn-commit-granularity-and-frequency.html)

