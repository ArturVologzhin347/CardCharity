package com.example.cardcharity.presentation.activities.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.core.os.postDelayed
import androidx.lifecycle.lifecycleScope
import com.example.cardcharity.presentation.activities.auth.signup.SignupEvent
import com.example.cardcharity.presentation.activities.auth.signup.SignupViewState
import com.example.cardcharity.presentation.base.BaseActivity
import com.example.cardcharity.utils.extensions.launchWhenStarted
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

class MainActivity : BaseActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel.fetchShops()
        super.onCreate(savedInstanceState)

        viewModel.rawShops.onEach { shops ->
            shops?.let { viewModel.fetchFormatData(it) }
        }.launchWhenStarted(lifecycleScope)
    }

    @Composable
    override fun Screen() {
        val viewState = viewModel.viewState.collectAsState()

        MainScreen(
            reduce = { event -> reduceEvent(event) },
            viewState = viewState.value
        )
    }

    private fun reduceEvent(event: MainEvent) {
        Timber.d("Reducing event: $event")

        when (event) {
            else -> viewModel.reduceEvent(event)
        }
    }


}
