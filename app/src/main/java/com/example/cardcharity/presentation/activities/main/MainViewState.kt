package com.example.cardcharity.presentation.activities.main

import com.example.cardcharity.domain.shop.ShopListModel


sealed class MainViewState {

    object Load : MainViewState()

    data class Success(val shops: List<ShopListModel>) : MainViewState()

    sealed class Fail : MainViewState() {
        object NoItems : Fail()
        object NotFound : Fail()
        object NoNetworkConnection : Fail()
        //object Failure : Fail()
    }


    companion object {

        fun load() = Load

        fun success(shops: List<ShopListModel>) = Success(shops)

        fun failNoItems() = Fail.NoItems

        fun failNotFound() = Fail.NotFound

        fun failNoNetworkConnection() = Fail.NoNetworkConnection

    }

}
