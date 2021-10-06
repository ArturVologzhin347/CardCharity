package com.example.cardcharity.repository.network.api

import com.example.cardcharity.repository.model.Shop
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ShopApi {
    @GET("/shop/")
    fun getAllShops(): Call<List<Shop>>

    @GET("/shop/{id}")
    fun getShopById(@Path("id") id: Int): Call<List<Shop>>
}