package com.example.cardcharity.di.module

import com.example.cardcharity.repository.network.NetworkFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
object NetworkModule {


    @Provides
    @Singleton
    fun provideRetrofit(retrofit: Retrofit): NetworkFactory {
        return NetworkFactory(retrofit)
    }


}
