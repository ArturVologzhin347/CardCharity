package com.example.cardcharity.presentation.activities.welcome

import com.example.cardcharity.presentation.base.mvi.MviViewState

sealed class WelcomeViewState: MviViewState

object Default: WelcomeViewState()

fun default() = Default
