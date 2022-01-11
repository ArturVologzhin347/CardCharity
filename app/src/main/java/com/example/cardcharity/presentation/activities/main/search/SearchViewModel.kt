package com.example.cardcharity.presentation.activities.main.search

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

class SearchViewModel(application: Application) : BaseViewModel(application) {
    private val _viewState = MutableStateFlow<SearchViewState>(loadData())
    val viewState = _viewState.asStateFlow()

    private val _shops = MutableStateFlow<List<Shop>?>(null)
    val shops = _shops.asStateFlow()


    var state: SearchViewState
        get() = viewState.value
        private set(value) {
            _viewState.value = value
            Timber.i("Current state: $state")
        }

    fun fetchShops() = viewModelScope.launch(Dispatchers.IO) {
        with(api) {
            request(shops.getAllShops()) { response ->
                when (response) {
                    is Event.Success -> _shops.value = response.data
                    is Event.Load -> state = load()
                    is Event.Fail -> state = when (response.throwable) {
                        is NoNetworkException -> failNoNetwork()
                        else -> failUnknown()
                    }
                }
            }
        }
    }

    fun reduceEvent(event: SearchEvent) {
        if (state is LoadData) {
            return
        }

        when (event) {
            Refresh -> TODO()
            is Search -> TODO()
            else -> throw NotImplementedError("Event $event is not implemented in $TAG")
        }
    }


    companion object {
        private const val TAG = "SearchViewModel"
    }

}