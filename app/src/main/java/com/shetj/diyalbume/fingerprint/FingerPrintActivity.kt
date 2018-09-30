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
        fpv.setOnStateChangedListener(FingerPrinterView.OnStateChangedListener { state ->
            if (state == FingerPrinterView.STATE_CORRECT_PWD) {
                ArmsUtils.makeText("指纹识别成功")
                onBackPressed()
                return@OnStateChangedListener
            }
            if (state == FingerPrinterView.STATE_WRONG_PWD) {
                ArmsUtils.makeText("指纹识别失败，请重试")
                fpv.state = FingerPrinterView.STATE_NO_SCANING
            }
        })
        rxFingerPrinter =  RxFingerPrinter(this)

        val observer = object : DisposableObserver<Boolean>() {

            override fun onStart() {
                if (fpv.state == FingerPrinterView.STATE_SCANING) {
                    return
                } else if (fpv.state == FingerPrinterView.STATE_CORRECT_PWD || fpv.state == FingerPrinterView.STATE_WRONG_PWD) {
                    fpv.state = FingerPrinterView.STATE_NO_SCANING
                } else {
                    fpv.state = FingerPrinterView.STATE_SCANING
                }
            }

            override fun onError(e: Throwable) {
                if (e is FPerException) {
                    Toast.makeText(this@FingerPrintActivity, e.displayMessage, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onComplete() {
            }

            override fun onNext(aBoolean: Boolean) {
                if (aBoolean) {
                    fpv.state = FingerPrinterView.STATE_CORRECT_PWD
                } else {
                    fpv.state = FingerPrinterView.STATE_WRONG_PWD
                }
            }
        }



        bt_open.setOnClickListener {
            rxFingerPrinter.begin()
                    .compose(bindToLifecycle())
                    .subscribe(observer)
        }

        btn_sys.setOnClickListener{
        }
    }



}
