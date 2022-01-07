package com.example.cardcharity.presentation.activities.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.core.os.postDelayed
import androidx.lifecycle.lifecycleScope
import com.example.cardcharity.presentation.base.BaseActivity
import com.example.cardcharity.repository.model.User
import com.example.cardcharity.utils.extensions.launchWhenCreated
import kotlinx.coroutines.flow.onEach

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.authorization.user.onEach {
            next(it)
        }.launchWhenCreated(lifecycleScope)
    }

    @Composable
    override fun Screen() {
        SplashScreen()
    }

    private fun next(user: User?) {
        handler.postDelayed(SPLASH_DELAY) {
            viewModel.next(user)
            finish()
        }
    }


    companion object {
        private const val SPLASH_DELAY = 600L
        private val handler = Handler(Looper.getMainLooper())
    }

}
