package com.wwwkr.newsapp.extenstions

import android.content.Context
import android.widget.Toast

var toast: Toast? = null

fun Context.toast(msg: String) {

    if(toast == null) {
        toast = Toast(this)
    }else {
        toast?.cancel()
    }

    toast?.apply {
        setText(msg)
        duration = Toast.LENGTH_SHORT
        show()
    }

}
