package com.example.cardcharity.presentation.activities.main

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.cardcharity.domain.common.Event
import com.example.cardcharity.presentation.base.BaseViewModel
import com.example.cardcharity.repository.model.Shop
import com.example.cardcharity.repository.network.exception.NoNetworkException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel(application: Application) : BaseViewModel(application) {
    private val _viewState = MutableStateFlow<MainViewState>(MainViewState.load())
    val viewState = _viewState.asStateFlow()

    private val _rawShops = MutableStateFlow<List<Shop>?>(null)
    val rawShops = _rawShops.asStateFlow()

    var state: MainViewState
        get() = viewState.value
        private set(value) {
            _viewState.value = value
            Timber.i("Current state: $state")
        }

    fun fetchShops() = viewModelScope.launch(Dispatchers.IO) {
        state = MainViewState.load()

        if (MOCK) {
            mockShops()
            return@launch
        }

        with(api) {
            request(shops.getAllShops()) { response ->
                when (response) {
                    is Event.Success -> _rawShops.value = response.data

                    is Event.Fail -> when (response.throwable) { //TODO
                        is NoNetworkException -> {}//No network
                        else -> {} //Failure
                    }

                    Event.Load -> {} /* Nothing */
                }
            }
        }
    }

    private fun mockShops() {
        //rawShops ...
        TODO()

    }

    fun fetchFormatData(shops: List<Shop>) {

    }


    fun reduceEvent(event: MainEvent) {
        when (event) {
            else -> throw NotImplementedError("Event $event is not implemented in $TAG")
        }
    }


    companion object {
        private const val TAG = "MainViewModel"
        private const val MOCK = false
    }
}