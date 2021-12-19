package com.example.cardcharity.utils.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import kotlin.reflect.KClass


fun <T : Any> openActivity(context: Context, clazz: KClass<T>) {
    clazz.openActivity(context)
}

fun Any.openActivity(context: Context) {
    this::class.openActivity(context)
}

fun <T : Any> KClass<T>.openActivity(context: Context) {
    val i = Intent(context, this.java)
    context.startActivity(i)
}

fun AppCompatActivity.openSettings() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    val uri: Uri = Uri.fromParts("package", packageName, null)
    intent.data = uri
    startActivity(intent)
}
