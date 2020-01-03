package com.shetj.diyalbume

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger

/**
 * newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。

newFixedThreadPool创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。

newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。

newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。

 */
class ThreadTest{

    val  mDiskIO: ExecutorService = Executors.newFixedThreadPool(4,object :ThreadFactory{
        val THREAD_NAME = "diskIO_thread_%d"

        val  threadId = AtomicInteger(0)

        override fun newThread(r: Runnable): Thread {
            return  Thread().also {
                it.name = String.format(THREAD_NAME,threadId.getAndIncrement())
            }
        }
    })

    val cacheThread = Executors.newCachedThreadPool(object :ThreadFactory{
        val THREAD_NAME = "cacheThread_%d"

        val  threadId = AtomicInteger(0)

        override fun newThread(r: Runnable): Thread {
            return  Thread().also {
                it.name = String.format(THREAD_NAME,threadId.getAndIncrement())
            }
        }
    })



    val singleThread = Executors.newSingleThreadExecutor( object :ThreadFactory{
        val THREAD_NAME = "singleThread_%d"

        val  threadId = AtomicInteger(0)

        override fun newThread(r: Runnable): Thread {
            return  Thread().also {
                it.name = String.format(THREAD_NAME,threadId.getAndIncrement())
            }
        }
    })

    val scheduledThread = Executors.newScheduledThreadPool(4,object :ThreadFactory{
        val THREAD_NAME = "scheduledThread_%d"

        val  threadId = AtomicInteger(0)

        override fun newThread(r: Runnable): Thread {
            return  Thread().also {
                it.name = String.format(THREAD_NAME,threadId.getAndIncrement())
            }
        }
    })

    fun test(){
        mDiskIO.execute{

        }

        mDiskIO.submit{

        }

        cacheThread.execute {

        }

        val future = singleThread.submit {

        }

        scheduledThread.submit {

        }
    }
}