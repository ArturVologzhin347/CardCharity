package com.example.cardcharity.di

import android.content.Context
import com.example.cardcharity.di.component.DaggerRepositoryComponent
import com.example.cardcharity.di.component.RepositoryComponent
import com.example.cardcharity.di.module.AppModule
import com.example.cardcharity.di.module.PreferencesModule
import com.example.cardcharity.repository.preferences.Preferences

class Dagger2(context: Context) {
    val repositoryComponent: RepositoryComponent by lazy {
        DaggerRepositoryComponent.builder().apply {
            appModule(AppModule(context))
            preferencesModule(PreferencesModule(Preferences.KEY))
        }.build()
    }
}