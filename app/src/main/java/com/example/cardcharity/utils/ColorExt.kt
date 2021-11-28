package com.example.cardcharity.utils

import android.content.Context
import android.graphics.Color
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import com.google.android.material.color.MaterialColors

private const val ALPHA_CHANNEL = 24
private const val RED_CHANNEL = 16
private const val GREEN_CHANNEL = 8
private const val BLUE_CHANNEL = 0


@ColorInt
fun Context.attr(@AttrRes attrResId: Int, @ColorInt defColor: Int = Color.CYAN): Int {
    return MaterialColors.getColor(this, attrResId, defColor)
}

@ColorInt
fun Int.mixWith(
    @ColorInt foreground: Int,
    @FloatRange(from = 0.0, to = 1.0) ratio: Float
): Int {
    return mixColors(this, foreground, ratio)
}

@ColorInt
fun mixColors(
    @ColorInt background: Int,
    @ColorInt foreground: Int,
    @FloatRange(from = 0.0, to = 1.0) ratio: Float
): Int {
    val inverseRatio: Float = 1.0f - ratio
    val a = ((background shr ALPHA_CHANNEL and 0xff).toFloat() * inverseRatio +
            (foreground shr ALPHA_CHANNEL and 0xff).toFloat() * ratio).toInt() and 0xff
    val r = ((background shr RED_CHANNEL and 0xff).toFloat() * inverseRatio +
            (foreground shr RED_CHANNEL and 0xff).toFloat() * ratio).toInt() and 0xff
    val g = ((background shr GREEN_CHANNEL and 0xff).toFloat() * inverseRatio +
            (foreground shr GREEN_CHANNEL and 0xff).toFloat() * ratio).toInt() and 0xff
    val b = ((background and 0xff).toFloat() * inverseRatio +
            (foreground and 0xff).toFloat() * ratio).toInt() and 0xff

    return a shl ALPHA_CHANNEL or (r shl RED_CHANNEL) or (g shl GREEN_CHANNEL) or (b shl BLUE_CHANNEL)
}