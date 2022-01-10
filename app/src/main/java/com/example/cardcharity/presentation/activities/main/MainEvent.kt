package com.example.cardcharity.presentation.activities.main

sealed class MainEvent {

    object Settings : MainEvent()

    object Refresh : MainEvent()

    object Search : MainEvent()


    companion object {

        fun settings() = Settings

        fun refresh() = Refresh

        fun search() = Search

    }


}