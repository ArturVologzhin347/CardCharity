package com.example.cardcharity.domain.shop.adapter

import com.example.cardcharity.domain.common.DataAdapter
import com.example.cardcharity.domain.shop.ShopListModel
import com.example.cardcharity.repository.model.Shop
import com.example.cardcharity.utils.extensions.trimmedIsNotEmpty

class ShopSearchAdapter : DataAdapter<Pair<String?, List<Shop>>, List<ShopListModel>> {

    override suspend fun format(input: Pair<String?, List<Shop>>): List<ShopListModel> {
        val searching = input.first
        val shops = input.second

        checkNotNull(searching)
        check(searching.trimmedIsNotEmpty())







        TODO()
    }
}