package com.example.cardcharity.utils.extensions

import android.view.View
import androidx.annotation.IdRes


fun <T: View> View.find(@IdRes id: Int): T {
    return findViewById(id)
}