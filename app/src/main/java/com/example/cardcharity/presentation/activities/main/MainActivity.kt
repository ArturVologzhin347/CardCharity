package com.example.cardcharity.presentation.activities.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.example.cardcharity.presentation.activities.settings.SettingsActivity
import com.example.cardcharity.presentation.base.BaseActivity
import com.example.cardcharity.utils.extensions.authorization
import com.example.cardcharity.utils.extensions.launchWhenStarted
import com.example.cardcharity.utils.extensions.openActivity
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

class MainActivity : BaseActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        viewModel.fetchShops()
        super.onCreate(savedInstanceState)

        viewModel.rawShops.onEach { shops ->
            shops?.let { viewModel.fetchFormatData(it) }
        }.launchWhenStarted(lifecycleScope)


        //TODO
        authorization.user.onEach { user ->
            user?.let { Timber.e(it.toString()) }
        }.launchWhenStarted(lifecycleScope)
    }

    @Composable
    override fun Screen() {
        val systemUiController = rememberSystemUiController()
        val viewState = viewModel.viewState.collectAsState()
        val user = authorization.user.collectAsState()

        SideEffect {
            systemUiController.setStatusBarColor(Color.Transparent)
        }

        MainScreen(
            reduce = { event -> reduceEvent(event) },
            user = user.value,
            viewState = viewState.value
        )
    }

    private fun reduceEvent(event: MainEvent) {
        Timber.d("Reducing event: $event")

        when (event) {
            MainEvent.Settings -> settingsEvent()
            MainEvent.Search -> searchEvent()
            else -> viewModel.reduceEvent(event)
        }
    }

    private fun settingsEvent() {
        openActivity(this, SettingsActivity::class)
    }

    private fun searchEvent() {
        TODO()
    }


}
