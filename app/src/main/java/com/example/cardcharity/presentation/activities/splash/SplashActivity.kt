package com.example.cardcharity.presentation.activities.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.example.cardcharity.R
import com.example.cardcharity.databinding.ActivitySplashBinding
import com.example.cardcharity.domen.auth.Authentication
import com.example.cardcharity.presentation.activities.auth.AuthActivity
import com.example.cardcharity.presentation.activities.main.MainActivity
import com.example.cardcharity.presentation.activities.welcome.WelcomeActivity
import com.example.cardcharity.presentation.base.BaseActivity
import com.example.cardcharity.repository.preferences.Preferences
import com.example.cardcharity.utils.hideNavigationBar
import com.example.cardcharity.utils.openActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideNavigationBar(this)
        handler.postDelayed({
            val next = if (Authentication.isLoggedIn()) {
                MainActivity::class
            } else if (!Preferences.hasWelcome) {
                WelcomeActivity::class
            } else {
                AuthActivity::class
            }
            openActivity(this, next)

        }, DELAY)
    }

    override fun getDayThemeResId(): Int {
        return R.style.Theme_CardCharity_Fullscreen_Day
    }

    override fun getNightThemeResId(): Int {
        return R.style.Theme_CardCharity_Fullscreen_Night
    }

    companion object {
        private const val DELAY = 600L
        private val handler = Handler(Looper.getMainLooper())
    }

}