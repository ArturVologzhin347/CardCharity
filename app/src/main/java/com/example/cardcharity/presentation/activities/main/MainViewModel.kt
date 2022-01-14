package com.example.cardcharity.presentation.activities.main

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.cardcharity.di.Dagger2
import com.example.cardcharity.domain.common.Event
import com.example.cardcharity.domain.shop.adapter.ShopMainAdapter
import com.example.cardcharity.presentation.base.mvi.MviViewModel
import com.example.cardcharity.repository.model.Shop
import com.example.cardcharity.repository.network.exception.NoNetworkException
import com.example.cardcharity.repository.preferences.Preferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel(application: Application) :
    MviViewModel<MainViewState, MainEvent>(load(), application) {

    private val _rawShops = MutableStateFlow<List<Shop>?>(null)
    val rawShops = _rawShops.asStateFlow()

    private val defaultShopsAdapter = ShopMainAdapter()

    @Inject
    lateinit var preferences: Preferences

    override fun inject(dagger: Dagger2) {
        super.inject(dagger)
        dagger.repositoryComponent.inject(this)
    }


    fun fetchShops() = viewModelScope.launch(Dispatchers.IO) {
        with(api) {
            request(shops.getAllShops()) { response ->
                when (response) {
                    is Event.Success -> _rawShops.value = response.data
                    is Event.Load -> state = load()
                    is Event.Fail -> state = when (response.throwable) {
                        is NoNetworkException -> failNoNetwork()
                        else -> failUnknown()
                    }
                }
            }
        }
    }

    fun fetchFormatData(shops: List<Shop>) = viewModelScope.launch(Dispatchers.IO) {
        val models = async { defaultShopsAdapter.format(shops) }
        state = success(models.await())
    }

    override fun reduceEvent(event: MainEvent) {
        when (event) {
            Refresh -> fetchShops()
            else -> super.reduceEvent(event)
        }
    }
}
