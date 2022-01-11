package com.example.cardcharity.presentation.activities.auth.signup

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.core.os.postDelayed
import androidx.lifecycle.lifecycleScope
import com.example.cardcharity.R
import com.example.cardcharity.presentation.activities.main.MainActivity
import com.example.cardcharity.presentation.base.mvi.MviActivity
import com.example.cardcharity.utils.extensions.launchWhenStarted
import kotlinx.coroutines.flow.onEach

class SignupActivity : MviActivity<SignupViewState, SignupEvent, SignupViewModel>() {
    override val viewModel: SignupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.viewState.onEach { state ->
            if (state is Success) reduceEvent(go())
        }.launchWhenStarted(lifecycleScope)
    }

    @Composable
    override fun Screen(reduce: (event: SignupEvent) -> Unit, viewState: SignupViewState) {
        SignupScreen(
            reduce = reduce,
            viewState = viewState
        )
    }

    override fun reduceEvent(event: SignupEvent) {
        if (viewModel.state == Load) return

        when (event) {
            Back -> finish()
            Go -> goEvent()
            else -> super.reduceEvent(event)
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
