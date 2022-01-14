package com.example.cardcharity.presentation.activities.welcome

import com.example.cardcharity.presentation.base.mvi.MviEvent

sealed class WelcomeEvent : MviEvent

object Next: WelcomeEvent()

fun next() = Next
