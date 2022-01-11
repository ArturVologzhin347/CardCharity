package com.example.cardcharity.presentation.activities.main.search

import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.cardcharity.presentation.base.BaseActivity
import timber.log.Timber

class SearchActivity : BaseActivity() {
    private val viewModel: SearchViewModel by viewModels()

    @Composable
    override fun Screen() {
        val state = viewModel.viewState.collectAsState()

        SearchScreen(
            reduce = { event -> reduceEvent(event) },
            viewState = state.value
        )
    }

    private fun reduceEvent(event: SearchEvent) {
        Timber.d("Reducing event: $event")

        when (event) {
            Finish -> this.finish()
            else -> viewModel.reduceEvent(event)
        }
    }
}
