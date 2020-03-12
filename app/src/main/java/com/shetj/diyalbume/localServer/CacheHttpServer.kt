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
import java.nio.charset.StandardCharsets

/**
 * 缓存本地服务器
 */
class CacheHttpServer : HttpServerRequestCallback {
    //缓存
    private val path: String = SDCardUtils.getPath("TYPE_DOWNLOAD_CACHE")
    //服务器实列
    private val server = AsyncHttpServer()
    private val KEY_CACHE_TMEP = ".s"
    /**
     * 开启本地服务
     */
    fun startServer() {
        //如果有其他的请求方式，例如下面一行代码的写法
        server.addAction("OPTIONS", "[\\d\\D]*", this)
        server["[\\d\\D]*", this]
        server.post("[\\d\\D]*", this)
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
        if (TextUtils.isEmpty(path)) {
            response.send("sd卡没有找到")
            return
        }
        val file = File(param)
        var stream: BufferedInputStream? = null
        var inputStream: FileInputStream? = null
        try {
            if (file.exists()) {
                //获取本地文件的输入流
                inputStream = FileInputStream(file)
                var length = file.length()
                //只有加密文件才走加密
                if (param.endsWith(KEY_CACHE_TMEP)) {
                    val bytes = ByteArray(11)
                    inputStream.read(bytes, 0, bytes.size)
                    length -= 11
                }
                response.setContentType("APPLICATION/OCTET-STREAM")
                response.headers["Content-Disposition"] = "attachment;filename=" + String(file.name.toByteArray(), StandardCharsets.ISO_8859_1)
                response.headers["Content-Length"] = "" + length
                response.sendStream(BufferedInputStream(inputStream, 64000), length)
            } else {
                response.code(404)
            }
        } catch (e: IOException) {
            response.end()
            e.printStackTrace()
        }
    }

    companion object {
        private var mInstance: CacheHttpServer? = null

        //端口
        private const val PORT_LISTEN_DEFAULT = 5050
        private var BASE_URL = "http://127.0.0.1:$PORT_LISTEN_DEFAULT/path/"
        //单列模式
        val instance: CacheHttpServer
            get() {
                return mInstance?: synchronized(CacheHttpServer::class.java) {
                            CacheHttpServer().also { mInstance = it }
                    }
            }

        fun getUri(MediaUrl:String): Uri? {
            val code = File(URI(MediaUrl).path).path
            //后面的参数必须进行Unicode编码，防止中文乱码
            return Uri.parse("$BASE_URL$code")
        }
    }
}