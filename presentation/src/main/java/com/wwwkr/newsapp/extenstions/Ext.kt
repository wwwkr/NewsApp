package com.wwwkr.newsapp.extenstions

import android.content.Context
import android.widget.Toast
import com.wwwkr.newsapp.NewsApplication

var toast: Toast? = null

fun toast(msg: String) {

    if(toast == null) {
        toast = Toast(NewsApplication.instance.applicationContext)
    }else {
        toast?.cancel()
    }

    toast?.apply {
        setText(msg)
        duration = Toast.LENGTH_SHORT
        show()
    }

}


fun Int.toStringRes(): String = NewsApplication.instance.applicationContext.getString(this)
