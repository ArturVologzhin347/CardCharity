package com.example.cardcharity.presentation.activities.settings

import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.cardcharity.presentation.activities.about.AboutActivity
import com.example.cardcharity.presentation.activities.password.reset.ResetPasswordActivity
import com.example.cardcharity.presentation.base.BaseActivity
import com.example.cardcharity.utils.extensions.authorization
import com.example.cardcharity.utils.extensions.openActivity
import timber.log.Timber

/*
- reset password
 */

class SettingsActivity : BaseActivity() {
    private val viewModel: SettingsViewModel by viewModels()

    @Composable
    override fun Screen() {
        val viewState = viewModel.viewState.collectAsState()
        val user = authorization.user.collectAsState()

        SettingsScreen(
            viewState = viewState.value,
            user = user.value,
            reduce = { event -> reduceEvent(event) }
        )
    }

    private fun reduceEvent(event: SettingsEvent) {
        Timber.d("Reducing event: $event")

        when (event) {
            SettingsEvent.Back -> finish()
            SettingsEvent.AboutApp -> openActivity(this, AboutActivity::class)
            SettingsEvent.ResetPassword -> openActivity(this, ResetPasswordActivity::class)
            else -> viewModel.reduceEvent(event)
        }
    }


}