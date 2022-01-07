package com.example.cardcharity.utils.extensions

import android.content.Context
import com.example.cardcharity.App
import com.example.cardcharity.di.Dagger2
import com.example.cardcharity.repository.network.Api
import okhttp3.OkHttpClient

val Context.dagger: Dagger2
    get() = when (this) {
        is App -> dagger
        else -> applicationContext.dagger
    }

val Context.api: Api
    get() = when (this) {
        is App -> api
        else -> applicationContext.api
    }

val Context.okHttpClient: OkHttpClient
    get() = when (this) {
        is App -> okHttpClient
        else -> applicationContext.okHttpClient
    }
