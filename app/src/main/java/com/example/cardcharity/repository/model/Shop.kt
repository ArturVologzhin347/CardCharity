package com.example.cardcharity.repository.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Shop(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String
)