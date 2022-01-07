package com.example.cardcharity

import android.app.Application
import android.content.Context
import com.example.cardcharity.di.Dagger2
import com.example.cardcharity.presentation.theme.DarkThemeManager
import com.example.cardcharity.repository.network.Api
import com.example.cardcharity.repository.preferences.Preferences
import okhttp3.OkHttpClient
import timber.log.Timber

import javax.inject.Inject

class App : Application() {
    private var _dagger: Dagger2? = null

    val dagger: Dagger2
        get() = checkNotNull(_dagger)

    @Inject
    lateinit var api: Api

    @Inject
    lateinit var okHttpClient: OkHttpClient

    @Inject
    lateinit var preferences: Preferences

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG_MODE) {
            Timber.plant(Timber.DebugTree())
        }

        _dagger = Dagger2(this).apply {
            repositoryComponent.inject(this@App)
        }


        preferences.setupDefaultValues()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

}