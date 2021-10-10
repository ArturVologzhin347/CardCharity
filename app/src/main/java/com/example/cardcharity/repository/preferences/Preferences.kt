package com.example.cardcharity.repository.preferences

object Preferences {
    //Тема приложения. Дневная, ночная или авто
    var theme: String by PreferencesDelegate("")

    //Запускалось ли приложение на этом девайсе
    var hasVisited: Boolean by PreferencesDelegate(false)

    //Увеличивать яркость до максимума на скрине с баркодом
    var barcodeIllumination: Boolean by PreferencesDelegate(true)
}