package com.example.cardcharity.presentation.base.mvi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.cardcharity.presentation.base.BaseActivity
import timber.log.Timber

abstract class MviActivity<VS : MviViewState, E : MviEvent, VM : MviViewModel<VS, E>> :
    BaseActivity() {
    protected abstract val viewModel: VM

    @Composable
    override fun Screen() {
        val viewState = viewModel.viewState.collectAsState()
        Screen(
            reduce = { event -> reduce(event) },
            viewState = viewState.value
        )
    }

    @Composable
    abstract fun Screen(reduce: (event: E) -> Unit, viewState: VS)

    private fun reduce(event: E) {
        Timber.d("Reducing event: $event")
        reduceEvent(event)
    }

    protected open fun reduceEvent(event: E) {
        viewModel.reduceEvent(event)
    }
}
