package com.example.cardcharity.repository.preferences

import com.example.cardcharity.repository.preferences.PreferencesDelegate

object Preferences {
    //Тема приложения. Дневная, ночная или авто.
    var theme: String by PreferencesDelegate("")

    //Запускалось ли приложение на этом девайсе.
    var hasVisited: Boolean by PreferencesDelegate(false)

}