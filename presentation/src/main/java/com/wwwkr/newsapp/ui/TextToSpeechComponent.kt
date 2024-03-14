package com.wwwkr.newsapp.ui

import android.annotation.SuppressLint
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import java.util.Locale

@SuppressLint("ComposableNaming")
@Composable
fun TextToSpeech(): TextToSpeech {

    val context = LocalContext.current

    return remember(context) {
        TextToSpeech(context) { state ->
            if (state != TextToSpeech.SUCCESS) {
                Toast.makeText(context, "TTS 객체 초기화 중 문제가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        }.apply {
            language = Locale.KOREAN
        }
    }
}