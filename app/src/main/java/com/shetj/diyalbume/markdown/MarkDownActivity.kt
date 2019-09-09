package com.shetj.diyalbume.markdown

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shetj.diyalbume.R
import kotlinx.android.synthetic.main.activity_mark_down.*
import kotlinx.android.synthetic.main.ijkplayer_control_view.*

/**
 * [MarkdownView]
 */
class MarkDownActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mark_down)
//        markdownView?.loadMarkdownFile("https://github.com/falnatsheh/MarkdownView/blob/master/README.md")
        markdownView.loadMarkdownFile("file:///android_asset/设计模式.md")
//        markdownView.loadMarkdownFile("http://www.my-site.com/myFile.md");
//        markdownView.loadMarkdownFile("file:///android_asset/hello.md","file:///android_asset/MyCustomTheme.css");
    }
}
