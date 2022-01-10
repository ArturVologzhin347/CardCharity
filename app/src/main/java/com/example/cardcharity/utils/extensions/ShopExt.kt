package com.example.cardcharity.utils.extensions

import android.net.Uri
import com.example.cardcharity.repository.model.Shop

//TODO
fun Shop.getImageUrlLogo(url: String): Uri {
   // return Uri.parse("https://logosklad.ru/photo/logos/616/1582597280.jpg")
//return Uri.parse("https://via.placeholder.com/150")
     return Uri.parse("${url}user/shop/$id/logo")
}

//TODO
fun Shop.getImageUrlCodeImage(url: String, uid: String): Uri {
   // return Uri.parse("http://qrcoder.ru/code/?123456789&10&0")
    return Uri.parse("${url}user/code/?shopId=$id&uid=$uid")
}