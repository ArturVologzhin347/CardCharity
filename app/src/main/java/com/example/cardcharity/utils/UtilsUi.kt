package com.example.cardcharity.utils

import android.view.Window

fun Window.setScreenBrightness(brightness: Float) {
    val layout = this.attributes
    layout.screenBrightness = brightness
    this.attributes = layout
}

fun Window.getBrightness(): Float {
    return this.attributes.screenBrightness
}