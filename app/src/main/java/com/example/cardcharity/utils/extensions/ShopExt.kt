package com.example.cardcharity.utils.extensions

import com.example.cardcharity.repository.model.Shop
import com.example.cardcharity.repository.network.RetrofitService


fun Shop.getLogoUrl(): String {
    return "${RetrofitService.URL}user/shop/$id/logo"
}

fun Shop.getCodeUrl(uid: String): String {
    return "${RetrofitService.URL}user/code/?shopId=$id&uid=$uid"
}