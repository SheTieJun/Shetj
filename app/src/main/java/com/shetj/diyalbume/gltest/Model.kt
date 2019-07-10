package com.shetj.diyalbume.gltest

import java.nio.FloatBuffer

class Model {
    //三角面个数
    var facetCount: Int = 0
    //顶点坐标数组
    private var verts: FloatArray? = null
    //每个顶点对应的法向量数组
    private var vnorms: FloatArray? = null
    //每个三角面的属性信息
    var remarks: ShortArray? = null

    //顶点数组转换而来的Buffer
    var vertBuffer: FloatBuffer? = null
        private set

    //每个顶点对应的法向量转换而来的Buffer
    var vnormBuffer: FloatBuffer? = null
        private set
    //以下分别保存所有点在x,y,z方向上的最大值、最小值
    internal var maxX: Float = 0.toFloat()
    internal var minX: Float = 0.toFloat()
    internal var maxY: Float = 0.toFloat()
    internal var minY: Float = 0.toFloat()
    internal var maxZ: Float = 0.toFloat()
    internal var minZ: Float = 0.toFloat()

    //返回模型的中心点
    //注意，下载的源码中，此函数修改修正如下
    val centrePoint: Point
        get() {

            val cx = minX + (maxX - minX) / 2
            val cy = minY + (maxY - minY) / 2
            val cz = minZ + (maxZ - minZ) / 2
            return Point(cx, cy, cz)
        }

    //包裹模型的最大半径
    val r: Float
        get() {
            val dx = maxX - minX
            val dy = maxY - minY
            val dz = maxZ - minZ
            var max = dx
            if (dy > max)
                max = dy
            if (dz > max)
                max = dz
            return max
        }

    //设置顶点数组的同时，设置对应的Buffer
    fun setVerts(verts: FloatArray) {
        this.verts = verts
        vertBuffer = OpenGLUtils.floatToBuffer(verts)
    }

    //设置顶点数组法向量的同时，设置对应的Buffer
    fun setVnorms(vnorms: FloatArray) {
        this.vnorms = vnorms
        vnormBuffer = OpenGLUtils.floatToBuffer(vnorms)
    }
}