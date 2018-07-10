package com.shetj.diyalbume.gltest

<<<<<<< HEAD
import android.opengl.GLES10.glRotatef
import android.opengl.GLES10.glTranslatef
=======
>>>>>>> d8fb7fcaf6fa1a9b06029de7f404d5affc1e08c7
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import me.shetj.base.base.BasePresenter
import me.shetj.base.base.BaseSwipeBackActivity
import org.xutils.common.util.LogUtil

class OpenGL3DActivity : BaseSwipeBackActivity<BasePresenter<*>>() {

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

    private lateinit var gestureDetector: GestureDetector

    override fun initView() {
        val ges =object : GestureDetector.SimpleOnGestureListener() {
            override fun onShowPress(e: MotionEvent?) {
                LogUtil.i("onShowPress : x = ${e?.x}        y = ${e?.y}")
            }

            override fun onSingleTapUp(e: MotionEvent?): Boolean {
                LogUtil.i("onSingleTapUp : x = ${e?.x}        y = ${e?.y}")
                return super.onSingleTapUp(e)
            }

            override fun onDown(e: MotionEvent?): Boolean {
                LogUtil.i("onDown : x = ${e?.x}        y = ${e?.y}")
                return super.onDown(e)
            }

            override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
                LogUtil.i("onFling : x1 = ${e1?.x}        y1 = ${e1?.y}")
                LogUtil.i("onFling : x2 = ${e1?.x}        y2 = ${e1?.y}")
                LogUtil.i("onFling : velocityX =  $velocityX")
                LogUtil.i("onFling : velocityY = $velocityY")

                return super.onFling(e1, e2, velocityX, velocityY)
            }

            override fun onLongPress(e: MotionEvent?) {
                LogUtil.i("onLongPress : x = ${e?.x}        y = ${e?.y}")
            }

            override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
//                glRenderer.rotate(distanceX)
                LogUtil.i("onScroll : x1 = ${e1?.x}        y1 = ${e1?.y}")
                LogUtil.i("onScroll : x2 = ${e1?.x}        y2 = ${e1?.y}")
                LogUtil.i("onScroll : distanceX = $distanceX")
                LogUtil.i("onScroll : distanceY = $distanceY")
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
                LogUtil.i("onDoubleTap : x = ${e?.x}        y = ${e?.y}")
                return super.onDoubleTap(e)
            }

            override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
                LogUtil.i("onDoubleTapEvent : x = ${e?.x}        y = ${e?.y}")
                return super.onDoubleTapEvent(e)
            }

            override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
                LogUtil.i("onSingleTapConfirmed : x = ${e?.x}        y = ${e?.y}")
                return super.onSingleTapConfirmed(e)
            }

            override fun onContextClick(e: MotionEvent?): Boolean {
                LogUtil.i("onContextClick : x = ${e?.x}        y = ${e?.y}")
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
