package com.example.cardcharity.repository.network

import com.example.cardcharity.repository.network.api.BarcodeApi
import com.example.cardcharity.repository.network.api.ShopApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NetworkService private constructor() {
    private val BASE_URL = "http://10d0-82-151-196-167.ngrok.io"

    private val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(interceptor)
    }.build()


    private var retrofit = Retrofit.Builder().apply {
        baseUrl(BASE_URL)
        addConverterFactory(GsonConverterFactory.create())
        client(okHttpClient)
    }.build()


    fun getShopApi(): ShopApi {
        return retrofit.create(ShopApi::class.java)
    }

    fun getBarcodeApi(): BarcodeApi {
        return retrofit.create(BarcodeApi::class.java)
    }

    companion object {
        private var mInstance: NetworkService? = null

        val instance: NetworkService
            get() {
                if(mInstance == null) {
                    mInstance = NetworkService()
                }
                return mInstance!!
            }

    }
}