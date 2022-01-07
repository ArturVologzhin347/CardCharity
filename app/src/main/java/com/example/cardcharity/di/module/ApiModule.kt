package com.example.cardcharity.di.module

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object ApiModule {

    @Provides
    @Singleton
    fun provideApiUrl(): String {
        return "https://10.3.21.33:8443/"
    }
}
