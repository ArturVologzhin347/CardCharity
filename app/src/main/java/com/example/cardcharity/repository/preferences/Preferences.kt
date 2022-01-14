package com.example.cardcharity.repository.preferences

import android.os.Build
import com.example.cardcharity.presentation.theme.DarkThemeManager
import com.example.cardcharity.repository.preferences.core.PrefDelegate
import com.example.cardcharity.repository.preferences.core.PreferencesStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Preferences @Inject constructor(private val store: PreferencesStore) {
    private var defaultValuesInitialized by PrefDelegate(store, false)
    var hasVisited by PrefDelegate(store, false)
    var highlightCode by PrefDelegate(store, true)

    var theme: DarkThemeManager.Theme
        get() {
            return store.getEnum("Theme")
        }
        set(value) {
            store.setEnum("Theme", value)
        }


    fun setupDefaultValues() {
        if (!defaultValuesInitialized) {
            theme = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                DarkThemeManager.Theme.AUTO
            } else {
                DarkThemeManager.Theme.DAY
            }

            defaultValuesInitialized = true
        }
    }

    companion object {
        const val KEY = "Preferences"
    }
}
