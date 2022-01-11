package com.example.cardcharity.presentation.activities.settings

import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.cardcharity.presentation.activities.about.AboutActivity
import com.example.cardcharity.presentation.activities.reset.ResetActivity
import com.example.cardcharity.presentation.base.mvi.MviActivity
import com.example.cardcharity.utils.extensions.authorization
import com.example.cardcharity.utils.extensions.openActivity

class SettingsActivity : MviActivity<SettingsViewState, SettingsEvent, SettingsViewModel>() {
    override val viewModel: SettingsViewModel by viewModels()

    @Composable
    override fun Screen(reduce: (event: SettingsEvent) -> Unit, viewState: SettingsViewState) {
        val user = authorization.user.collectAsState()

        SettingsScreen(
            reduce = reduce,
            viewState = viewState,
            user = user.value
        )
    }

    override fun reduceEvent(event: SettingsEvent) {
        when (event) {
            Finish -> finish()
            AboutApp -> openActivity(this, AboutActivity::class)
            ResetPassword -> ResetActivity.start(this, authorization.email ?: "")
            else -> super.reduceEvent(event)
        }
    }
}
