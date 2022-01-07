package com.example.cardcharity.utils.extensions

import android.content.Context
import android.content.res.Resources
import android.os.Build.VERSION_CODES

import android.os.Build.VERSION
import kotlin.math.ceil
import android.util.DisplayMetrics





fun Context.getStatusBarHeight(): Int {
    val resourceId: Int = resources.getIdentifier("status_bar_height", "dimen", "android")
    return if (resourceId > 0)
        resources.getDimensionPixelSize(resourceId).toDp(this)
    else ceil(
        (if (VERSION.SDK_INT >= VERSION_CODES.M) 24 else 25) * resources.displayMetrics.density
    ).toInt().toDp(this)
}


fun Int.toDp(context: Context): Int {
    return this / (context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
}