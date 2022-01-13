package com.example.cardcharity.repository.network

import com.example.cardcharity.domain.common.Event
import com.example.cardcharity.repository.network.remote.ShopApi
import kotlinx.coroutines.flow.MutableStateFlow
import okhttp3.HttpUrl
import retrofit2.Call
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Api @Inject constructor(private val network: NetworkFactory) {
    val url: HttpUrl = network.url
    val shops by network.lazyApi(ShopApi::class)

    suspend fun <T : Any> request(
        call: Call<T>,
        flow: MutableStateFlow<Event<T>?>
    ) = request(call) { event -> flow.value = event }

    suspend fun <T : Any> request(
        call: Call<T>,
        response: (response: Event<T>) -> Unit
    ) = network.request(call, response)
}
