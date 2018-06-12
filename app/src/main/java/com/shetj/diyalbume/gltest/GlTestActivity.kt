package com.shetj.diyalbume.gltest

import android.app.ActivityManager
import android.content.Context
import android.opengl.GLES10.glClear
import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import android.os.Build
import android.os.Bundle
import me.shetj.base.base.BaseActivity
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class GlTestActivity : BaseActivity () {


    private lateinit var glSurfaceView: GLSurfaceView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (checkSupported()) {
            glSurfaceView = GLSurfaceView(this)
            glSurfaceView.setEGLContextClientVersion(2);
            glSurfaceView.setRenderer(object : GLSurfaceView.Renderer {
                override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {

                    glViewport(0, 0, width, height);
                }

                override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
//                    当Surface被创建的时候，GLSurfaceView会调用这个方法，这发生在应用程序创建的第一次，并且当设备被唤醒或者用户从其他activity切换回去时，也会被调用。
                   //和 onResume 很想

                    glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
                }

                override fun onDrawFrame(gl: GL10?) {
//                    在画图之前，一定要把纸给清理干净喽：
                    glClear(GL_COLOR_BUFFER_BIT) //清空屏幕
                }
            })
            setContentView(glSurfaceView)
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
        }
    }

    override fun onResume() {
        super.onResume()
                if (rendererSet) {
            glSurfaceView.onResume()
        }
    }

}
