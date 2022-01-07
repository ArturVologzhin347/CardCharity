package com.example.cardcharity.presentation.activities.auth.signup

import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.cardcharity.presentation.activities.auth.login.LoginViewState
import com.example.cardcharity.presentation.base.BaseActivity
import timber.log.Timber

/*
- back button
- signup label
- email field (notEmpty, isEmail)
- password field (min 6 letters, notEmpty), toggle, helper text
- repeat password (repeated password == password)
- errors handling (like in login)
- apply button
- text under button.

 */

class SignupActivity : BaseActivity() {
    private val viewModel: SignupViewModel by viewModels()


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

        if (viewModel.state == SignupViewState.Load) {
            Timber.i("Cannot reduce event $event because state is Load")
            return
        }

        when (event) {
            is SignupEvent.Back -> finish()
            else -> viewModel.reduceEvent(event)
        }
    }

}