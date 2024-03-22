package com.wwwkr.newsapp.common

import android.content.Context
import android.content.Intent
import android.net.Uri

object Utils {

    fun openInBrowser(url: String, context: Context) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }

}