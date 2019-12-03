package com.shetj.diyalbume.camera

import android.Manifest
import android.graphics.Matrix
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.view.Surface
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.extensions.BokehImageCaptureExtender
import com.shetj.diyalbume.R
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_camerax.*
import me.shetj.base.base.BaseActivity
import me.shetj.base.base.BasePresenter
import me.shetj.base.kt.hasPermission
import me.shetj.base.tools.app.ArmsUtils
import timber.log.Timber
import java.io.File
import java.util.concurrent.Executor

/**
 * androidx camera2 test
 */
class CameraxActivity : BaseActivity<BasePresenter<*>>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camerax)
    }

    override fun initData() {

    }

    override fun initView() {

        val hasPermission = hasPermission(Manifest.permission.CAMERA)
        if (hasPermission) {
            texture_view.post {
                //使用`viewFinder.post{.}’来确保在调用‘startCamera()’时view 已经展示。
                startCamera()
            }
        }else{
            RxPermissions(this).request(Manifest.permission.CAMERA)
                    .subscribe {
                        if (it){
                            startCamera()
                        }else{
                            finish()
                        }
                    }
        }
    }

    private fun startCamera(){
        //创建预览配置
        val previewConfig = PreviewConfig.Builder().apply {
//            setTargetAspectRatio(AspectRatio.RATIO_16_9)
            setTargetResolution(Size(ArmsUtils.getScreenWidth(rxContext),ArmsUtils.getScreenHeight(rxContext)))
        }.build()

        val preview = Preview(previewConfig)
        preview.setOnPreviewOutputUpdateListener {
            //更新渲染视图，先移除在添加
            val parent = texture_view.parent as ViewGroup
            parent.removeView(texture_view)
            parent.addView(texture_view,0)
            texture_view.surfaceTexture = it.surfaceTexture
            // Every time the provided texture view changes, recompute layout
            texture_view.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
                updateTransform()
            }

        }


        // 拍照-Add this before CameraX.bindToLifecycle
        val imageCaptureConfig = ImageCaptureConfig.Builder()
                .apply {
                    // Create a Extender object which can be used to apply extension
                    // configurations.
                    val bokehImageCapture = BokehImageCaptureExtender.create(this)
                    // Query if extension is available (optional).
                    if (bokehImageCapture.isExtensionAvailable) {
                        Timber.i("bokehImageCapture.isExtensionAvailable")
                        // Enable the extension if available.
                        bokehImageCapture.enableExtension()
                    }
                    setTargetAspectRatio(AspectRatio.RATIO_16_9)
                    setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
                }.build()




        // Build the image capture use case and attach button click listener
        val imageCapture = ImageCapture(imageCaptureConfig)
        capture_button.setOnClickListener {
            val file = File(externalMediaDirs.first(),
                    "${System.currentTimeMillis()}.jpg")
            imageCapture.takePicture(file, {

            },
                    object : ImageCapture.OnImageSavedListener {
                        override fun onError(imageCaptureError: ImageCapture.ImageCaptureError, message: String, cause: Throwable?) {
                            val msg = "Photo capture failed: $message"
                            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                            Log.e("CameraXApp", msg)
                            cause?.printStackTrace()
                        }


                        override fun onImageSaved(file: File) {
                            val msg = "Photo capture succeeded: ${file.absolutePath}"
                            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                            Log.d("CameraXApp", msg)
                        }
                    })
        }

        CameraX.bindToLifecycle(this,preview,imageCapture)
    }

    private fun updateTransform() {

        val matrix = Matrix()

        val centerX = texture_view.width/2f
        val centerY = texture_view.height/2f

        val rotationDegrees = when(texture_view.display.rotation){
            Surface.ROTATION_0 ->0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 ->180
            Surface.ROTATION_270 ->270
            else ->return
        }

        matrix.postRotate(-rotationDegrees.toFloat(),centerX,centerY)

        texture_view.setTransform(matrix)
    }
}
