package com.example.cardcharity.di.module

import dagger.Module
import dagger.Provides

@Module
object ApiModule {

    @Provides
    fun provideApiUrl(): String {
        return "https://10.3.21.33:8443/"
    }



}