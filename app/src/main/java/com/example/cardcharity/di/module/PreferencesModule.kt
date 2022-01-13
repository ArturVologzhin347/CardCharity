package com.example.cardcharity.di.module

import android.content.Context
import com.example.cardcharity.repository.preferences.core.PreferencesStore
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PreferencesModule(private val key: String) {

    @Provides
    @Singleton
    fun providePreferences(context: Context): PreferencesStore {
        return PreferencesStore.of(context, key)
    }
}
