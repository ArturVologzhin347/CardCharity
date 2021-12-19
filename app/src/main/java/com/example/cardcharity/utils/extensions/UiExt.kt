package com.example.cardcharity.utils.extensions

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.Window
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior


fun dpToPx(context: Context, dp: Int): Int {
    return (dp * context.resources.displayMetrics.density).toInt()
}

fun pxToDp(context: Context, px: Int): Int {
    val resources: Resources = context.resources
    val metrics: DisplayMetrics = resources.displayMetrics
    return (px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
}

@Suppress("DEPRECATION")
fun AppCompatActivity.setStatusBarColor(@ColorInt color: Int) {
    window.statusBarColor = color

    if (!color.forWhite()) {
        return
    }

    when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.R ->
            window.insetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )

        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
            window.decorView.apply {
                systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or systemUiVisibility
            }
        }

        else ->
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    }
}


@Suppress("DEPRECATION")
fun Window.setTranslucentStatus() {
    setFlags(
        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
    )
}

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

val Dialog.behavior: BottomSheetBehavior<FrameLayout>
    get() {
        val bottomSheet = findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        return BottomSheetBehavior.from(bottomSheet as FrameLayout)
    }

