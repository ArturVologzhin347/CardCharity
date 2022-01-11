package com.example.cardcharity.presentation.activities.main

import com.example.cardcharity.domain.shop.ShopListModel
import com.example.cardcharity.presentation.base.mvi.MviViewState

sealed class MainViewState: MviViewState

object Load : MainViewState()

data class Success(val shops: List<ShopListModel>) : MainViewState()

sealed class Fail : MainViewState()

object NoItems : Fail()
object NoNetwork : Fail()
object Unknown : Fail()


fun load() = Load

fun success(shops: List<ShopListModel>) = Success(shops)

fun failNoItems() = NoItems

fun failNoNetwork() = NoNetwork

fun failUnknown() = Unknown

