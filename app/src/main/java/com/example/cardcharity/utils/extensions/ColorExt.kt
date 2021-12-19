package com.example.cardcharity.utils.extensions

import android.content.Context
import android.graphics.Color
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import androidx.core.graphics.ColorUtils
import com.google.android.material.color.MaterialColors
import kotlin.math.roundToInt

private const val ALPHA_CHANNEL = 24
private const val RED_CHANNEL = 16
private const val GREEN_CHANNEL = 8
private const val BLUE_CHANNEL = 0

private const val defaultColorID = android.R.color.black
private const val defaultColor = "000000"


fun Int.forWhite(): Boolean {
    return ColorUtils.calculateContrast(Color.WHITE, this) < 4.5
}

fun Int.forBlack(): Boolean {
    return ColorUtils.calculateContrast(Color.BLACK, this) < 4.5
}

@ColorInt
fun Context.attr(@AttrRes attrResId: Int, @ColorInt defColor: Int = Color.CYAN): Int {
    return MaterialColors.getColor(this, attrResId, defColor)
}

@ColorInt
fun Int.transparent(@IntRange(from = 0, to = 100) alpha: Int): Int {
    return Color.parseColor(transparentColor(this, alpha))
}

private fun transparentColor(colorInt: Int, @IntRange(from = 0, to = 100) alpha: Int): String {
    var color = defaultColor
    try {
        // convert color code into hex a string and remove starting 2 digit
        color = Integer.toHexString(colorInt).uppercase().substring(2)
    } catch (ignored: Exception) {
        //Ignored
    }
    return if (color.isNotEmpty() && alpha < 100) {
        if (color.trim { it <= ' ' }.length == 6) {
            "#" + convert(alpha) + color
        } else {
            convert(alpha) + color
        }
        // if color is empty or any other problem occur then we return default color
    } else "#" + Integer.toHexString(defaultColorID).uppercase().substring(2)
}

private fun convert(@IntRange(from = 0, to = 100) alpha: Int): String {
    val hexString = Integer.toHexString((255 * alpha / 100).toFloat().roundToInt())
    return "${if (hexString.length < 2) "0" else ""}$hexString"
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