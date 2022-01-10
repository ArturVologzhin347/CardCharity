package com.example.cardcharity.domain.shop

import com.example.cardcharity.repository.model.Shop
import com.example.cardcharity.repository.preferences.core.PreferencesStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LastShopsManager @Inject constructor(private val store: PreferencesStore) {
    var lastShopsIds: ArrayList<Int>
        get() = store.getString(LAST_SHOPS_PREFERENCES_KEY).toShopIdsArrayList()
        private set(value) = store.setString(LAST_SHOPS_PREFERENCES_KEY, value.toSavableString())

    private var shopWasAdded = false

    val shopsCount: Int
        get() = lastShopsIds.size


    /**
     * @return last selected shops from input data
     */
    fun getLastFromShops(shops: List<Shop>): List<Shop> {
        return with(lastShopsIds) {
            shops.filter { shop -> contains(shop.id) }
        }
    }

    fun addShop(shop: Shop) {
        if (shopWasAdded) {
            return
        }

        val id = shop.id
        lastShopsIds = lastShopsIds.apply {
            if (id in this) {
                remove(id)
                add(id)
            } else {
                if (size >= MAX_SHOP_COUNT) {
                    removeLast()
                }
                add(id)
            }
        }
    }

    /**
     * Use in activity onCreate()
     */
    fun start() {
        shopWasAdded = false
    }


    fun clear() {
        lastShopsIds = arrayListOf()
    }

    private fun String?.toShopIdsArrayList(): ArrayList<Int> {
        return ArrayList((this ?: "").split(';').map { it.toInt() })
    }

    private fun ArrayList<Int>.toSavableString(): String {
        return this.joinToString(";")
    }

    companion object {
        const val MAX_SHOP_COUNT = 5
        const val LAST_SHOPS_PREFERENCES_KEY = "lastShops"
    }
}
