package com.example.cardcharity.repository.network.remote

import com.example.cardcharity.repository.model.Shop
import retrofit2.Call
import retrofit2.http.GET

interface ShopApi {

    @GET("/user/shop")
    fun getAllShops(): Call<List<Shop>>

}