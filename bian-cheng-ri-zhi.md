# 编程日志

## 2018年11月2日

1. kotlin 扩展函数学习使用
2. kotlin 扩展函数的一些工具封装

```text
合并commit

1. git rebase -i HEAD~2

2. 进入文件进行修改
pick 的意思是要会执行这个 commit
squash 的意思是这个 commit 会被合并到前一个commit

3.ESc 退出编辑  ：wq 保存退出  i 进入编辑模式

4. git rebase --abort 撤销修改
```

单例写法

```text
    companion object {
        // For Singleton instantiation.
        @Volatile
        private var browserManager :MediaBrowserManager ?=null

        fun getInstance() = browserManager ?: synchronized(this) {
            browserManager?: MediaBrowserManager()
                .also { browserManager = it }
        }
    }
```

