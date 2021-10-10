package com.example.cardcharity.presentation.activities.settings

import android.app.Application
import com.example.cardcharity.presentation.appearence.ThemeController
import com.example.cardcharity.presentation.base.BaseViewModel

class SettingsViewModel(application: Application) : BaseViewModel(application) {
    val themeState: ThemeController.ThemeState
        get() = ThemeController.themeState

    fun nightModeSwitcher(isChecked: Boolean, invalidate: () -> Unit) {
        if (themeState != ThemeController.ThemeState.AUTO) {
            val newState = if (isChecked) {
                ThemeController.ThemeState.NIGHT
            } else {
                ThemeController.ThemeState.DAY
            }
            ThemeController.setupNewThemeState(newState)
            invalidate()
        }
    }

    fun nightModeAutoSwitcher(isChecked: Boolean, invalidate: () -> Unit) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            val newState = if (isChecked) {
                ThemeController.ThemeState.AUTO
            } else {
                ThemeController.getSystemTheme(context).state
            }
            ThemeController.setupNewThemeState(newState)
            invalidate()
        }
    }
}