package com.example.cardcharity.presentation.activities.main.search

import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.example.cardcharity.presentation.base.BaseActivity
import com.example.cardcharity.presentation.base.mvi.MviActivity
import com.example.cardcharity.utils.extensions.authorization
import com.example.cardcharity.utils.extensions.launchWhenStarted
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.flow.onEach
import timber.log.Timber


class SearchActivity : MviActivity<SearchViewState, SearchEvent, SearchViewModel>() {
    override val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel.fetchShops()
        super.onCreate(savedInstanceState)

        viewModel.shops.onEach { shops ->
            shops?.let { reduceEvent(search("")) }
        }.launchWhenStarted(lifecycleScope)

    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    override fun Screen(reduce: (event: SearchEvent) -> Unit, viewState: SearchViewState) {
        val user = authorization.user.collectAsState()

        SearchScreen(
            reduce = reduce,
            viewState = viewState,
            user = user.value
        )
    }

    override fun reduceEvent(event: SearchEvent) {

        when (event) {
            Finish -> this.finish()
            else -> super.reduceEvent(event)
        }
    }
}
