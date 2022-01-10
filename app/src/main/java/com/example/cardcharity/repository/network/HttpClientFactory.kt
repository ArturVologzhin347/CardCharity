package com.example.cardcharity.repository.network


import android.content.Context
import com.example.cardcharity.BuildConfig
import com.example.cardcharity.repository.network.interceptor.CacheInterceptor
import com.example.cardcharity.repository.network.interceptor.OfflineCacheInterceptor
import com.example.cardcharity.utils.extensions.setDebugMode
import com.example.cardcharity.utils.extensions.setupResponsesCache
import com.example.cardcharity.utils.extensions.toUnsafe
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject

class HttpClientFactory @Inject constructor(private val context: Context) {

    fun buildHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().setDebugMode(BuildConfig.DEBUG_MODE)
        val offlineCacheInterceptor = OfflineCacheInterceptor(context)
        val cacheInterceptor = CacheInterceptor()

        return OkHttpClient.Builder().apply {
            addInterceptor(loggingInterceptor)
            //setupResponsesCache(context, RESPONSES_CACHE_SIZE)
            //addNetworkInterceptor(cacheInterceptor)
            //addInterceptor(offlineCacheInterceptor)
            toUnsafe()
        }.build()
    }


    companion object {
        private const val RESPONSES_CACHE_SIZE = 10L * 1024L * 1024L // 10 MiB
    }
}
