package com.example.cardcharity.presentation.activities.main.search

sealed class SearchEvent

object Finish: SearchEvent()

object Refresh: SearchEvent()

data class Search(val searching: String): SearchEvent()

fun finish() = Finish

fun refresh() = Refresh

fun search(searching: String) = Search(searching)
