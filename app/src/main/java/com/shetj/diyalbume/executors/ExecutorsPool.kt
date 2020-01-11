package com.shetj.diyalbume.executors

import java.util.*
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong

/**
 * **@packageName：** com.shetj.diyalbume.executors<br></br>
 * **@author：** shetj<br></br>
 * **@createTime：** 2018/7/18 0018<br></br>
 * **@company：**<br></br>
 * **@email：** 375105540@qq.com<br></br>
 * **@describe**<br></br>
 */
class ExecutorsPool(corePoolSize: Int, maximumPoolSize: Int, keepAliveTime: Long, unit: TimeUnit, workQueue: BlockingQueue<Runnable>, threadFactory: ThreadFactory) :
        ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory) {
    /**
     * 判断当前线程池是否繁忙
     * @return
     */
    val isBusy: Boolean
        get() = activeCount >= corePoolSize

    /**
     * 默认工作线程数5
     *
     * @param fifo 优先级相同时, 等待队列的是否优先执行先加入的任务.
     */
    constructor(fifo: Boolean) : this(CORE_POOL_SIZE, fifo) {}

    /**
     * @param poolSize 工作线程数
     * @param fifo     优先级相同时, 等待队列的是否优先执行先加入的任务.
     */
    constructor(poolSize: Int, fifo: Boolean) : this(poolSize,
            MAXIMUM_POOL_SIZE, KEEP_ALIVE.toLong(),
            TimeUnit.SECONDS, PriorityBlockingQueue<Runnable>(MAXIMUM_POOL_SIZE,
            if (fifo) FIFO else LIFO), sThreadFactory) {}

    /**
     * 提交任务
     * @param runnable
     */
    override fun execute(runnable: Runnable) {
        if (runnable is PriorityRunnable) {
            runnable.SEQ = SEQ_SEED.getAndIncrement()
        }
        super.execute(runnable)
    }


    /**
     * 线程优先级
     */
    enum class Priority {
        HIGH, NORMAL, LOW
    }

    companion object {


        private val CORE_POOL_SIZE = 5//核心线程池大小
        private val MAXIMUM_POOL_SIZE = 256//最大线程池队列大小
        private val KEEP_ALIVE = 1//保持存活时间，当线程数大于corePoolSize的空闲线程能保持的最大时间。
        private val SEQ_SEED = AtomicLong(0)//主要获取添加任务


        /**
         * 线程队列方式 先进先出
         */
        private val FIFO = Comparator<Runnable> { lhs, rhs ->
            if (lhs is PriorityRunnable && rhs is PriorityRunnable) {
                val result = lhs.priority.ordinal - rhs.priority.ordinal
                if (result == 0) (lhs.SEQ - rhs.SEQ).toInt() else result
            } else {
                0
            }
        }

        /**
         * 线程队列方式 后进先出
         */
        private val LIFO = Comparator<Runnable> { lhs, rhs ->
            if (lhs is PriorityRunnable && rhs is PriorityRunnable) {
                val result = lhs.priority.ordinal - rhs.priority.ordinal
                if (result == 0) (rhs.SEQ - lhs.SEQ).toInt() else result
            } else {
                0
            }
        }

        /**
         * 创建线程工厂
         */
        private val sThreadFactory = object : ThreadFactory {
            private val mCount = AtomicInteger(1)

            override fun newThread(runnable: Runnable): Thread {
                return Thread(runnable, "download#" + mCount.getAndIncrement())
            }
        }
    }


}
