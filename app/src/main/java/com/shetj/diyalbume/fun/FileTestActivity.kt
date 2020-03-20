package com.shetj.diyalbume.`fun`

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Environment.DIRECTORY_MUSIC
import com.shetj.diyalbume.R
import kotlinx.android.synthetic.main.activity_file_test.*
import me.shetj.base.tools.app.Utils
import me.shetj.base.tools.file.SDCardUtils

class FileTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_test)
        title = "文件管理"
        tv_1.text = " Environment.getExternalStorageState() = \n${ Environment.getExternalStorageState()}"
        tv_2.text = " Utils.app.getExternalFilesDir(DIRECTORY_MUSIC)!!.absolutePath =\n ${  Utils.app.getExternalFilesDir(DIRECTORY_MUSIC)!!.absolutePath}"
        tv_3.text = " SDCardUtils.rootDirectoryPath = \n${ SDCardUtils.rootDirectoryPath}"
        tv_4.text = " SDCardUtils.filesDir =\n ${ SDCardUtils.filesDir}"
        tv_5.text = " SDCardUtils.cache（Utils.app.cacheDir.absolutePath） = \n${ SDCardUtils.cache}"
        tv_5.text = " SDCardUtils.sdCardPath （ Environment.getExternalStorageDirectory().absolutePath）= \n${ SDCardUtils.sdCardPath}"
    }
}
