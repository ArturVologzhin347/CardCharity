package com.example.cardcharity.presentation.activities.settings

import android.os.Build

sealed class SettingsViewState(
    val nightMode: Boolean,
    val syncWithSystemTheme: Boolean,
    val highlightCode: Boolean
) {

    class Default(nightMode: Boolean, syncWithAndroidTheme: Boolean, highlightCode: Boolean) :
        SettingsViewState(nightMode, syncWithAndroidTheme, highlightCode)

    companion object {

        fun default(nightMode: Boolean, syncWithSystemTheme: Boolean, highlightCode: Boolean) =
            Default(nightMode, syncWithSystemTheme, highlightCode)

    }


}


val SettingsViewState.nightModeEnabled: Boolean
    get() = !syncWithSystemTheme

val SettingsViewState.syncWithSystemThemeEnabled: Boolean
    get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
