package com.example.cardcharity.repository.preferences

import com.example.cardcharity.presentation.appearence.ThemeController

object Preferences {
    //Тема приложения. Дневная, ночная или авто
    var theme: String by PreferencesDelegate(ThemeController.ThemeState.DAY.name)

    //Запускалось ли приложение на этом девайсе
    var hasVisited: Boolean by PreferencesDelegate(false)

    //Видел ли юзер welcome activity
    var hasWelcome: Boolean by PreferencesDelegate(false)

    //Увеличивать яркость до максимума на скрине с баркодом
    var barcodeIllumination: Boolean by PreferencesDelegate(true)


    fun initializeDefaultPreferences() {
        if (!hasVisited) {
            theme = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                ThemeController.ThemeState.AUTO
            } else {
                ThemeController.ThemeState.DAY
            }.name

            hasVisited = true
        }
    }
}