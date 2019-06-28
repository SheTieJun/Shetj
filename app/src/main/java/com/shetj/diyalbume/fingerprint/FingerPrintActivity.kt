package com.shetj.diyalbume.fingerprint

import android.os.Bundle
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.shetj.diyalbume.R
import io.reactivex.observers.DisposableObserver
import kotlinx.android.synthetic.main.activity_finger_print.*
import me.shetj.base.base.BaseActivity
import me.shetj.base.base.BasePresenter
import me.shetj.base.tools.app.ArmsUtils
import me.shetj.fingerprinter.FPerException
import me.shetj.fingerprinter.RxFingerPrinter
import timber.log.Timber


/**
 * 指纹
 */
@Route(path = "/shetj/FingerPrintActivity")
class FingerPrintActivity : BaseActivity<BasePresenter<*>>() {
    override fun initView() {
    }

    override fun initData() {
    }
    private lateinit var rxFingerPrinter: RxFingerPrinter
    private var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finger_print)
        rxFingerPrinter =  RxFingerPrinter(this)

        val observer = object : DisposableObserver<Int>() {

            override fun onStart() {
            }

            override fun onError(e: Throwable) {
                if (e is FPerException) {
                    Toast.makeText(this@FingerPrintActivity, e.displayMessage, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onComplete() {
            }

            override fun onNext(type: Int) {
                Timber.i(type.toString())
               when(type){
                   0 ->{
                        ArmsUtils.makeText("验证失败！")
                   }
                   1 ->{
                       ArmsUtils.makeText("验证成功！")
                   }
                   else->{
                        finish()
                   }
               }
            }
        }
        rxFingerPrinter.init()
                .compose(bindToLifecycle())
                .subscribe(observer)


        bt_open.setOnClickListener {
            rxFingerPrinter.start()
        }

        btn_sys.setOnClickListener{
        }
    }



}
