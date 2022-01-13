package com.example.cardcharity.presentation.activities.main.search

import com.example.cardcharity.presentation.activities.main.search.list.SearchShopItem
import com.example.cardcharity.presentation.base.mvi.MviViewState
import com.example.cardcharity.repository.model.Shop

sealed class SearchViewState: MviViewState

object LoadData : SearchViewState()

object Load : SearchViewState()

data class Success(val shops: List<SearchShopItem>) : SearchViewState()

sealed class Fail : SearchViewState()
object NoNetwork : Fail()
object NoData : Fail()
object NotFound : Fail()
object Unknown : Fail()


fun loadData() = LoadData

fun load() = Load

fun success(shops: List<SearchShopItem>) = Success(shops)

fun failNoNetwork() = NoNetwork

fun failNoData() = NoData

fun failNotFound() = NotFound

fun failUnknown() = Unknown
