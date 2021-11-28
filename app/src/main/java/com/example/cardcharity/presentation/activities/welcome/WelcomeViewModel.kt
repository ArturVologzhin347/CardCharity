package com.example.cardcharity.presentation.activities.welcome

import android.app.Application
import android.content.Context
import com.example.cardcharity.presentation.activities.splash.SplashActivity
import com.example.cardcharity.presentation.base.BaseViewModel
import com.example.cardcharity.repository.preferences.Preferences
import com.example.cardcharity.utils.openActivity

class WelcomeViewModel(application: Application) : BaseViewModel(application) {


    fun next(context: Context) {
        Preferences.hasWelcome = true
        openActivity(context, SplashActivity::class)
    }
}