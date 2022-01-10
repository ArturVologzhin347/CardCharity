package com.example.cardcharity.repository.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class CacheInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        chain.proceed(chain.request()).apply {
            val cacheControl = header("Cache-Control")
            return when {
                cacheControl == null ||
                        cacheControl.contains("no-store") ||
                        cacheControl.contains("no-cache") ||
                        cacheControl.contains("must-validate") ||
                        cacheControl.contains("max=age=0") -> {
                    this.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + 5000)
                        .build();
                }
                else -> this

            }
        }
    }
}
