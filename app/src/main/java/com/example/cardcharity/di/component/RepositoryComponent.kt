package com.example.cardcharity.di.component

import com.example.cardcharity.App
import com.example.cardcharity.di.module.*
import com.example.cardcharity.presentation.activities.auth.login.LoginViewModel
import com.example.cardcharity.presentation.activities.auth.signup.SignupViewModel
import com.example.cardcharity.presentation.activities.main.MainViewModel
import com.example.cardcharity.presentation.activities.settings.SettingsViewModel
import com.example.cardcharity.presentation.activities.splash.SplashViewModel
import com.example.cardcharity.presentation.base.BaseActivity
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        AppModule::class,
        ApiModule::class,
        NetworkModule::class,
        HttpClientModule::class,
        PreferencesModule::class,
        RetrofitModule::class
    ]
)

@Singleton
interface RepositoryComponent {
    fun inject(app: App)
    fun inject(activity: BaseActivity)
    fun inject(vm: SplashViewModel)
    fun inject(vm: SettingsViewModel)
}