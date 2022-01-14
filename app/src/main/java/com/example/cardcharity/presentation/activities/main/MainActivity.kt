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
import com.example.cardcharity.di.Dagger2
import com.example.cardcharity.presentation.activities.main.search.SearchActivity
import com.example.cardcharity.presentation.activities.settings.SettingsActivity
import com.example.cardcharity.presentation.base.mvi.MviActivity
import com.example.cardcharity.repository.preferences.Preferences
import com.example.cardcharity.utils.extensions.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import kotlin.math.max

class MainActivity : MviActivity<MainViewState, MainEvent, MainViewModel>() {
    override val viewModel: MainViewModel by viewModels()
    private var previousScreenBrightness = 0F

    override fun onCreate(savedInstanceState: Bundle?) {
        previousScreenBrightness = window.getBrightness()
        viewModel.fetchShops()
        super.onCreate(savedInstanceState)

        viewModel.rawShops.onEach { shops ->
            shops?.let { viewModel.fetchFormatData(it) }
        }.launchWhenStarted(lifecycleScope)
    }

    @Composable
    override fun Screen(reduce: (event: MainEvent) -> Unit, viewState: MainViewState) {
        val user = authorization.user.collectAsState()

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
            is Highlight -> highlightEvent(event)
            else -> super.reduceEvent(event)
        }
    }

    private fun settingsEvent() {
        openActivity(this, SettingsActivity::class)
    }


    private fun searchEvent() {
        openActivity(this, SearchActivity::class)
    }

    private fun highlightEvent(event: Highlight) {
        if (event.enable) {
            maxBrightnessEvent()
        } else {
            setPreviousBrightness()
        }
    }

    private fun maxBrightnessEvent() {
        if (viewModel.preferences.highlightCode) {
            with(window) {
                previousScreenBrightness = getBrightness()
                setScreenBrightness(MAX_BRIGHTNESS)
            }
        }
    }

    private fun setPreviousBrightness() {
        if (viewModel.preferences.highlightCode) {
            window.setScreenBrightness(previousScreenBrightness)

        }
    }

    companion object {
        private const val MAX_BRIGHTNESS = 1f
    }
}
