package com.example.cardcharity.presentation.activities.main

import com.example.cardcharity.domain.shop.ShopListModel


sealed class MainViewState {

    object Load : MainViewState()

    data class Success(val shops: List<ShopListModel>) : MainViewState()

    sealed class Fail : MainViewState() {
        object NoItems : Fail()
        object NoNetworkConnection : Fail()
        object Unknown : Fail()
    }


    companion object {

        fun load() = Load

        fun success(shops: List<ShopListModel>) = Success(shops)

        fun failNoItems() = Fail.NoItems

        fun failNoNetworkConnection() = Fail.NoNetworkConnection

        fun failUnknown() = Fail.Unknown

    }

}
