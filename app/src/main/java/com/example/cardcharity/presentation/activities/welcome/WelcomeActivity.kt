package com.example.cardcharity.presentation.activities.welcome

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.example.cardcharity.presentation.activities.splash.SplashActivity
import com.example.cardcharity.presentation.activities.splash.SplashScreen
import com.example.cardcharity.presentation.base.BaseActivity
import com.example.cardcharity.presentation.base.mvi.MviActivity
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class WelcomeActivity : MviActivity<WelcomeViewState, WelcomeEvent, WelcomeViewModel>() {
    override val viewModel: WelcomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
    }

    @Composable
    override fun Screen(reduce: (event: WelcomeEvent) -> Unit, viewState: WelcomeViewState) {
        rememberSystemUiController().apply {
            SideEffect {
                setStatusBarColor(Color.Transparent)
            }
        }

        WelcomeScreen(reduce = reduce)
    }

    override fun reduceEvent(event: WelcomeEvent) = when (event) {
        Next -> reduceNext()
    }

    private fun reduceNext() {
        viewModel.visit()
        Intent(this, SplashActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }
}
