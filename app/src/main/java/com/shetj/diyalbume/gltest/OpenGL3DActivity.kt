package com.shetj.diyalbume.gltest

import android.opengl.GLES10.glRotatef
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import com.alibaba.android.arouter.facade.annotation.Route
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import me.shetj.base.base.BasePresenter
import me.shetj.base.qmui.BaseQMUIActivity
import timber.log.Timber
@Route(path = "/shetj/OpenGL3DActivity")
class OpenGL3DActivity : BaseQMUIActivity<BasePresenter<*>>() {
    override fun getContextViewId(): Int {
        return 0
    }

    private lateinit var glSurfaceView: GLSurfaceView

    private lateinit var glRenderer: GLRenderer

    private lateinit var publishSubject: PublishSubject<Long>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (checkSupported()) {
            glSurfaceView = GLSurfaceView(this)
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

    private lateinit var gestureDetector: GestureDetector

    override fun initView() {
        val ges =object : GestureDetector.SimpleOnGestureListener() {
            override fun onShowPress(e: MotionEvent?) {
                Timber.i("onShowPress : x = ${e?.x}        y = ${e?.y}")
            }

            override fun onSingleTapUp(e: MotionEvent?): Boolean {
                Timber.i("onSingleTapUp : x = ${e?.x}        y = ${e?.y}")
                return super.onSingleTapUp(e)
            }

            override fun onDown(e: MotionEvent?): Boolean {
                Timber.i("onDown : x = ${e?.x}        y = ${e?.y}")
                return super.onDown(e)
            }

            override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
                Timber.i("onFling : x1 = ${e1?.x}        y1 = ${e1?.y}")
                Timber.i("onFling : x2 = ${e1?.x}        y2 = ${e1?.y}")
                Timber.i("onFling : velocityX =  $velocityX")
                Timber.i("onFling : velocityY = $velocityY")

                return super.onFling(e1, e2, velocityX, velocityY)
            }

            override fun onLongPress(e: MotionEvent?) {
                Timber.i("onLongPress : x = ${e?.x}        y = ${e?.y}")
            }

            override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
//                glRenderer.rotate(distanceX)
                Timber.i("onScroll : x1 = ${e1?.x}        y1 = ${e1?.y}")
                Timber.i("onScroll : x2 = ${e1?.x}        y2 = ${e1?.y}")
                Timber.i("onScroll : distanceX = $distanceX")
                Timber.i("onScroll : distanceY = $distanceY")
                rotateDegreenx = (rotateDegreenx + distanceX).toLong()
//                glRotatef(rotateDegreenx.toFloat(), 1.0f, 0.0f, 0.0f)//绕x轴旋转
//                rotateDegreeny = (rotateDegreeny + distanceY).toLong()
//                    glRotatef(rotateDegreeny.toFloat(), 0.0f, 1.0f, 0.0f)//绕Y轴旋转
                glRenderer.rotate(rotateDegreenx.toFloat())
                glRotatef(rotateDegreeny+distanceY,0f,1f,0f)
                glSurfaceView.invalidate()
                return super.onScroll(e1,e2,distanceX, distanceY)
            }

            override fun onDoubleTap(e: MotionEvent?): Boolean {
                Timber.i("onDoubleTap : x = ${e?.x}        y = ${e?.y}")
                return super.onDoubleTap(e)
            }

            override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
                Timber.i("onDoubleTapEvent : x = ${e?.x}        y = ${e?.y}")
                return super.onDoubleTapEvent(e)
            }

            override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
                Timber.i("onSingleTapConfirmed : x = ${e?.x}        y = ${e?.y}")
                return super.onSingleTapConfirmed(e)
            }

            override fun onContextClick(e: MotionEvent?): Boolean {
                Timber.i("onContextClick : x = ${e?.x}        y = ${e?.y}")
                return super.onContextClick(e)
            }

        }

        gestureDetector = GestureDetector(this,ges)


    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        gestureDetector.onTouchEvent(ev)
        return super.dispatchTouchEvent(ev)
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

    private var rotateDegreenx: Long = 0
    private var rotateDegreeny: Long = 0
    private var disposable: Disposable? = null




    override fun onResume() {
        super.onResume()
        if (rendererSet) {
            glSurfaceView.onResume()
//            disposable = Flowable.interval(100, 5, TimeUnit.MILLISECONDS)
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe {
//                        val l = rotateDegreen + it
//                        glRenderer.rotate(l.toFloat())
//
//                    }
        }
    }

}
