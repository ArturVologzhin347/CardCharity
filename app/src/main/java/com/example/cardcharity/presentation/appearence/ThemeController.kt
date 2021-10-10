package com.example.cardcharity.presentation.appearence

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import com.example.cardcharity.presentation.base.BaseActivity
import com.example.cardcharity.repository.preferences.Preferences

object ThemeController {
    private lateinit var theme: Theme

    val themeState: ThemeState
        get() = ThemeState.valueOf(Preferences.theme)

    fun initialize(context: Context) {
        theme = getCurrentTheme(context)
    }

    fun observe(activity: ThemeActivity) {
        activity.setTheme(getThemeFromActivity(activity))
        activity.theme = theme
    }

    fun invalidate(activity: ThemeActivity) {
        activity as AppCompatActivity

        val currentTheme = getCurrentTheme(activity)
        if (activity.theme != currentTheme) {
            theme = currentTheme

            activity.startActivity(Intent(activity, activity::class.java))
            activity.finish()
            activity.overridePendingTransition(
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
        }
    }

    //Use invalidate() after
    fun setupNewThemeState(state: ThemeState) {
        Preferences.theme = state.name
    }

    fun getSystemThemeIsNight(context: Context): Boolean {
        val nightMode = context.resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK

        return when (nightMode) {
            Configuration.UI_MODE_NIGHT_NO -> false
            Configuration.UI_MODE_NIGHT_YES -> true
            else -> false
        }
    }

    @StyleRes
    private fun getThemeFromActivity(activity: ThemeActivity): Int {
        return when(theme) {
            Theme.DAY -> activity.getDayThemeResId()
            Theme.NIGHT -> activity.getNightThemeResId()
        }
    }

    private fun getCurrentTheme(context: Context): Theme {
        return when (themeState) {
            ThemeState.AUTO -> getSystemTheme(context)
            ThemeState.DAY -> Theme.DAY
            ThemeState.NIGHT -> Theme.NIGHT
        }
    }

    fun getSystemTheme(context: Context): Theme {
        return if (getSystemThemeIsNight(context)) {
            Theme.NIGHT
        } else {
            Theme.DAY
        }
    }


    enum class ThemeState {
        DAY,
        NIGHT,
        AUTO //Android theme. Only for android Q or higher
    }

    enum class Theme(val state: ThemeState) {
        DAY(ThemeState.DAY),
        NIGHT(ThemeState.NIGHT)
    }
}