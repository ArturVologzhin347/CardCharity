package com.example.cardcharity.utils.extensions

import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity

fun MenuItem.view(activity: AppCompatActivity): View {
    return activity.findViewById(this.itemId)
}