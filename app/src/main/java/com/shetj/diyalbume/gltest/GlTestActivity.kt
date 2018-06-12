package com.shetj.diyalbume.gltest

import android.opengl.GLSurfaceView
import android.os.Bundle
import me.shetj.base.base.BaseActivity
import me.shetj.base.base.BasePresenter
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


class GlTestActivity : BaseActivity<BasePresenter<*>> (){
    private val mTriangleArray =
            //x,y,z  y =1
            floatArrayOf(-1f, 1f, 0f,
                    -1f, -1f, 0f,
                    1f, -1f, 0f,
                    1f, 1f, 0f,
                    -1f, 1f, 0f,
                    1f, -1f, 0f)
    private val mColor =
            floatArrayOf(1f, 0f, 0f, 1f,
                    0f, 1f, 0f, 1f,
                    0f, 0f, 1f, 1f)
    private var mTriangleBuffer: FloatBuffer? = null
    private var mColorBuffer: FloatBuffer? = null
    private lateinit var glSurfaceView: GLSurfaceView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mTriangleBuffer =  Util.floatToBuffer(mTriangleArray)
        mColorBuffer = Util.floatToBuffer(mColor)

        if (checkSupported()) {
            glSurfaceView = GLSurfaceView(this)
//            glSurfaceView.setEGLContextClientVersion(2)
            setContentView(glSurfaceView)
            glSurfaceView.setRenderer(object : GLSurfaceView.Renderer {
                override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {

                    val ratio = width.toFloat() / height
                    // 设置OpenGL场景的大小,(0,0)表示窗口内部视口的左下角，(w,h)指定了视口的大小
                    gl.glViewport(0, 0, width, height)
                    // 设置投影矩阵
                    gl.glMatrixMode(GL10.GL_PROJECTION)
                    // 重置投影矩阵
                    gl.glLoadIdentity()
                    // 设置视口的大小
                    gl.glFrustumf(-ratio, ratio, -1f, 1f, 1f, 10f)
                    //以下两句声明，以后所有的变换都是针对模型(即我们绘制的图形)
                    gl.glMatrixMode(GL10.GL_MODELVIEW)
                    gl.glLoadIdentity()

                }

                override fun onSurfaceCreated(gl: GL10, config: EGLConfig?) {
//                    当Surface被创建的时候，GLSurfaceView会调用这个方法，这发生在应用程序创建的第一次
//                  ，并且当设备被唤醒或者用户从其他activity切换回去时，也会被调用。
                    //和 onResume 很想
                    gl.glClearColor(1f, 0f, 1f, 0f)
                }

                override fun onDrawFrame(gl: GL10) {
                    // 清除屏幕和深度缓存
                    gl.glClear(GL10.GL_COLOR_BUFFER_BIT or GL10.GL_DEPTH_BUFFER_BIT)
                    // 重置当前的模型观察矩阵
                    gl.glLoadIdentity()

                    // 允许设置顶点
                    //GL10.GL_VERTEX_ARRAY顶点数组
                    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY)
                    // 允许设置颜色
                    //GL10.GL_COLOR_ARRAY颜色数组
                    gl.glEnableClientState(GL10.GL_COLOR_ARRAY)

                    //将三角形在z轴上移动
                    gl.glTranslatef(0f, 0.0f, -3f)

                    // 设置三角形
                    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mTriangleBuffer)
                    // 设置三角形颜色
                    gl.glColorPointer(4, GL10.GL_FLOAT, 0, mColorBuffer)
                    // 绘制三角形
                    gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 6)




                    // 取消颜色设置
                    gl.glDisableClientState(GL10.GL_COLOR_ARRAY)
                    // 取消顶点设置
                    gl.glDisableClientState(GL10.GL_VERTEX_ARRAY)

                    //绘制结束
                    gl.glFinish()

                }
            })

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
