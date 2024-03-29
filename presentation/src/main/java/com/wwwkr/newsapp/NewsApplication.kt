package com.wwwkr.newsapp

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NewsApplication : Application() {
    init {
        instance = this
    }

    companion object {
        lateinit var instance: NewsApplication
    }

}