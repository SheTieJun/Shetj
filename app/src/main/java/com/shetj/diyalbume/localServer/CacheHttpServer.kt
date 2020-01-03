package com.shetj.diyalbume.localServer

import android.net.Uri
import android.text.TextUtils
import com.koushikdutta.async.http.server.AsyncHttpServer
import com.koushikdutta.async.http.server.AsyncHttpServerRequest
import com.koushikdutta.async.http.server.AsyncHttpServerResponse
import com.koushikdutta.async.http.server.HttpServerRequestCallback
import me.shetj.base.tools.file.SDCardUtils
import timber.log.Timber
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.net.URI

/**
 * 缓存本地服务器
 */
class CacheHttpServer : HttpServerRequestCallback {
    //缓存
    private val path: String = SDCardUtils.getPath("TYPE_DOWNLOAD_CAHE")
    //服务器实列
    private val server = AsyncHttpServer()
    private val KEY_CACHE_TMEP = ".s"
    /**
     * 开启本地服务
     */
    fun startServer() { //如果有其他的请求方式，例如下面一行代码的写法
        server.addAction("OPTIONS", "[\\d\\D]*", this)
        server["[\\d\\D]*", this]
        server.post("[\\d\\D]*", this)
        //服务器监听端口
        server.listen(PORT_LISTEN_DEFAULT)
    }

    /**
     * 停止本地服务
     */
    fun stopServer() {
        server.stop()
    }

    override fun onRequest(request: AsyncHttpServerRequest, response: AsyncHttpServerResponse) { //unicode转换
        val param = request.path.replace("/path/", "")
        //获取传入的参数
        Timber.d("onRequest: $param  $param")
        //这个是获取header参数的地方，一定要谨记
        val headers = request.headers.multiMap
        // 获取本地的存文件的目录
//获取文件路径
        if (TextUtils.isEmpty(path)) {
            response.send("sd卡没有找到")
            return
        }
        val file = File(param)
        var stream: BufferedInputStream? = null
        var inputStream: FileInputStream? = null
        try {
            if (file.exists()) { //获取本地文件的输入流
                inputStream = FileInputStream(file)
                //只有加密文件才走加密
                if (param.endsWith(KEY_CACHE_TMEP)) {
                    val bytes = ByteArray(11)
                    inputStream.read(bytes, 0, bytes.size)
                }
                //出去干扰字符的流
                stream = BufferedInputStream(inputStream)
                //写出没有干扰字符的流
                response.sendStream(stream, stream.available().toLong())
            } else {
                response.code(404)
            }
        } catch (e: IOException) {
            response.end()
            e.printStackTrace()
        }
    }

    companion object {
        private const val TAG = "CacheHttpServer"
        //当前类的实列 采用用单列
        private var mInstance: CacheHttpServer? = null
        //端口
        private const val PORT_LISTEN_DEFAULT = 5050
        var BASE_URL = "http://127.0.0.1:$PORT_LISTEN_DEFAULT/path/"
        //单列模式
        val instance: CacheHttpServer?
            get() {
                if (mInstance == null) {
                    synchronized(CacheHttpServer::class.java) {
                        if (mInstance == null) {
                            mInstance = CacheHttpServer()
                        }
                    }
                }
                return mInstance
            }


        fun getUri(MediaUrl:String): Uri? {
            val code = File(URI(MediaUrl).path).path
            //后面的参数必须进行Unicode编码，防止中文乱码
            return Uri.parse("$BASE_URL$code")
        }
    }
}