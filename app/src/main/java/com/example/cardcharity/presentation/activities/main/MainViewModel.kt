package com.example.cardcharity.presentation.activities.main

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.viewModelScope
import com.example.cardcharity.presentation.activities.main.list.ModelShop
import com.example.cardcharity.presentation.activities.main.list.RecyclerAdapterShop
import com.example.cardcharity.presentation.base.BaseViewModel
import com.example.cardcharity.repository.Api
import com.example.cardcharity.repository.model.Shop
import com.example.cardcharity.utils.extensions.firstInLowercase
import com.example.cardcharity.utils.getRandomString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class MainViewModel(application: Application) : BaseViewModel(application) {
    private val _shops = MutableStateFlow<List<ModelShop>?>(null)
    val shops: StateFlow<List<ModelShop>?> = _shops.asStateFlow()

    private val _uiState = MutableStateFlow<UiState?>(null)
    val uiState: StateFlow<UiState?> = _uiState.asStateFlow()

    var _adapter: RecyclerAdapterShop? = null
    val adapter: RecyclerAdapterShop
        get() = checkNotNull(_adapter)

    fun initialize(uid: String) {
        _adapter = RecyclerAdapterShop(uid)
    }

    fun requestModels() {
        _uiState.value = UiState.LOADING


        /*
          viewModelScope.launch {
            val models = buildModels(getMockModels())
            _uiState.value = UiState.SUCCESSFUL
            _shops.value = models
        }
         */




          after {// After ui animations
            Api.shopService.getAllShops().enqueue(object : Callback<List<Shop>> {
                override fun onResponse(call: Call<List<Shop>>, response: Response<List<Shop>>) {
                    viewModelScope.launch {
                        val result = response.body() ?: emptyList()
                        val models = buildModels(result + getMockModels())
//                      val models = buildModels(result)
                        val uiState = if (models.isEmpty()) UiState.ERROR else UiState.SUCCESSFUL
                        _shops.value = models
                        _uiState.value = uiState
                    }
                }

                override fun onFailure(call: Call<List<Shop>>, t: Throwable) {
                    _uiState.value = UiState.NO_CONNECTION
                    _shops.value = null
                    Timber.w(t)
                }
            })
        }


    }

    private suspend fun getMockModels(): List<Shop> = withContext(Dispatchers.IO) {
        return@withContext arrayListOf<Shop>().apply {
            repeat(50) {
                add(
                    Shop(
                        id = 4,
                        name = getRandomString(10)
                    )
                )
            }
        }
    }

    private suspend fun buildModels(raws: List<Shop>): List<ModelShop> =
        withContext(Dispatchers.IO) {
            return@withContext arrayListOf<ModelShop>().apply {
                if (raws.isEmpty()) {
                    return@apply
                }

                val sortedRaws = raws.sortedBy { it.name.lowercase() }

                var lastCharacter: String? = null
                for (shop in sortedRaws) {
                    val character = shop.name.firstInLowercase()

                    if (character != lastCharacter) {
                        add(ModelShop.buildTitle(character))
                        lastCharacter = character
                    }

                    add(ModelShop.buildModel(shop))
                }
            }
        }

    private fun after(action: () -> Unit) {
        handler.postDelayed(action, 600)
    }

    enum class UiState {
        SUCCESSFUL,
        LOADING,
        NO_CONNECTION,
        ERROR
    }

    companion object {
        val handler = Handler(Looper.getMainLooper())
    }
}