package com.example.cardcharity.di.module

import com.example.cardcharity.repository.network.RetrofitFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
object RetrofitModule {

    @Provides
    @Singleton
    fun provideRetrofit(url: String, okHttpClient: OkHttpClient): Retrofit {
        return RetrofitFactory(
            url = url,
            okHttpClient = okHttpClient
        ).buildRetrofit()
    }
}