package com.shetj.diyalbume.gltest

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

/**
 * Package com.hc.opengl
 * Created by HuaChao on 2016/7/28.
 */
object OpenGLUtils {

    fun floatToBuffer(a: FloatArray): FloatBuffer {
        //先初始化buffer，数组的长度*4，因为一个float占4个字节
        val bb = ByteBuffer.allocateDirect(a.size * 4)
        //数组排序用nativeOrder
        bb.order(ByteOrder.nativeOrder())
        val buffer = bb.asFloatBuffer()
        buffer.put(a)
        buffer.position(0)
        return buffer
    }

    fun byte4ToInt(bytes: ByteArray, offset: Int): Int {
        val b3 = bytes[offset + 3].toInt() and 0xFF
        val b2 = bytes[offset + 2].toInt() and 0xFF
        val b1 = bytes[offset + 1].toInt() and 0xFF
        val b0 = bytes[offset + 0].toInt() and 0xFF
        return (b3 shl  24) or (b2 shl 16) or (b1 shl 8) or b0
    }

    fun byte2ToShort(bytes: ByteArray, offset: Int): Short {
        val b1 = bytes[offset + 1].toInt() and 0xFF
        val b0 = bytes[offset + 0].toInt() and 0xFF
        return (b1 shl 8 or b0).toShort()
    }

    fun byte4ToFloat(bytes: ByteArray, offset: Int): Float {
        return java.lang.Float.intBitsToFloat(byte4ToInt(bytes, offset))
    }

}