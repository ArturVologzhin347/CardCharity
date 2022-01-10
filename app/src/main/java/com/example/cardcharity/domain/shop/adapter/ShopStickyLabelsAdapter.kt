package com.example.cardcharity.domain.shop.adapter

import com.example.cardcharity.domain.common.DataAdapter
import com.example.cardcharity.domain.shop.ShopListModel
import com.example.cardcharity.repository.model.Shop
import com.example.cardcharity.utils.extensions.firstInLowercase
import com.example.cardcharity.utils.extensions.firstInUppercase
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ShopStickyLabelsAdapter : DataAdapter<List<Shop>, List<ShopListModel>> {
    override suspend fun format(input: List<Shop>): List<ShopListModel> = suspendCoroutine {
        if (input.isEmpty()) {
            it.resume(emptyList())
            return@suspendCoroutine
        }

        val shops = arrayListOf<ShopListModel>().apply {
            val sorted = input.sortedBy { shop -> shop.name.lowercase() }

            var lastCharacter: String? = null
            sorted.forEach { shop ->
                val character = shop.name.firstInUppercase()

                if (character != lastCharacter) {
                    add(ShopListModel.LabelModel(label = character))
                    lastCharacter = character
                }

                add(ShopListModel.ShopModel(shop = shop))
            }
        }
        it.resume(shops)
    }
}