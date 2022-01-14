package com.example.cardcharity.utils.extensions

import android.content.Context
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.util.DisplayMetrics
import android.view.Window
import androidx.annotation.FloatRange
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import kotlin.math.ceil

fun Window.setScreenBrightness(@FloatRange(from = 0.0, to = 1.0) brightness: Float) {
    this.attributes = attributes.apply { screenBrightness = brightness }
}

fun Window.getBrightness(): Float {
    return this.attributes.screenBrightness
}

fun Context.getStatusBarHeight(): Int =
    getSystemDimen("status_bar_height")

fun Context.getNavigationBarHeight(): Int =
    getSystemDimen("navigation_bar_height")


fun Context.getSystemDimen(name: String): Int {
    val resourceId: Int = resources.getIdentifier(name, "dimen", "android")
    return if (resourceId > 0)
        resources.getDimensionPixelSize(resourceId).toDp(this)
    else ceil(
        (if (VERSION.SDK_INT >= VERSION_CODES.M) 24 else 25) * resources.displayMetrics.density
    ).toInt().toDp(this)
}


fun Int.toDp(context: Context): Int =
    this / (context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)


inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }) { onClick() }
}