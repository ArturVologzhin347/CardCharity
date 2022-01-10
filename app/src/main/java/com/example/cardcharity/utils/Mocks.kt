package com.example.cardcharity.utils

import com.example.cardcharity.repository.model.Shop


fun getMockedShops(count: Int): List<Shop> {
    return arrayListOf<Shop>().apply {
        repeat(count) {
            add(
                Shop(
                    id = it,
                    name = getRandomString(10)
                )
            )
        }
    }
}
