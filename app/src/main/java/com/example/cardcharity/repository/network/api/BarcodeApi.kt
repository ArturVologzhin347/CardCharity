package com.example.cardcharity.repository.network.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BarcodeApi {
    @GET("/code/?")
    fun getImage(@Query("shop") shop: Int): Call<String>
}