package com.shetj.diyalbume.executors

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.jakewharton.rxbinding2.view.RxView
import com.shetj.diyalbume.R
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_executors.*

@Route(path = "/shetj/ExecutorsActivity")
class ExecutorsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_executors)
        RxView.clicks(btn_test).subscribe {
            testExcutors()
        }
    }

    private fun testExcutors() {
        val executorService = ExecutorsPool(5, false)
        for (i in 0..19) {
            var priorityRunnable = PriorityRunnable(ExecutorsPool.Priority.NORMAL, Runnable {
                Flowable.just("${Thread.currentThread().name}NORMAL\n")
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            tv_msg.append(it)
                        }
            })

            if (i % 3 == 1) {
                priorityRunnable = PriorityRunnable(ExecutorsPool.Priority.HIGH,Runnable {
                    Flowable.just("${Thread.currentThread().name}HIGH\n")
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe {
                                tv_msg.append(it)
                            }
                })
            } else if (i % 5 == 0) {
                priorityRunnable =PriorityRunnable(ExecutorsPool.Priority.LOW,Runnable {
                    Flowable.just("${Thread.currentThread().name}LOW\n")
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe {
                                tv_msg.append(it)
                            }
                })
            }
            executorService.execute(priorityRunnable)
        }


    }
}
