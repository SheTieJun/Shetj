package com.shetj.diyalbume.executors

import com.shetj.diyalbume.executors.ExecutorsPool.Priority

/**
 * 带有优先级的Runnable类型
 */

class PriorityRunnable(priority: Priority?, /*
		 *  任务真正执行者
		 */
                       private val runnable: Runnable) : Runnable {
    /*
		 *  任务优先级
		 */
    val priority: Priority
    /*
		 * 任务唯一标示
		 */
    internal var SEQ: Long = 0


    init {
        this.priority = priority ?: Priority.NORMAL
    }

    override fun run() {
        this.runnable.run()
    }
}