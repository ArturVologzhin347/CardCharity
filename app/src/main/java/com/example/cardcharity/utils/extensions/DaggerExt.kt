package com.example.cardcharity.utils.extensions

import android.content.Context
import com.example.cardcharity.App
import com.example.cardcharity.di.Dagger2
import com.example.cardcharity.domain.auth.Authorization
import com.example.cardcharity.repository.network.Api
import okhttp3.OkHttpClient

val Context.dagger: Dagger2
    get() = app.dagger

val Context.api: Api
    get() = app.api

val Context.authorization: Authorization
    get() = app.authorization

val Context.okHttpClient: OkHttpClient
    get() = app.okHttpClient

private val Context.app: App
    get() = when (this) {
        is App -> this
        else -> applicationContext.app
    }
