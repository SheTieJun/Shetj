package com.shetj.diyalbume.gltest

import android.opengl.GLSurfaceView
import android.os.Bundle
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import me.shetj.base.base.BaseSwipeBackActivity
import org.intellij.lang.annotations.Flow
import java.util.concurrent.TimeUnit

class OpenGL3DActivity : BaseSwipeBackActivity() {

    private lateinit var glSurfaceView: GLSurfaceView

    private lateinit var glRenderer: GLRenderer

    private lateinit var publishSubject: PublishSubject<Long>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (checkSupported()) {
            glSurfaceView = GLSurfaceView(this)
//            glSurfaceView.setEGLContextClientVersion(2)
            setContentView(glSurfaceView)
            glRenderer = GLRenderer(this)
            glSurfaceView.setRenderer(glRenderer)
            rendererSet = true
            initView()
            initData()
        }else{
            finish()
        }


    }


    private fun checkSupported() : Boolean {
        return GLUtils.checkSupported(this)
    }

    override fun initView() {


    }

    override fun initData() {

    }

    private var rendererSet: Boolean = false

    override fun onPause() {
        super.onPause()
        if (rendererSet) {
            glSurfaceView.onPause()
            if (disposable != null && !disposable?.isDisposed!!) {
                disposable!!.dispose()
            }
        }
    }

    private var rotateDegreen: Long = 0

    private var disposable: Disposable? = null


    override fun onResume() {
        super.onResume()
        if (rendererSet) {
            glSurfaceView.onResume()
            disposable = Flowable.interval(100, 5, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        val l = rotateDegreen + it
                        glRenderer.rotate(l.toFloat())
                        glSurfaceView.invalidate()
                    }
        }
    }

}
