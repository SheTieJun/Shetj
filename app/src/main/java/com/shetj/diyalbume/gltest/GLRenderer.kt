package com.shetj.diyalbume.gltest

import android.content.Context
import android.opengl.GLSurfaceView
import android.opengl.GLU

import java.io.IOException

import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class GLRenderer(context: Context) : GLSurfaceView.Renderer {

    private var model: Model? = null
    private var mCenterPoint: Point? = null
    private val eye = Point(0f, 0f, -3f)
    private val up = Point(0f, 1f, 0f)
    private val center = Point(0f, 0f, 0f)
    private var mScalef = 1f
    private var mDegree = 0f


    internal var materialAmb = floatArrayOf(0.4f, 0.4f, 1.0f, 1.0f)
    internal var materialDiff = floatArrayOf(0.0f, 0.0f, 1.0f, 1.0f)
    internal var materialSpec = floatArrayOf(1.0f, 0.5f, 0.0f, 1.0f)
    internal var ambient = floatArrayOf(0.9f, 0.9f, 0.9f, 1.0f)
    internal var diffuse = floatArrayOf(0.5f, 0.5f, 0.5f, 1.0f)
    internal var specular = floatArrayOf(1.0f, 1.0f, 1.0f, 1.0f)
    internal var lightPosition = floatArrayOf(0.5f, 0.5f, 0.5f, 0.0f)

    init {
        try {

            model = STLReader().parserBinStlInAssets(context, "snow.STL")
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun rotate(degree: Float) {
        mDegree = degree
    }

    override fun onDrawFrame(gl: GL10) {
        // 清除屏幕和深度缓存
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT or GL10.GL_DEPTH_BUFFER_BIT)


        gl.glLoadIdentity()// 重置当前的模型观察矩阵


        //眼睛对着原点看
        GLU.gluLookAt(gl, eye.x, eye.y, eye.z, center.x,
                center.y, center.z, up.x, up.y, up.z)

        //为了能有立体感觉，通过改变mDegree值，让模型不断旋转
        gl.glRotatef(mDegree, 0f, 1f, 0f)

        //将模型放缩到View刚好装下
        gl.glScalef(mScalef, mScalef, mScalef)
        //把模型移动到原点
        gl.glTranslatef(-mCenterPoint!!.x, -mCenterPoint!!.y,
                -mCenterPoint!!.z)


        //===================begin==============================//

        //允许给每个顶点设置法向量
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY)
        // 允许设置顶点
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY)
        // 允许设置颜色

        //设置法向量数据源
        gl.glNormalPointer(GL10.GL_FLOAT, 0, model!!.vnormBuffer)
        // 设置三角形顶点数据源
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, model!!.vertBuffer)

        // 绘制三角形
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, model!!.facetCount * 3)

        // 取消顶点设置
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY)
        //取消法向量设置
        gl.glDisableClientState(GL10.GL_NORMAL_ARRAY)

        //=====================end============================//

    }


    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {

        // 设置OpenGL场景的大小,(0,0)表示窗口内部视口的左下角，(width, height)指定了视口的大小
        gl.glViewport(0, 0, width, height)

        gl.glMatrixMode(GL10.GL_PROJECTION) // 设置投影矩阵
        gl.glLoadIdentity() // 设置矩阵为单位矩阵，相当于重置矩阵
        GLU.gluPerspective(gl, 45.0f, width.toFloat() / height, 1f, 100f)// 设置透视范围

        //以下两句声明，以后所有的变换都是针对模型(即我们绘制的图形)
        gl.glMatrixMode(GL10.GL_MODELVIEW)
        gl.glLoadIdentity()


    }

    override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
        gl.glEnable(GL10.GL_DEPTH_TEST) // 启用深度缓存
        gl.glClearDepthf(1.0f) // 设置深度缓存值
        gl.glDepthFunc(GL10.GL_LEQUAL) // 设置深度缓存比较函数
        gl.glShadeModel(GL10.GL_SMOOTH)// 设置阴影模式GL_SMOOTH

        openLight(gl)
        enableMaterial(gl)
        val r = model!!.r
        //r是半径，不是直径，因此用0.5/r可以算出放缩比例
        mScalef = 0.5f / r
        mCenterPoint = model!!.centrePoint

    }

    fun openLight(gl: GL10) {

        gl.glEnable(GL10.GL_LIGHTING)
        gl.glEnable(GL10.GL_LIGHT0)
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, OpenGLUtils.floatToBuffer(ambient))
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, OpenGLUtils.floatToBuffer(diffuse))
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, OpenGLUtils.floatToBuffer(specular))
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, OpenGLUtils.floatToBuffer(lightPosition))

    }

    fun enableMaterial(gl: GL10) {

        //材料对环境光的反射情况
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, OpenGLUtils.floatToBuffer(materialAmb))
        //散射光的反射情况
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, OpenGLUtils.floatToBuffer(materialDiff))
        //镜面光的反射情况
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, OpenGLUtils.floatToBuffer(materialSpec))

    }
}