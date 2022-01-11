package com.example.cardcharity.utils.extensions

import android.content.Context
import android.graphics.ColorMatrixColorFilter
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat

fun Context.drawable(@DrawableRes drawableResId: Int): Drawable {
    return ResourcesCompat.getDrawable(resources, drawableResId, null)
        ?: throw NullPointerException("Drawable cannot be null")
}

fun Drawable.setColor(@ColorInt color: Int) {
    val red = ((color and 0xFF0000) / 0xFFFF).toFloat()
    val green = ((color and 0xFF00) / 0xFF).toFloat()
    val blue = (color and 0xFF).toFloat()

    floatArrayOf(
        0F, 0F, 0F, 0F, red,
        0F, 0F, 0F, 0F, green,
        0F, 0F, 0F, 0F, blue,
        0F, 0F, 0F, 1F, 0F
    ).let {
        colorFilter = ColorMatrixColorFilter(it)
    }
}
