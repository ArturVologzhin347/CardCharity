package com.example.cardcharity.presentation.activities.settings

import android.os.Build
import com.example.cardcharity.presentation.base.mvi.MviViewState

sealed class SettingsViewState(
    val nightMode: Boolean,
    val syncWithSystemTheme: Boolean,
    val highlightCode: Boolean
) : MviViewState

object Initial: SettingsViewState(
    nightMode = false,
    syncWithSystemTheme = true,
    highlightCode = true
)

class Default(nightMode: Boolean, syncWithAndroidTheme: Boolean, highlightCode: Boolean) :
    SettingsViewState(nightMode, syncWithAndroidTheme, highlightCode)

fun default(nightMode: Boolean, syncWithSystemTheme: Boolean, highlightCode: Boolean) =
    Default(nightMode, syncWithSystemTheme, highlightCode)

fun initial() = Initial


val SettingsViewState.nightModeEnabled: Boolean
    get() = !syncWithSystemTheme

val SettingsViewState.syncWithSystemThemeEnabled: Boolean
    get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
