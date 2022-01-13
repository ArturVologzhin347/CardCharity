package com.example.cardcharity.presentation.activities.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.example.cardcharity.presentation.activities.main.search.SearchActivity
import com.example.cardcharity.presentation.activities.settings.SettingsActivity
import com.example.cardcharity.presentation.base.mvi.MviActivity
import com.example.cardcharity.utils.extensions.authorization
import com.example.cardcharity.utils.extensions.launchWhenStarted
import com.example.cardcharity.utils.extensions.openActivity
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.flow.onEach

class MainActivity : MviActivity<MainViewState, MainEvent, MainViewModel>() {
    override val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        viewModel.fetchShops()
        super.onCreate(savedInstanceState)

        viewModel.rawShops.onEach { shops ->
            shops?.let { viewModel.fetchFormatData(it) }
        }.launchWhenStarted(lifecycleScope)
    }

    @Composable
    override fun Screen(reduce: (event: MainEvent) -> Unit, viewState: MainViewState) {
        val user = authorization.user.collectAsState()
        val isLight = MaterialTheme.colors.isLight

        rememberSystemUiController().apply {
            SideEffect {
                setStatusBarColor(Color.Transparent, isLight)
            }
        }

        MainScreen(
            reduce = reduce,
            viewState = viewState,
            user = user.value
        )
    }

    override fun reduceEvent(event: MainEvent) {
        when (event) {
            Settings -> settingsEvent()
            Search -> searchEvent()
            else -> super.reduceEvent(event)
        }
    }

    private fun settingsEvent() {
        openActivity(this, SettingsActivity::class)
    }


    private fun searchEvent() {
        openActivity(this, SearchActivity::class)
    }
}
