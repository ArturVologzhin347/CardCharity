package com.example.cardcharity

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.multidex.MultiDex
import com.example.cardcharity.presentation.appearence.ThemeController
import com.example.cardcharity.repository.preferences.Preferences
import com.example.cardcharity.repository.preferences.PreferencesHelper
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG_MODE) {
            Timber.plant(Timber.DebugTree())
        }

        initializeSharedPreferences()
        initializeDefaultSharedProperties()
        ThemeController.initialize(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)//MultiDex enabled.
    }

    private fun initializeSharedPreferences() {
        preferences = getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE)
        preferencesHelper = PreferencesHelper(PREFERENCES_KEY, preferences)
    }

    private fun initializeDefaultSharedProperties() {
        val hasVisited = Preferences.hasVisited
        if(!hasVisited) {

            //Default theme
            val defaultThemeState = if(android.os.Build.VERSION.SDK_INT
                >= android.os.Build.VERSION_CODES.Q) {
                ThemeController.ThemeState.NIGHT
            } else {
                ThemeController.ThemeState.DAY
            }

            Preferences.theme = defaultThemeState.name
            Preferences.hasVisited = true
        }
    }


    companion object {
        lateinit var preferences: SharedPreferences
        lateinit var preferencesHelper: PreferencesHelper
        private const val PREFERENCES_KEY = "Preferences"
    }
}