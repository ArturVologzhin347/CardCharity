package com.example.cardcharity.presentation.activities.main.list

import com.example.cardcharity.repository.model.Shop

class ModelShop private constructor(
    val type: ModelType,
    private val shop: Shop? = null,
    private val title: String? = null
) {

    fun getShop(): Shop = checkNotNull(shop)
    fun geTitle(): String = checkNotNull(title)

    companion object {
        fun buildModel(shop: Shop): ModelShop {
            return ModelShop(
                type = ModelType.MODEL,
                shop = shop,
                title = null
            )
        }

        fun buildTitle(title: String): ModelShop {
            return ModelShop(
                type = ModelType.TITLE,
                shop = null,
                title = title
            )

        }
    }
}