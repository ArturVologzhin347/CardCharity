package com.example.cardcharity.presentation.activities.main

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.cardcharity.di.Dagger2
import com.example.cardcharity.domain.common.Event
import com.example.cardcharity.domain.shop.adapter.ShopSearchAdapter
import com.example.cardcharity.domain.shop.adapter.ShopStickyLabelsAdapter
import com.example.cardcharity.presentation.base.BaseViewModel
import com.example.cardcharity.repository.model.Shop
import com.example.cardcharity.repository.network.exception.NoNetworkException
import com.example.cardcharity.utils.extensions.nullIfEmpty
import com.example.cardcharity.utils.extensions.nullIfTrimmedEmpty
import com.example.cardcharity.utils.extensions.trimmedIsNotEmpty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel(application: Application) : BaseViewModel(application) {
    private val _viewState = MutableStateFlow<MainViewState>(MainViewState.load())
    val viewState = _viewState.asStateFlow()

    private val _rawShops = MutableStateFlow<List<Shop>?>(null)
    val rawShops = _rawShops.asStateFlow()

    private val _search = MutableStateFlow<String?>(null)
    val search = _search.asStateFlow()

    var state: MainViewState
        get() = viewState.value
        private set(value) {
            _viewState.value = value
            Timber.i("Current state: $state")
        }

    private val defaultShopsAdapter = ShopStickyLabelsAdapter()
    private val searchShopsAdapter = ShopSearchAdapter()


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

                    is Event.Fail -> state = when (response.throwable) {
                        is NoNetworkException -> MainViewState.failNoNetworkConnection()
                        else -> MainViewState.failUnknown()
                    }

                    Event.Load -> {} /* Nothing */
                }
            }
        }
    }

    //TODO delete
    private fun mockShops() {
        _rawShops.value = listOf(
            Shop(1, "Пятёрочка"),
            Shop(2, "Магнит"),
            Shop(3, "Перекрёсток"),
            Shop(4, "Карусель"),
            Shop(5, "Монетка"),
            Shop(6, "Мегамарт"),
            Shop(7, "Пятёрочка"),
            Shop(8, "Магнит"),
            Shop(9, "Перекрёсток"),
            Shop(10, "Карусель"),
            Shop(11, "Монетка"),
            Shop(12, "Мегамарт"),
            Shop(13, "Красное & Белое")
        )
    }

    fun fetchFormatData(shops: List<Shop>) = viewModelScope.launch(Dispatchers.IO) {
        val searchingName = search.value
        val isSearching = searchingName != null

        val models = if (isSearching) {
            check(searchingName.trimmedIsNotEmpty())
            async { searchShopsAdapter.format(input = searchingName to shops) }
        } else {
            async { defaultShopsAdapter.format(shops) }
        }.await()

        state = MainViewState.success(models)
    }

    fun reduceEvent(event: MainEvent) {
        when (event) {

            MainEvent.Refresh -> fetchShops()
            else -> throw NotImplementedError("Event $event is not implemented in $TAG")
        }
    }


    companion object {
        private const val TAG = "MainViewModel"
        private const val MOCK = false
    }
}
