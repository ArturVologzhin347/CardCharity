package com.example.cardcharity.presentation.activities.auth.signup

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.core.os.postDelayed
import androidx.lifecycle.lifecycleScope
import com.example.cardcharity.R
import com.example.cardcharity.presentation.activities.auth.login.LoginActivity
import com.example.cardcharity.presentation.activities.auth.login.LoginEvent
import com.example.cardcharity.presentation.activities.auth.login.LoginViewState
import com.example.cardcharity.presentation.activities.main.MainActivity
import com.example.cardcharity.presentation.base.BaseActivity
import com.example.cardcharity.utils.extensions.launchWhenStarted
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

class SignupActivity : BaseActivity() {
    private val viewModel: SignupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.viewState.onEach { state ->
            if (state is Success) {
                reduceEvent(go())
            }
        }.launchWhenStarted(lifecycleScope)
    }

    @Composable
    override fun Screen() {
        val viewState = viewModel.viewState.collectAsState()

        SignupScreen(
            reduce = { event -> reduceEvent(event) },
            viewState = viewState.value
        )
    }

    private fun reduceEvent(event: SignupEvent) {
        Timber.d("Reducing event: $event")

        if (viewModel.state == Load) {
            Timber.i("Cannot reduce event $event because state is Load")
            return
        }

        when (event) {
            Back -> finish()
            Go -> goEvent()
            else -> viewModel.reduceEvent(event)
        }
    }

    private fun goEvent() {
        Toast.makeText(this, getString(R.string.signup_success), Toast.LENGTH_LONG).show()
        handler.postDelayed(DELAY_AFTER_GO) {
            Intent(this, MainActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }
    }

    companion object {
        private val handler = Handler(Looper.getMainLooper())
        private const val DELAY_AFTER_GO = 600L
    }
}