package com.example.cardcharity.repository.network.api

import com.example.cardcharity.repository.model.Shop
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ShopApi {
    @GET("/user/shop")
    fun getAllShops(): Call<List<Shop>>
}