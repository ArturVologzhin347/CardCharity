package com.example.cardcharity.di.module

import android.content.Context
import com.example.cardcharity.repository.network.HttpClientFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
object HttpClientModule {

    @Provides
    @Singleton
    fun provideHttpClient(context: Context): OkHttpClient {
        return HttpClientFactory(context).buildHttpClient()
    }
}
