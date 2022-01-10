package com.example.cardcharity.repository.network.interceptor

import android.content.Context
import com.example.cardcharity.utils.extensions.isNetworkAvailable
import okhttp3.Interceptor
import okhttp3.Response

class OfflineCacheInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if (isNetworkAvailable(context)) {
            request = request.newBuilder().apply {
                removeHeader("Pragma")
                header("Cache-Control", "public, only-if-cached")
            }.build()
        }

        return chain.proceed(request)
    }
}