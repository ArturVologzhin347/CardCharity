package com.example.cardcharity.presentation.base.mvi

import android.app.Application
import com.example.cardcharity.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

abstract class MviViewModel<VS : MviViewState, E : MviEvent>(
    initialState: VS, application: Application
) : BaseViewModel(application) {

    protected val _viewState = MutableStateFlow(initialState)
    val viewState = _viewState.asStateFlow()

    var state: VS
        get() = viewState.value
        set(value) {
            _viewState.value = value
            Timber.i("Current state: $state")
        }

    open fun reduceEvent(event: E) {
        throw NotImplementedError("Event $event is not implemented.")
    }
}
