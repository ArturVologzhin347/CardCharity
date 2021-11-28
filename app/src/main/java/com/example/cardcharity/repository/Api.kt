package com.example.cardcharity.repository

import com.example.cardcharity.repository.network.RetrofitService
import com.example.cardcharity.repository.network.api.ShopApi

object Api : RetrofitService(URL) {
    val unsafeHttpClient = httpClient
    val shopService by lazyApi(ShopApi::class)
}