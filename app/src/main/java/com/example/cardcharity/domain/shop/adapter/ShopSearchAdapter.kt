package com.example.cardcharity.domain.shop.adapter

import com.example.cardcharity.domain.common.DataAdapter
import com.example.cardcharity.presentation.activities.main.search.list.SearchShopItem
import com.example.cardcharity.repository.model.Shop
import com.example.cardcharity.utils.extensions.trimmedIsEmpty

class ShopSearchAdapter : DataAdapter<Pair<String, List<Shop>>, List<SearchShopItem>> {

    override suspend fun format(input: Pair<String, List<Shop>>): List<SearchShopItem> {
        val searching = input.first
        val shops = input.second

        if (searching.trimmedIsEmpty()) {
            return shops.mapToItems(searching)
        }

        return shops.filter { it.name.contains(searching, ignoreCase = true) }
            .mapToItems(searching)
    }

    private fun List<Shop>.mapToItems(searching: String): List<SearchShopItem> {
        return map {
            SearchShopItem(
                shop = it,
                searched = searching
            )
        }
    }
}