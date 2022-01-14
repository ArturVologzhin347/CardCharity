package com.example.cardcharity.presentation.activities.main

import com.example.cardcharity.presentation.base.mvi.MviEvent

sealed class MainEvent: MviEvent

object Settings : MainEvent()

object Refresh : MainEvent()

object Search : MainEvent()

data class Highlight(val enable: Boolean) : MainEvent()

fun settings() = Settings

fun refresh() = Refresh

fun search() = Search

fun highlight(enable: Boolean) = Highlight(enable)




