package com.example.cardcharity.presentation.activities.settings

import com.example.cardcharity.presentation.base.mvi.MviEvent

sealed class SettingsEvent: MviEvent

object Finish : SettingsEvent()

object SignOut : SettingsEvent()

//object AboutApp : SettingsEvent()

object ResetPassword : SettingsEvent()

data class NightMode(val enabled: Boolean) : SettingsEvent()

data class SyncWithSystemTheme(val enabled: Boolean, val nightMode: Boolean) : SettingsEvent()

data class HighlightCode(val enabled: Boolean) : SettingsEvent()


fun finish() = Finish

fun signOut() = SignOut

//fun aboutApp() = AboutApp

fun resetPassword() = ResetPassword

fun nightMode(enabled: Boolean) = NightMode(enabled)

fun syncWithSystemTheme(enabled: Boolean, nightMode: Boolean) =
    SyncWithSystemTheme(enabled, nightMode)

fun highlightCode(enabled: Boolean) = HighlightCode(enabled)




