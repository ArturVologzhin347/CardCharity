package com.example.cardcharity.presentation.activities.main.search

import com.example.cardcharity.repository.model.Shop

sealed class SearchViewState

object LoadData : SearchViewState()

object Load : SearchViewState()

data class Success(val shops: List<Shop>) : SearchViewState()


sealed class Fail : SearchViewState()

object NoNetwork : Fail()
object NoData : Fail()
object NotFound : Fail()
object Unknown : Fail()


fun loadData() = LoadData

fun load() = Load

fun success(shops: List<Shop>) = Success(shops)

fun failNoNetwork() = NoNetwork

fun failNoData() = NoData

fun failNotFound() = NotFound

fun failUnknown() = Unknown
