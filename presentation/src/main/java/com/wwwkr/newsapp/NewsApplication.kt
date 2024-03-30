package com.wwwkr.newsapp

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NewsApplication : Application() {
    init {
        instance = this
    }

    companion object {
        lateinit var instance: NewsApplication
    }

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}