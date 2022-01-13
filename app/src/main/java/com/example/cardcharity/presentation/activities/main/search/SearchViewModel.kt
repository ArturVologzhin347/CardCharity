package com.example.cardcharity.presentation.activities.main.search

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.cardcharity.domain.common.Event
import com.example.cardcharity.domain.shop.adapter.ShopSearchAdapter
import com.example.cardcharity.presentation.base.mvi.MviViewModel
import com.example.cardcharity.repository.model.Shop
import com.example.cardcharity.repository.network.exception.NoNetworkException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel(application: Application) :
    MviViewModel<SearchViewState, SearchEvent>(loadData(), application) {

    private val _shops = MutableStateFlow<List<Shop>?>(null)
    val shops = _shops.asStateFlow()

    private val searchShopsAdapter = ShopSearchAdapter()

    fun fetchShops() = viewModelScope.launch(Dispatchers.IO) {
        with(api) {
            request(shops.getAllShops()) { response ->
                when (response) {
                    is Event.Success -> {
                        _shops.value = response.data.sortedBy { shop -> shop.name.lowercase() }
                    }

                    is Event.Load -> state = loadData()
                    is Event.Fail -> state = when (response.throwable) {
                        is NoNetworkException -> failNoNetwork()
                        else -> failUnknown()
                    }
                }
            }
        }
    }


    override fun reduceEvent(event: SearchEvent) {
        when (event) {
            Refresh -> fetchShops()
            is Search -> searchEvent(event)
            else -> super.reduceEvent(event)
        }
    }

    private fun searchEvent(event: Search) = viewModelScope.launch {
        val shops = shops.value

        if (shops.isNullOrEmpty()) {
            return@launch
        }

        val data = withContext(Dispatchers.IO) {
            searchShopsAdapter.format(event.searching to shops)
        }

        state = if (data.isEmpty()) {
            failNotFound()
        } else {
            success(data)
        }
    }
}
