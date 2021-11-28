package com.example.cardcharity.utils

import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity

fun Window.setScreenBrightness(brightness: Float) {
    this.attributes = this.attributes.apply { screenBrightness = brightness }//TODO
}

fun Window.getBrightness(): Float {
    return this.attributes.screenBrightness
}

@Suppress("DEPRECATION")
fun hideNavigationBar(activity: AppCompatActivity) {
    activity.window.decorView.systemUiVisibility =
        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
}

