package com.example.cardcharity.domain.shop.adapter

import com.example.cardcharity.domain.common.DataAdapter
import com.example.cardcharity.domain.shop.ShopListModel
import com.example.cardcharity.repository.model.Shop

class ShopSearchAdapter : DataAdapter<Pair<String, List<Shop>>, List<ShopListModel>> {

    override suspend fun format(input: Pair<String, List<Shop>>): List<ShopListModel> {
        check(input.first.trim().isNotEmpty())
        { "Trimmed search value by name cannot be empty." }






        TODO()
    }
}