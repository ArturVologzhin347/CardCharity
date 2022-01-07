package com.example.cardcharity.domain.shop

import com.example.cardcharity.repository.model.Shop

sealed class ShopListModel(val id: Int) {

    data class ShopModel(val shop: Shop) : ShopListModel(shop.id)

    data class LabelModel(val label: String) : ShopListModel(label.hashCode())

    companion object {

        fun shop(shop: Shop) = ShopModel(shop)

        fun label(label: String) = LabelModel(label)

    }

}
