package com.shetj.diyalbume.gltest

import android.content.Context

import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream

import me.shetj.base.s

class STLReader {
    private val stlLoadListener: StlLoadListener? = null

    @Throws(IOException::class)
    fun parserBinStlInSDCard(path: String): Model {

        val file = File(path)
        val fis = FileInputStream(file)
        return parserBinStl(fis)
    }

    @Throws(IOException::class)
    fun parserBinStlInAssets(context: Context, fileName: String): Model {

        return parserBinStl(s.app.applicationContext.assets.open(fileName))
    }

    //解析二进制的Stl文件
    @Throws(IOException::class)
    fun parserBinStl(`in`: InputStream): Model {
        stlLoadListener?.onstart()
        val model = Model()
        //前面80字节是文件头，用于存贮文件名；
        `in`.skip(80)

        //紧接着用 4 个字节的整数来描述模型的三角面片个数
        val bytes = ByteArray(4)
        `in`.read(bytes)// 读取三角面片个数
        val facetCount = OpenGLUtils.byte4ToInt(bytes, 0)
        model.facetCount = facetCount
        if (facetCount == 0) {
            `in`.close()
            return model
        }

        // 每个三角面片占用固定的50个字节
        val facetBytes = ByteArray(50 * facetCount)
        // 将所有的三角面片读取到字节数组
        `in`.read(facetBytes)
        //数据读取完毕后，可以把输入流关闭
        `in`.close()


        parseModel(model, facetBytes)


        stlLoadListener?.onFinished()
        return model
    }

    /**
     * 解析模型数据，包括顶点数据、法向量数据、所占空间范围等
     */
    private fun parseModel(model: Model, facetBytes: ByteArray) {
        val facetCount = model.facetCount
        /**
         * 每个三角面片占用固定的50个字节,50字节当中：
         * 三角片的法向量：（1个向量相当于一个点）*（3维/点）*（4字节浮点数/维）=12字节
         * 三角片的三个点坐标：（3个点）*（3维/点）*（4字节浮点数/维）=36字节
         * 最后2个字节用来描述三角面片的属性信息
         */
        // 保存所有顶点坐标信息,一个三角形3个顶点，一个顶点3个坐标轴
        val verts = FloatArray(facetCount * 3 * 3)
        // 保存所有三角面对应的法向量位置，
        // 一个三角面对应一个法向量，一个法向量有3个点
        // 而绘制模型时，是针对需要每个顶点对应的法向量，因此存储长度需要*3
        // 又同一个三角面的三个顶点的法向量是相同的，
        // 因此后面写入法向量数据的时候，只需连续写入3个相同的法向量即可
        val vnorms = FloatArray(facetCount * 3 * 3)
        //保存所有三角面的属性信息
        val remarks = ShortArray(facetCount)

        var stlOffset = 0
        try {
            for (i in 0 until facetCount) {
                stlLoadListener?.onLoading(i, facetCount)
                for (j in 0..3) {
                    val x = OpenGLUtils.byte4ToFloat(facetBytes, stlOffset)
                    val y = OpenGLUtils.byte4ToFloat(facetBytes, stlOffset + 4)
                    val z = OpenGLUtils.byte4ToFloat(facetBytes, stlOffset + 8)
                    stlOffset += 12

                    if (j == 0) {//法向量
                        vnorms[i * 9] = x
                        vnorms[i * 9 + 1] = y
                        vnorms[i * 9 + 2] = z
                        vnorms[i * 9 + 3] = x
                        vnorms[i * 9 + 4] = y
                        vnorms[i * 9 + 5] = z
                        vnorms[i * 9 + 6] = x
                        vnorms[i * 9 + 7] = y
                        vnorms[i * 9 + 8] = z
                    } else {//三个顶点
                        verts[i * 9 + (j - 1) * 3] = x
                        verts[i * 9 + (j - 1) * 3 + 1] = y
                        verts[i * 9 + (j - 1) * 3 + 2] = z

                        //记录模型中三个坐标轴方向的最大最小值
                        if (i == 0 && j == 1) {
                            model.maxX = x
                            model.minX = model.maxX
                            model.maxY = y
                            model.minY = model.maxY
                            model.maxZ = z
                            model.minZ = model.maxZ
                        } else {
                            model.minX = Math.min(model.minX, x)
                            model.minY = Math.min(model.minY, y)
                            model.minZ = Math.min(model.minZ, z)
                            model.maxX = Math.max(model.maxX, x)
                            model.maxY = Math.max(model.maxY, y)
                            model.maxZ = Math.max(model.maxZ, z)
                        }
                    }
                }
                val r = OpenGLUtils.byte2ToShort(facetBytes, stlOffset)
                stlOffset = stlOffset + 2
                remarks[i] = r
            }
        } catch (e: Exception) {
            if (stlLoadListener != null) {
                stlLoadListener.onFailure(e)
            } else {
                e.printStackTrace()
            }
        }

        //将读取的数据设置到Model对象中
        model.setVerts(verts)
        model.setVnorms(vnorms)
        model.remarks = remarks

    }

    interface StlLoadListener {
        fun onstart()

        fun onLoading(cur: Int, total: Int)

        fun onFinished()

        fun onFailure(e: Exception)
    }
}