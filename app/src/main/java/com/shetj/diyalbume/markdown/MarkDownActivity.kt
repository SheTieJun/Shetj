package com.shetj.diyalbume.markdown

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shetj.diyalbume.R
import kotlinx.android.synthetic.main.activity_mark_down.*

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
