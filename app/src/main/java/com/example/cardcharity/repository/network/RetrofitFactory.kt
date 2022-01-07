package com.example.cardcharity.repository.network

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

class RetrofitFactory @Inject constructor(
    val url: String,
    val okHttpClient: OkHttpClient
) {

    fun buildRetrofit(): Retrofit {
        val moshi = Moshi.Builder().build()
        val converterFactory = buildConverterFactory(moshi)

        return Retrofit.Builder().apply {
            client(okHttpClient)
            addConverterFactory(converterFactory)
            baseUrl(url)
        }.build()
    }

    private fun buildConverterFactory(moshi: Moshi): Converter.Factory {
        return MoshiConverterFactory.create(moshi).apply {
            asLenient()
            withNullSerialization()
        }
    }
}
