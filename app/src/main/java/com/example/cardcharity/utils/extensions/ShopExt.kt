package com.example.cardcharity.utils.extensions

import android.net.Uri
import com.example.cardcharity.repository.model.Shop

fun Shop.getImageUrlLogo(url: String): Uri {
    return Uri.parse("${url}user/shop/$id/logo")
}

fun Shop.getImageUrlCodeImage(url: String, uid: String): Uri {
    return Uri.parse("${url}user/code/?shopId=$id&uid=$uid")
}