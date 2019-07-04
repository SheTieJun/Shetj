package com.shetj.diyalbume.main.view

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.shetj.diyalbume.R

class CustomTabsHelper {

    companion object {
        fun openUrl(context: Context, url: String) {
                val builder = CustomTabsIntent.Builder()
                builder.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
                builder.build().launchUrl(context, Uri.parse(url))
        }
    }

}