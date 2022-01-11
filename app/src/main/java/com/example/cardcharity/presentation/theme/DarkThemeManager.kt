package com.example.cardcharity.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.example.cardcharity.repository.preferences.Preferences
import com.example.cardcharity.utils.checkOrNull
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DarkThemeManager @Inject constructor(private val preferences: Preferences) {
    private val _theme = MutableStateFlow(Theme.DAY)
    val theme = _theme.asStateFlow()

    init {
        _theme.value = preferences.theme
    }

    fun setNewTheme(theme: Theme) {
        if (theme == Theme.AUTO) requireAndroidQ()
        Timber.i("Current theme - ${this.theme.value}\nNew theme - ${theme.name}")

        _theme.value = theme
        preferences.theme = theme
    }


    fun setSyncWithSystemTheme(
        localInNightMode: Boolean,
        synchronized: Boolean
    ) {
        requireAndroidQ()
        setNewTheme(if (synchronized) Theme.AUTO else getCurrentTheme(localInNightMode))
    }


    @Composable
    fun isDarkTheme(theme: Theme): Boolean {
        return when (theme) {
            Theme.AUTO -> isSystemInDarkTheme()
            Theme.NIGHT -> true
            Theme.DAY -> false
        }
    }

    fun getCurrentTheme(nightMode: Boolean): Theme {
        return if (nightMode) Theme.NIGHT else Theme.DAY
    }

    private fun requireAndroidQ() {
        require(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        { "Theme AUTO require Android Q or higher" }

    }

    enum class Theme {
        DAY,
        NIGHT,
        AUTO
    }
}
