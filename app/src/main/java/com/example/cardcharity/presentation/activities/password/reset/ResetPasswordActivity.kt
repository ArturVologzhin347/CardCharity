package com.example.cardcharity.presentation.activities.password.reset

import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.cardcharity.presentation.activities.auth.login.LoginViewState
import com.example.cardcharity.presentation.base.BaseActivity
import timber.log.Timber

class ResetPasswordActivity : BaseActivity() {
    private val viewModel: ResetPasswordViewModel by viewModels()

    @Composable
    override fun Screen() {
        val state = viewModel.viewState.collectAsState()

        ResetPasswordScreen(
            reduce = { event -> reduceEvent(event) },
            viewState = state.value
        )
    }

    private fun reduceEvent(event: ResetPasswordEvent) {
        Timber.d("Reducing event: $event")

        if (viewModel.state == Load) {
            Timber.i("Cannot reduce event $event because state is Load")
            return
        }

        when(event) {
            Finish -> this.finish()
            else -> viewModel.reduceEvent(event)
        }

    }

}